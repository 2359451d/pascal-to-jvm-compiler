package ast.visitor.impl;

import ast.visitor.PascalBaseVisitor;
import ast.visitor.PascalParser;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import runtime.RuntimeFunction;
import runtime.RuntimeLibManager;
import runtime.RuntimeProcedure;
import type.*;
import type.enumerated.EnumeratedIdentifier;
import type.enumerated.EnumeratedType;
import type.error.ErrorType;
import type.nestedType.NestedBaseType;
import type.nestedType.param.ActualParam;
import type.nestedType.param.FormalParam;
import type.nestedType.param.Param;
import type.primitive.Boolean;
import type.primitive.Character;
import type.primitive.floating.DefaultFloatType;
import type.primitive.floating.FloatBaseType;
import type.primitive.floating.Real;
import type.primitive.integer.DefaultIntegerType;
import type.primitive.integer.Integer32;
import type.primitive.integer.IntegerBaseType;
import type.procOrFunc.Function;
import type.procOrFunc.Procedure;
import type.structured.ArrayType;
import type.structured.File;
import type.structured.RecordType;
import type.structured.StructuredBaseType;
import tableUtils.SymbolTable;
import tableUtils.Table;
import tableUtils.TableManager;
import tableUtils.TypeTable;
import utils.ErrorMessage;
import utils.log.ErrorReporter;

import java.util.*;

public class PascalCheckerVisitor extends PascalBaseVisitor<TypeDescriptor> {

    // Contextual errors
    private int errorCount = 0;

    private CommonTokenStream tokens;

    /**
     * Table & Table Manager fields
     */
    private Table<Object, TypeDescriptor> symbolTable;
    private Table<Object, TypeDescriptor> typeTable;
    private TableManager<Object, TypeDescriptor> tableManager = TableManager.getInstance(); // quick reference


    // Max Builtin Integer Type (default - Integer32)
    static final IntegerBaseType defaultIntegerType = new Integer32();
    private int indexCount = 0;

    // Constructor
    public PascalCheckerVisitor(CommonTokenStream toks) {
        tableManager.resetContainer();
        tokens = toks;
        symbolTable = new SymbolTable<>();
        typeTable = new TypeTable<>();
    }

    private void reportError(ParserRuleContext ctx, String message) {
        // Print an error message relating to the given
        // part of the AST.
        Interval interval = ctx.getSourceInterval();
        Token start = tokens.get(interval.a);
        Token finish = tokens.get(interval.b);
        int startLine = start.getLine();
        int startCol = start.getCharPositionInLine();
        int finishLine = finish.getLine();
        int finishCol = finish.getCharPositionInLine();
        // lazy logging
        ErrorReporter.error("{}:{}-{}:{} {}",
                () -> startLine,
                () -> startCol,
                () -> finishLine,
                () -> finishCol,
                () -> message);
        errorCount++;
    }

    /**
     * Report error in a formatted way
     *
     * @param ctx
     * @param message
     * @param args
     */
    private void reportError(ParserRuleContext ctx, String message, Object... args
    ) {
        reportError(ctx, String.format(message, args));
    }

    public int getNumberOfContextualErrors() {
        // Return the total number of errors so far detected.
        return errorCount;
    }

    private boolean hasNoOverflow(Long number) {
        if (number == null) return true;
        return number <= defaultIntegerType.MAX_VALUE;
    }

    private boolean hasNoUnderflow(Long number) {
        if (number == null) return true;
        return number >= defaultIntegerType.MIN_VALUE;
    }

    private boolean hasNoOverflowOrUnderflow(Long number, boolean checkOverflow, boolean checkUnderflow) {
        // null means check in runtime
        if (number == null) return true;

        if (checkOverflow && checkUnderflow) {
            return hasNoOverflow(number) && hasNoUnderflow(number);
        } else if (checkOverflow) return hasNoOverflow(number);
        return hasNoUnderflow(number);
    }

    private void predefine() {
        // Add predefined procedures to the type table.
        //BuiltInUtils.fillTable(symbolTable);
        symbolTable.put("maxint", DefaultIntegerType.of((long) Integer.MAX_VALUE, true));
        RuntimeLibManager.fillTable(symbolTable);
    }

    private void define(String id, TypeDescriptor type,
                        ParserRuleContext ctx) {
        //select the specific selectedTable with corresponding usage context
        Table<Object, TypeDescriptor> selectedTable = tableManager.selectTable(ctx.getClass());

        // Add id with its type to the selectedTable
        // Checking whether id is already declared in the same scope.
        // IGNORE CASE
        boolean isDuplicatedInOtherTable = false;
        Map<Class<? extends ParserRuleContext>, Table<Object, TypeDescriptor>> otherTables = tableManager.selectAllTablesExcludedToClass(ctx.getClass());
        for (Table<Object, TypeDescriptor> eachTable : otherTables.values()) {
            if (eachTable.containsKey(id.toLowerCase())) isDuplicatedInOtherTable = true;
        }

        boolean putSuccessfully = false;
        if (!isDuplicatedInOtherTable && selectedTable != null)
            putSuccessfully = selectedTable.put(id.toLowerCase(), type);

        if (isDuplicatedInOtherTable || !putSuccessfully)
            reportError(ctx, id + " is redeclared");
    }

    private TypeDescriptor retrieve(String id, boolean notSuppressError,
                                    ParserRuleContext ctx) {
        Table<Object, TypeDescriptor> selectedTable = tableManager.selectTable(ctx.getClass());
        // Retrieve id's type from all the defined table.
        // Case insensitive
        TypeDescriptor type = selectedTable.get(id.toLowerCase());

        if (type == null) {
            Map<Class<? extends ParserRuleContext>, Table<Object, TypeDescriptor>> tablesExcludedToClass = tableManager.selectAllTablesExcludedToClass(ctx.getClass());
            for (Table<Object, TypeDescriptor> eachTable : tablesExcludedToClass.values()) {
                type = eachTable.get(id.toLowerCase());
                if (type != null) return type;
            }
            if (notSuppressError) reportError(ctx, "Identifier %s is undeclared", id);
            return ErrorType.UNDEFINED_TYPE;
        }
        return type;
    }

    private TypeDescriptor retrieve(String id, ParserRuleContext occ) {
        // default: do not suppress identifier undeclared errors
        return retrieve(id, true, occ);
    }

    private Map<String, Pair<TypeDescriptor, ParserRuleContext>> prototypeImplTrackingMap;

    /**
     * 1. Set up predefined builtins
     * <p>
     * 2. After visiting all the proc/func declarations,
     * <p>
     * Check whether there exist implementation block for every declared func/proc prototype
     * <p>
     * 3. After visiting all the type definition part
     * <p>
     * Check whether the pointed type is declared (so that pointer type is valid)
     * <p>
     * * ! If not report errors
     * *
     * * * Every new Entry would be inserted when the ProcedurePrototypeDecl/FunctionPrototypeDecl node is visited
     * * * If corresponding implementation found, previous entry would be removed
     * * * Eventually, iterate all the entry inside the tracking map. And report the errors
     * *
     * </p>
     *
     * @param ctx
     * @return
     */
    @Override
    public TypeDescriptor visitProgram(PascalParser.ProgramContext ctx) {
        predefine();
        prototypeImplTrackingMap = new LinkedHashMap<>();
        pointedTypeTrackingMap = new LinkedHashMap<>();
        visit(ctx.block());

        // missing implementation, report errors
        if (!prototypeImplTrackingMap.isEmpty()) {
            prototypeImplTrackingMap.forEach((k, v) -> {
                TypeDescriptor prototypeDecl = v.getLeft();
                ParserRuleContext context = v.getRight();
                reportError(context, "Forward declaration cannot be resolved. Implementation is missing for: %s",
                        prototypeDecl);
            });
        }
        return null;
    }

    /**
     * block
     * : (labelDeclarationPart | constantDefinitionPart | typeDefinitionPart | variableDeclarationPart | procedureAndFunctionDeclarationPart | usesUnitsPart | IMPLEMENTATION)* compoundStatement
     * ;
     *
     * @param ctx
     * @return
     */
    @Override
    public TypeDescriptor visitBlock(PascalParser.BlockContext ctx) {
        visitChildren(ctx);
        tableManager.displayAllTablesCurrentScope();
        return null;
    }

    @Override
    public TypeDescriptor visitIdentifier(PascalParser.IdentifierContext ctx) {
        //skip if the parent node is ProgramHeading, do not process the ProgramHeading ID
        ParserRuleContext parent = ctx.getParent();
        if (parent instanceof PascalParser.ProgramHeadingContext) return null;
        return retrieve(ctx.getText(), ctx);
    }

    @Override
    public TypeDescriptor visitIdentifierList(PascalParser.IdentifierListContext ctx) {
        if (ctx.parent instanceof PascalParser.EnumeratedTypeContext) {
            // if upper node is enumerate type, do not visit identifier in symbol table
            return null;
        }
        return super.visitIdentifierList(ctx);
    }

    @Override
    public TypeDescriptor visitTypeDefinitionPart(PascalParser.TypeDefinitionPartContext ctx) {
        visitChildren(ctx);
        // after finishing all the type definition part
        // check whether there exist any invalid pointer type (report errors if any)
        if (!pointedTypeTrackingMap.isEmpty()) {
            pointedTypeTrackingMap.forEach((k, v) -> {
                reportError(v.getRight(), "%s is undeclared", k);
            });
        }
        return null;
    }

    /**
     * Retrieve the type from the type table
     * Return the type if defined, otherwise return null
     *
     * <p>
     * typeIdentifier
     * : identifier                                 # typeId
     * | primitiveType=(CHAR | BOOLEAN | INTEGER | REAL | STRING)  # primitiveType
     * ;
     *
     * @param ctx
     * @return
     */
    @Override
    public TypeDescriptor visitTypeId(PascalParser.TypeIdContext ctx) {
        String id = ctx.identifier().getText();
        TypeDescriptor type = retrieve(id, false, ctx);
        if (type.equiv(ErrorType.UNDEFINED_TYPE)) {
            reportError(ctx, "Type %s is undeclared", id);
        }
        return type;
    }

    /**
     * Each new defined type would be inserted into the type table
     * <p>
     * typeName -> Type
     * </p>
     *
     * <p>
     * typeDefinition
     * : identifier EQUAL (type_ | functionType | procedureType)
     * ;
     *
     * @param ctx
     * @return
     */
    @Override
    public TypeDescriptor visitTypeDefinition(PascalParser.TypeDefinitionContext ctx) {
        String id = ctx.identifier().getText().toLowerCase();
        Map<Object, TypeDescriptor> allVarInCurrentScope = typeTable.getAllVarInCurrentScope();
        TypeDescriptor type = visit(ctx.type_());
        define(id, type, ctx);
        //POINTER to POINTER (two level pointer)
        // if id in the pointed type map, then pre-defined pointer type is valid
        // update the hostType of the stored placeholder pointer
        if (pointedTypeTrackingMap.containsKey(id)) {
            Pair<String, ParserRuleContext> pair = pointedTypeTrackingMap.get(id);
            String pointerId = pair.getLeft();
            TypeDescriptor pointerType = retrieve(pointerId, true, ctx);
            if (pointerType instanceof PointerType) {
                ((PointerType) pointerType).setPointedType(type);
            }
            // remove the entry from the tracking map
            pointedTypeTrackingMap.remove(id);
            return null;
        }
        return null;
    }

    @Override
    public TypeDescriptor visitVariableDeclaration(PascalParser.VariableDeclarationContext ctx) {
        tableManager.displayAllTablesCurrentScope();

        List<PascalParser.IdentifierContext> identifierContextList = ctx.identifierList().identifier();
        for (PascalParser.IdentifierContext identifierContext : identifierContextList) {
            String id = identifierContext.IDENT().getText();
            TypeDescriptor type = visit(ctx.type_());
            define(id, type, ctx);
        }
        //symbolTable.displayCurrentScope();
        tableManager.displayAllTablesCurrentScope();
        return null;
    }

    /**
     * Constant definition part, inserted into the symbol table
     * Type checking whether right operand is allowed constant
     * There are 3 builtin constant (could be used without prior definition)
     * - false, true, maxInt
     * <p>
     * Remark: Error is reported in "constant" node, this node only
     * insert a new entry into the symbol table, if const type is valid
     * </p>
     *
     * <p>
     * constantDefinition
     * : identifier EQUAL constant
     * ;
     * constant
     * : unsignedNumber #unsignedNumberConst
     * | sign unsignedNumber #signedNumberConst
     * | identifier #constantIdentifier
     * | sign identifier #constantSignedIdentifier
     * | string #stringConst
     * | constantChr #chrConst // chr(int) , ordinal func
     * | TRUE #trueConst
     * | FALSE #falseConst
     * ;
     *
     * @param ctx
     * @return
     */
    @Override
    public TypeDescriptor visitConstantDefinition(PascalParser.ConstantDefinitionContext ctx) {
        String id = ctx.identifier().getText();
        TypeDescriptor type = visit(ctx.constant());
        // suppress errors if constant type is ErrorType
        if (!(type instanceof ErrorType)) {
            BaseType constantType = (BaseType) type;
            constantType.setConstant(true);
            define(id, constantType, ctx);
        }
        return null;
    }

    /**
     * Legal constant type (unsigned): int, real
     * <p>
     * constant
     * * : unsignedNumber #unsignedNumberConst
     * <p>
     * unsignedNumber
     * : unsignedInteger
     * | unsignedReal
     * ;
     *
     * @param ctx
     * @return
     */
    @Override
    public TypeDescriptor visitUnsignedNumberConst(PascalParser.UnsignedNumberConstContext ctx) {
        TypeDescriptor unsignedNumber = visit(ctx.unsignedNumber());
        if (unsignedNumber instanceof IntegerBaseType) {
            Long numberValue = ((IntegerBaseType) unsignedNumber).getValue();
            if (!hasNoOverflow(numberValue)) {
                reportError(ctx, "Illegal constant definition [%s] with right operand [%s] " +
                                "which must be between [%d] and [%d]",
                        ctx.parent.getText(), ctx.getText(),
                        defaultIntegerType.MIN_VALUE, defaultIntegerType.MAX_VALUE
                );
                //return ErrorType.INTEGER_OVERFLOW;
            }
        }
        return unsignedNumber;
        //switch (ctx.unsignedNumber().type.getType()) {
        //    // check whether unsignedNumber(int) is less than maxint
        //    case PascalParser.NUM_INT:
        //        TypeDescriptor number = visit(ctx.unsignedNumber());
        //        Long numberValue = ((IntegerBaseType) number).getValue();
        //        if (!hasNoOverflow(numberValue)) {
        //            //reportError(ctx, "Illegal constant definition [%s] with right operand [%s] " +
        //            //                "which must be between [%d] and [%d]",
        //            //        ctx.parent.getText(), ctx.getText(),
        //            //        integerType.MIN_VALUE, integerType.MAX_VALUE
        //            //);
        //            return ErrorType.INTEGER_OVERFLOW;
        //        }
        //        return visit(ctx.unsignedNumber());
        //    case PascalParser.NUM_REAL:
        //        return Type.REAL;
        //}
    }

    /**
     * Check whether right operand(int) is in the range of (-maxint, maxint)
     * No need to check whether sign is applicable on right operand
     * As right operand must be real or integer
     * <p>
     * sign unsignedNumber #signedNumberConst
     * <p>
     * unsignedNumber
     * : unsignedIntesger
     * | unsignedReal
     * ;
     *
     * @param ctx
     * @return
     */
    @Override
    public TypeDescriptor visitSignedNumberConst(PascalParser.SignedNumberConstContext ctx) {
        TypeDescriptor unsignedNumber = visit(ctx.unsignedNumber());
        String monadicOperator = ctx.sign().getText();
        // Only check, if right operand is integer
        if (unsignedNumber instanceof IntegerBaseType) {
            Long numberValue = ((IntegerBaseType) unsignedNumber).getValue();
            switch (monadicOperator) {
                case "-":
                    numberValue = -numberValue;
                    if (!hasNoUnderflow(numberValue)) {
                        reportError(ctx, "Illegal constant definition [%s] with right operand [%s]" +
                                        "which must be between [%d] and [%d]",
                                ctx.getParent().getText(), numberValue, defaultIntegerType.MIN_VALUE, defaultIntegerType.MAX_VALUE);
                        return ErrorType.INTEGER_UNDERFLOW;
                        //break;
                    }
                case "+":
                    if (!hasNoOverflow(numberValue)) {
                        reportError(ctx, "Illegal constant definition [%s] with right operand [%s]" +
                                        "which must be between [%d] and [%d]",
                                ctx.getParent().getText(), numberValue, defaultIntegerType.MIN_VALUE, defaultIntegerType.MAX_VALUE);
                        return ErrorType.INTEGER_OVERFLOW;
                        //break;
                    }
            }
            return new Integer32(numberValue);
        }
        return unsignedNumber;
    }

    @Override
    public TypeDescriptor visitConstantIdentifier(PascalParser.ConstantIdentifierContext ctx) {
        return super.visitConstantIdentifier(ctx);
    }

    @Override
    public TypeDescriptor visitConstantSignedIdentifier(PascalParser.ConstantSignedIdentifierContext ctx) {
        return super.visitConstantSignedIdentifier(ctx);
    }

    @Override
    public TypeDescriptor visitStringConst(PascalParser.StringConstContext ctx) {
        return super.visitStringConst(ctx);
    }

    @Override
    public TypeDescriptor visitChrConst(PascalParser.ChrConstContext ctx) {
        return super.visitChrConst(ctx);
    }

    @Override
    public TypeDescriptor visitBoolConst(PascalParser.BoolConstContext ctx) {
        return super.visitBoolConst(ctx);
    }

    @Override
    public TypeDescriptor visitPrimitiveType(PascalParser.PrimitiveTypeContext ctx) {
        switch (ctx.primitiveType.getType()) {
            case PascalParser.INTEGER:
                //return IntegerBaseType.copy(defaultIntegerType);
                return DefaultIntegerType.instance;
            case PascalParser.STRING:
                return new StringLiteral();
            case PascalParser.CHAR:
                return new Character();
            case PascalParser.BOOLEAN:
                return new Boolean();
            case PascalParser.REAL:
                return DefaultFloatType.instance;
        }
        return super.visitPrimitiveType(ctx);
    }

    /**
     * Enumerated type, which is defined to be the Ordinal type
     * <p>
     * scalarType
     * : LPAREN identifierList RPAREN   # enumeratedType
     * ;
     *
     * @param ctx
     * @return
     */
    @Override
    public TypeDescriptor visitEnumeratedType(PascalParser.EnumeratedTypeContext ctx) {
        List<PascalParser.IdentifierContext> identifierContextList = ctx.identifierList().identifier();
        EnumeratedType enumeratedType = new EnumeratedType();
        HashMap<String, Integer> valueMap = new LinkedHashMap<>();
        for (int i = 0; i < identifierContextList.size(); i++) {
            // store lower case, (simulating case insensitive)
            String id = identifierContextList.get(i).getText().toLowerCase();
            valueMap.put(id,
                    i);
            // insert each enumerated identifier into the symbol table
            //symbolTable.put(id, new EnumeratedIdentifier(enumeratedType, id));
            define(id, new EnumeratedIdentifier(enumeratedType, id), ctx);
        }
        enumeratedType.setValueMap(valueMap);

        return enumeratedType;
    }

    /**
     * Host type must be ordinal type:
     * <p>
     * - int, char, bool,
     * <p>
     * - existing subrange/enum types
     * <p>
     * subrangeType
     * : constant DOTDOT constant
     * ;
     *
     * @param ctx
     * @return
     */
    @Override
    public TypeDescriptor visitSubrangeType(PascalParser.SubrangeTypeContext ctx) {
        // choose type of the first constant as the host type
        TypeDescriptor lowerBound = visit(ctx.constant(0));
        TypeDescriptor upperBound = visit(ctx.constant(1));
        // check whether lowerBound & upperBound is valid
        ErrorType subrangeError = checkSubrangeDecl(lowerBound, upperBound, ctx);
        if (null != subrangeError) {
            if (subrangeError.equiv(ErrorType.INVALID_SUBRANGE_TYPE)) {
                reportError(ctx, "Invalid subrange bound type [%s]. \nLtype: %s,\nRtype:%s",
                        ctx.getText(), lowerBound, upperBound);
            }
            if (subrangeError.equiv(ErrorType.INCOMPATIBLE_SUBRANGE_TYPE)) {
                reportError(ctx, "Incompatible subrange bound [%s]. \nLtype: %s,\nRtype:%s",
                        ctx.getText(), lowerBound, upperBound);
            }
            if (subrangeError.equiv(ErrorType.INVALID_SUBRANGE_BOUND)) {
                reportError(ctx, "Invalid subrange bound [%s]. Lower bound value > Upper bound value",
                        ctx.getText());
            }
        }

        Subrange subrange = new Subrange(lowerBound, upperBound);
        return subrange;
    }

    private ErrorType checkSubrangeDecl(TypeDescriptor lowerBound, TypeDescriptor upperBound, PascalParser.SubrangeTypeContext ctx) {
        if (lowerBound.getClass() != upperBound.getClass()) return ErrorType.INCOMPATIBLE_SUBRANGE_TYPE;
        if (lowerBound instanceof Boolean) {
            boolean lowerValue = ((Boolean) lowerBound).getValue();
            boolean upperValue = ((Boolean) upperBound).getValue();
            return java.lang.Boolean.compare(lowerValue, upperValue) == 1 ? ErrorType.INVALID_SUBRANGE_BOUND : null;
        }
        if (lowerBound instanceof StringLiteral) {
            String lowerValue = ((StringLiteral) lowerBound).getValue();
            String upperValue = ((StringLiteral) upperBound).getValue();
            // only accepts character type(1 char + 2 marks) as subrange bound
            if (lowerValue.length() > 3 || upperValue.length() > 3) return ErrorType.INVALID_SUBRANGE_TYPE;
            if (StringUtils.compare(lowerValue, upperValue) > 0) return ErrorType.INVALID_SUBRANGE_BOUND;
        }

        if (lowerBound instanceof IntegerBaseType) {
            Long lowerValue = ((IntegerBaseType) lowerBound).getValue();
            Long upperValue = ((IntegerBaseType) upperBound).getValue();
            return lowerValue <= upperValue ? null : ErrorType.INVALID_SUBRANGE_BOUND;
        }

        if (lowerBound instanceof EnumeratedIdentifier) {
            Map<String, Integer> valueMap = ((EnumeratedIdentifier) lowerBound).getBelongsTo().getValueMap();
            String lowerValue = ((EnumeratedIdentifier) lowerBound).getValue();
            String upperValue = ((EnumeratedIdentifier) upperBound).getValue();
            // enumerated types are not of the same kind
            if (!valueMap.containsKey(upperValue)) return ErrorType.INCOMPATIBLE_SUBRANGE_TYPE;
            if (valueMap.get(lowerValue) > valueMap.get(upperValue)) return ErrorType.INVALID_SUBRANGE_BOUND;
        }

        return null;
    }

    /**
     * If token PACKED is not null, set the structured type isPacked flag to true
     * <p>
     * structuredType
     * : PACKED unpackedStructuredType
     * | unpackedStructuredType
     * ;
     *
     * @param ctx
     * @return
     */
    @Override
    public TypeDescriptor visitStructuredType(PascalParser.StructuredTypeContext ctx) {
        TypeDescriptor structuredType = visit(ctx.unpackedStructuredType());

        if (ctx.PACKED() != null) {
            if (structuredType instanceof StructuredBaseType) {
                ((StructuredBaseType) structuredType).setPacked(true);
            }
        }
        return structuredType;
    }

    /**
     * ! Index must be ordinal types
     * ! * bool, char, int, subrange, enumerated types
     * ! * perform index range checking
     * <p>
     * ? Host Type could be any types
     * <p>
     * arrayType
     * : ARRAY LBRACK typeList RBRACK OF componentType
     * | ARRAY LBRACK2 typeList RBRACK2 OF componentType
     * ;
     * typeList
     * : indexType (COMMA indexType)*
     * ;
     * indexType
     * : simpleType
     * ;
     * simpleType
     * : scalarType
     * | subrangeType
     * | typeIdentifier
     * | stringtype
     * ;
     *
     * @param ctx
     * @return
     */
    @Override
    public TypeDescriptor visitArrayType(PascalParser.ArrayTypeContext ctx) {
        TypeDescriptor componentType = visit(ctx.componentType());
        List<TypeDescriptor> indexList = new ArrayList<>();
        List<PascalParser.IndexTypeContext> indexTypeContextList = ctx.typeList().indexType();
        indexTypeContextList.forEach(each -> {
            indexList.add(visit(each));
        });


        ArrayType arrayType;
        // flatten the index and extract the component type
        if (componentType instanceof ArrayType) {
            TypeDescriptor _componentType = ((ArrayType) componentType).getComponentType();
            List<TypeDescriptor> _indexList = ((ArrayType) componentType).getIndexList();
            indexList.addAll(_indexList);
            arrayType = new ArrayType(_componentType, indexList);
        } else {
            arrayType = new ArrayType(componentType, indexList);
        }
        // create a unpacked array (default)
        return arrayType;
    }

    /**
     * Record type
     * <p>
     * ! Fields name must be distinct of the same record type
     * ! Record type could be defined in the type, var decl as usual
     * <p>
     * recordType
     * : RECORD fieldList? END
     * ;
     *
     * @param ctx
     * @return
     */
    @Override
    public TypeDescriptor visitRecordType(PascalParser.RecordTypeContext ctx) {
        // enter a new scope for define the fields of record type
        symbolTable.enterLocalScope();
        visit(ctx.fieldList());
        Map<String, TypeDescriptor> fieldsMap = new HashMap<>();
        Map<Object, TypeDescriptor> allVarInCurrentScope = symbolTable.getAllVarInCurrentScope();
        for (Map.Entry<Object, TypeDescriptor> entry : allVarInCurrentScope.entrySet()) {
            if (entry.getKey() instanceof String) {
                fieldsMap.put((String) entry.getKey(), entry.getValue());
            }
        }
        RecordType recordType = new RecordType(fieldsMap);
        symbolTable.exitLocalScope();
        return recordType;
    }

    /**
     * fixedPart
     * : recordSection (SEMI recordSection)*
     * ;
     *
     * @param ctx
     * @return
     */
    @Override
    public TypeDescriptor visitFixedPart(PascalParser.FixedPartContext ctx) {
        return super.visitFixedPart(ctx);
    }

    /**
     * Define all the identifiers with corresponding types in current record scope
     * <p>
     * recordSection
     * : identifierList COLON type_
     * ;
     *
     * @param ctx
     * @return
     */
    @Override
    public TypeDescriptor visitRecordSection(PascalParser.RecordSectionContext ctx) {
        List<PascalParser.IdentifierContext> identifierList = ctx.identifierList().identifier();
        TypeDescriptor type = visit(ctx.type_());
        identifierList.forEach(each -> define(each.getText(), type, ctx));
        return null;
    }

    private Map<String, Pair<String, ParserRuleContext>> pointedTypeTrackingMap;

    @Override
    public TypeDescriptor visitPointerType(PascalParser.PointerTypeContext ctx) {
        // lazily check the correctness of the pointer type definition
        // where allows the pointed type to be declared later
        PascalParser.TypeIdentifierContext typeIdentifierContext = ctx.typeIdentifier();
        if (ctx.parent.parent instanceof PascalParser.TypeDefinitionContext
                && typeIdentifierContext instanceof PascalParser.TypeIdContext) {
            String id = null;
            PascalParser.IdentifierContext identifier = ((PascalParser.TypeDefinitionContext) ctx.parent.parent).identifier();
            id = identifier.getText().toLowerCase();
            String text = ((PascalParser.TypeIdContext) typeIdentifierContext).identifier().getText();

            TypeDescriptor retrieve = retrieve(text, false, ctx);
            if (retrieve!=null && !(retrieve instanceof ErrorType)) {
                return new PointerType(retrieve);
            }

            pointedTypeTrackingMap.put(text.toLowerCase(), Pair.of(id, ctx));
            // directly return the pointer type placeholder first
            return new PointerType(null);
        }
        // if pointedType is builtin type
        // or already defined type where ctx node is under variable declaration
        TypeDescriptor builtinType = visit(typeIdentifierContext);
        return new PointerType(builtinType);
    }

    @Override
    public TypeDescriptor visitFileType(PascalParser.FileTypeContext ctx) {
        TypeDescriptor type = visit(ctx.type_());
        return new File(type);
    }


    /**
     * formalParameterSection
     * : parameterGroup  #noLabelParam
     * | VAR parameterGroup #varLabelParam
     * | FUNCTION parameterGroup #funcParam
     * | PROCEDURE parameterGroup #procParam
     * ;
     *
     * @param ctx
     * @return
     */
    @Override
    public TypeDescriptor visitNoLabelParam(PascalParser.NoLabelParamContext ctx) {
        TypeDescriptor type = visit(ctx.parameterGroup().typeIdentifier());
        List<PascalParser.IdentifierContext> identifiers = ctx.parameterGroup().identifierList().identifier();
        for (PascalParser.IdentifierContext identifier : identifiers) {
            // for each group, define the corresponding formal parameter with null label
            // (x,y,...:Type)
            String id = identifier.getText();
            define(id, new FormalParam(type, id, null), ctx);
        }
        return null;
    }

    /**
     * formalParameterSection
     * : parameterGroup  #noLabelParam
     * | VAR parameterGroup #varLabelParam
     * | FUNCTION parameterGroup #funcParam
     * | PROCEDURE parameterGroup #procParam
     * ;
     *
     * @param ctx
     * @return
     */
    @Override
    public TypeDescriptor visitVarLabelParam(PascalParser.VarLabelParamContext ctx) {
        TypeDescriptor type = visit(ctx.parameterGroup().typeIdentifier());
        List<PascalParser.IdentifierContext> identifiers = ctx.parameterGroup().identifierList().identifier();
        for (PascalParser.IdentifierContext identifier : identifiers) {
            // for each group, define the corresponding formal parameter with var label
            // (var x,y,...:Type)
            String id = identifier.getText();
            define(id, new FormalParam(type, id, "var"), ctx);
        }
        return null;
    }

    /**
     * Function as parameter
     * Return a Function Type as the hostType of FormalParam class
     *
     * <p>
     * functionHeading
     * : FUNCTION identifier (formalParameterList)? COLON resultType
     * ;
     * </p>
     *
     * @param ctx
     * @return
     */
    @Override
    public TypeDescriptor visitFuncParam(PascalParser.FuncParamContext ctx) {

        tableManager.allTablesEnterNewScope();

        // Function id, ignore case
        String id = ctx.functionHeading().identifier().getText();
        TypeDescriptor resultType = visit(ctx.functionHeading().resultType());
        // prepare the formals
        List<TypeDescriptor> formalParams = new ArrayList<>();
        if (ctx.functionHeading().formalParameterList() != null) {
            List<PascalParser.FormalParameterSectionContext> formalParameterSectionContexts = ctx.functionHeading().formalParameterList().formalParameterSection();
            formalParameterSectionContexts.forEach(this::visit);
            symbolTable.getAllVarInCurrentScope().forEach((k, v) -> formalParams.add(v));
        }
        tableManager.displayAllTablesCurrentScope();

        tableManager.allTablesExitNewScope();

        Function function = new Function(formalParams, resultType);
        define(id, new FormalParam(function, id, null), ctx);

        return null;
    }

    /**
     * Procedure as parameter
     * Return a Function Type as the hostType of FormalParam class
     *
     * <p>
     * procedureHeading
     * : PROCEDURE identifier (formalParameterList)?
     * ;
     * </p>
     *
     * @param ctx
     * @return
     */
    @Override
    public TypeDescriptor visitProcParam(PascalParser.ProcParamContext ctx) {
        tableManager.allTablesEnterNewScope();

        // Function id, ignore case
        String id = ctx.procedureHeading().identifier().getText();
        // prepare the formals
        List<TypeDescriptor> formalParams = new ArrayList<>();
        if (ctx.procedureHeading().formalParameterList() != null) {
            List<PascalParser.FormalParameterSectionContext> formalParameterSectionContexts = ctx.procedureHeading().formalParameterList().formalParameterSection();
            formalParameterSectionContexts.forEach(this::visit);
            symbolTable.getAllVarInCurrentScope().forEach((k, v) -> formalParams.add(v));
        }
        tableManager.allTablesExitNewScope();
        Procedure procedure = new Procedure(formalParams);
        define(id, new FormalParam(procedure, id, null), ctx);
        return null;
    }


    @Override
    public TypeDescriptor visitProcedureAndFunctionDeclarationPart(PascalParser.ProcedureAndFunctionDeclarationPartContext ctx) {
        visitChildren(ctx);
        return null;
    }

    /**
     * procedureDeclaration
     * : PROCEDURE identifier (formalParameterList)? SEMI block
     * ;
     *
     * @param ctx
     * @return
     */
    @Override
    public TypeDescriptor visitProcedureDecl(PascalParser.ProcedureDeclContext ctx) {
        String id = ctx.identifier().getText().toLowerCase();
        ArrayList<TypeDescriptor> params = new ArrayList<>();

        // set true if procedureDecl ctx is actually the implementation of predefined prototype
        boolean isProcedureImpl = false;
        Pair<TypeDescriptor, ParserRuleContext> procPrototypePair = null;
        if (prototypeImplTrackingMap.containsKey(id)) {
            isProcedureImpl = true;
            procPrototypePair = prototypeImplTrackingMap.get(id);
            TypeDescriptor type = procPrototypePair.getLeft();
            // if prototype is of Function type which the implementation cannot match
            // report errors
            if (!(type instanceof Procedure)) {
                reportError(ctx, "Implantation header cannot match previous prototype declaration: %s",
                        type);
            }
            // remove the entry in the tracking map, no matter whether the implementation can match the header or not
            prototypeImplTrackingMap.remove(id);
        }

        symbolTable.displayCurrentScope();
        //symbolTable.enterLocalScope();
        //typeTable.enterLocalScope();
        tableManager.allTablesEnterNewScope();

        // if the procedure has formal parameters
        if (ctx.formalParameterList() != null) {
            List<PascalParser.FormalParameterSectionContext> formalParameterSectionList = ctx.formalParameterList().formalParameterSection();
            for (PascalParser.FormalParameterSectionContext paramSection : formalParameterSectionList) {
                // define parameter group in current scope of type Param(Type,String:label)
                visit(paramSection);
            }
            // all formal params set up
            Map<Object, TypeDescriptor> allParams = symbolTable.getAllVarInCurrentScope();
            allParams.forEach((k, v) -> params.add(v));
        } else if (isProcedureImpl) {
            // visit all the parameters from prototype trackingmap
            ParserRuleContext procPrototypePairRight = procPrototypePair.getRight();
            if (procPrototypePairRight instanceof PascalParser.ProcedurePrototypeDeclContext) {
                PascalParser.ProcedureHeadingContext functionHeadingContext = ((PascalParser.ProcedurePrototypeDeclContext) procPrototypePairRight).procedureHeading();
                if (functionHeadingContext.formalParameterList() != null) {
                    List<PascalParser.FormalParameterSectionContext> formalParameterSectionList = functionHeadingContext.formalParameterList().formalParameterSection();
                    for (PascalParser.FormalParameterSectionContext paramSection : formalParameterSectionList) {
                        // define parameter group in current scope of type Param(Type,String:label)
                        visit(paramSection);
                    }
                    // all formal params set up
                    Map<Object, TypeDescriptor> allParams = symbolTable.getAllVarInCurrentScope();
                    allParams.forEach((k, v) -> params.add(v));
                }
            }
        }

        symbolTable.displayCurrentScope();
        Procedure procedure = new Procedure(params);
        define(id, procedure, ctx);

        tableManager.displayAllTablesCurrentScope();
        //symbolTable.displayCurrentScope();
        visit(ctx.block()); // scope & type checking in current proc scope
        //symbolTable.exitLocalScope(); // back to last scope
        //typeTable.exitLocalScope();
        tableManager.allTablesExitNewScope();

        if (!isProcedureImpl) define(id, procedure, ctx);
        //symbolTable.displayCurrentScope();
        tableManager.displayAllTablesCurrentScope();
        return null;
    }

    @Override
    public TypeDescriptor visitProcedurePrototypeDecl(PascalParser.ProcedurePrototypeDeclContext ctx) {
        String id = ctx.procedureHeading().identifier().getText().toLowerCase();
        ArrayList<TypeDescriptor> params = new ArrayList<>();

        tableManager.allTablesEnterNewScope();

        // if the procedure has formal parameters
        PascalParser.FormalParameterListContext formalParameterListContext = ctx.procedureHeading().formalParameterList();
        if (formalParameterListContext != null) {
            List<PascalParser.FormalParameterSectionContext> formalParameterSectionList = formalParameterListContext.formalParameterSection();
            for (PascalParser.FormalParameterSectionContext paramSection : formalParameterSectionList) {
                // define parameter group in current scope of type Param(Type,String:label)
                visit(paramSection);
            }
            // all formal params set up
            Map<Object, TypeDescriptor> allParams = symbolTable.getAllVarInCurrentScope();
            allParams.forEach((k, v) -> params.add(v));
        }
        Procedure procedure = new Procedure(params);
        define(id, procedure, ctx);

        //tableManager.displayAllTablesCurrentScope();

        tableManager.allTablesExitNewScope();
        define(id, procedure, ctx);
        symbolTable.displayCurrentScope();

        // insert new entry into the tracking map
        // check whether there exist implementation corresponds to current function prototype declaration
        // Ignore Case
        prototypeImplTrackingMap.put(id.toLowerCase(), Pair.of(procedure, ctx));

        return null;
    }

    /**
     * Procedure implementation part, must correspond to predefined forward declaration
     * <p>
     * procedureDeclaration
     * : procedureHeading SEMI directive #procedurePrototypeDecl
     * | PROCEDURE identifier SEMI block #procedureImpl
     * | PROCEDURE identifier (formalParameterList)? SEMI block #procedureDecl
     * ;
     *
     * @param ctx
     * @return
     */
    //@Override
    //public TypeDescriptor visitProcedureImpl(PascalParser.ProcedureImplContext ctx) {
    //    String id = ctx.identifier().getText().toLowerCase();
    //    symbolTable.displayCurrentScope();
    //
    //    if (prototypeImplTrackingMap.containsKey(id)) {
    //        Pair<TypeDescriptor, ParserRuleContext> functionPrototypePair = prototypeImplTrackingMap.get(id);
    //        TypeDescriptor type = functionPrototypePair.getLeft();
    //        // if prototype is of Function type which the implementation cannot match
    //        // report errors
    //        if (!(type instanceof Procedure)) {
    //            reportError(ctx, "Implantation header cannot match previous prototype declaration: %s",
    //                    type);
    //        }
    //        // remove the entry in the tracking map, no matter whether the implementation can match the header or not
    //        prototypeImplTrackingMap.remove(id);
    //    }
    //
    //    // enter new scope for scope checking
    //    tableManager.allTablesEnterNewScope();
    //
    //    TypeDescriptor procedureOrFunction = retrieve(id, ctx); // get predefined procedure prototype
    //    define(id, procedureOrFunction, ctx);
    //
    //    //extract all the defined formals, inserting into current scope
    //    if (procedureOrFunction instanceof ProcFuncBaseType) {
    //        List<TypeDescriptor> formalParams = ((ProcFuncBaseType) procedureOrFunction).getFormalParams();
    //        formalParams.forEach(each -> {
    //            if (each instanceof FormalParam) {
    //                String formalName = ((FormalParam) each).getName();
    //                define(formalName, each, ctx);
    //            }
    //        });
    //    }
    //
    //    visit(ctx.block()); // scope & type checking in current proc scope
    //    tableManager.allTablesExitNewScope();
    //
    //    return null;
    //}

    /**
     * Check whether a Function has result assignment or not (should be the last node in the block)
     * Report errors if the result assignment statement is missing
     * <p>
     * ! Enhancement could be using XPath .etc to find specific node type in the tree
     *
     * @return boolean - true if function has result assignment, otherwise return false
     */
    @Deprecated
    private boolean functionHasResultAssignment(PascalParser.StatementsContext ctx, String functionId) {
        List<PascalParser.StatementContext> statement = ctx.statement();
        for (PascalParser.StatementContext statementContext : statement) {
            PascalParser.UnlabelledStatementContext unlabelledStatementContext = statementContext.unlabelledStatement();
            PascalParser.SimpleStatementContext simpleStatementContext = unlabelledStatementContext.simpleStatement();
            if (simpleStatementContext != null) {
                PascalParser.AssignmentStatementContext assignmentStatement = simpleStatementContext.assignmentStatement();
                if (assignmentStatement != null) {
                    if (assignmentStatement.variable().getText().equals(functionId)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Global tracking whether the selectedFunction has return assignment
     */
    private boolean functionHasReturnAssignment;
    /**
     * Select id of current functionDeclaration node
     */
    private String selectedFunction = null;

    /**
     * functionDeclaration
     * : FUNCTION identifier (formalParameterList)? COLON resultType SEMI block
     * ;
     *
     * @param ctx
     * @return
     */
    @Override
    public TypeDescriptor visitFunctionDecl(PascalParser.FunctionDeclContext ctx) {
        // initialise for each declared Function
        functionHasReturnAssignment = false;

        String id = ctx.identifier().getText().toLowerCase();

        // set true if FunctionDecl ctx is actually the implementation of predefined prototype
        boolean isFunctionImpl = false;
        if (!prototypeImplTrackingMap.containsKey(id) &&
                ctx.resultType() == null) {
            reportError(ctx, "Invalid function declaration [%s]. Function must have return type.", ctx.getText());
            return null;
        }
        Pair<TypeDescriptor, ParserRuleContext> functionPrototypePair = null;
        if (prototypeImplTrackingMap.containsKey(id)) {
            isFunctionImpl = true;
            functionPrototypePair = prototypeImplTrackingMap.get(id);
            TypeDescriptor type = functionPrototypePair.getLeft();
            // if prototype is of Procedure type which the implementation cannot match
            // report errors
            if (!(type instanceof Function)) {
                reportError(ctx, "Implantation header cannot match previous prototype declaration: %s",
                        type);
            }
            // remove the entry in the tracking map, no matter whether the implementation can match the header or not
            prototypeImplTrackingMap.remove(id);
        }

        selectedFunction = id;
        TypeDescriptor resultType = null;
        if (ctx.resultType() != null) {
            // if normal function declaration
            resultType = visit(ctx.resultType());
        } else if (functionPrototypePair != null) {
            TypeDescriptor function = functionPrototypePair.getLeft();
            if (function instanceof Function) {
                resultType = ((Function) function).getResultType();
            }
        }
        ArrayList<TypeDescriptor> params = new ArrayList<>();
        tableManager.allTablesEnterNewScope();
        // if the procedure has formal parameters
        if (ctx.formalParameterList() != null) {
            List<PascalParser.FormalParameterSectionContext> formalParameterSectionList = ctx.formalParameterList().formalParameterSection();
            for (PascalParser.FormalParameterSectionContext paramSection : formalParameterSectionList) {
                // define parameter group in current scope of type Param(Type,String:label)
                visit(paramSection);
            }

            // all formal params set up
            Map<Object, TypeDescriptor> allParams = symbolTable.getAllVarInCurrentScope();
            allParams.forEach((k, v) -> params.add(v));

        } else if (isFunctionImpl) {
            // visit all the parameters from prototype trackingmap
            ParserRuleContext functionPrototypePairRight = functionPrototypePair.getRight();
            if (functionPrototypePairRight instanceof PascalParser.FunctionPrototypeDeclContext) {
                PascalParser.FunctionHeadingContext functionHeadingContext = ((PascalParser.FunctionPrototypeDeclContext) functionPrototypePairRight).functionHeading();
                if (functionHeadingContext.formalParameterList() != null) {
                    List<PascalParser.FormalParameterSectionContext> formalParameterSectionList = functionHeadingContext.formalParameterList().formalParameterSection();
                    for (PascalParser.FormalParameterSectionContext paramSection : formalParameterSectionList) {
                        // define parameter group in current scope of type Param(Type,String:label)
                        visit(paramSection);
                    }
                    // all formal params set up
                    Map<Object, TypeDescriptor> allParams = symbolTable.getAllVarInCurrentScope();
                    allParams.forEach((k, v) -> params.add(v));
                }
            }
        }

        Function function = new Function(params, resultType);
        define(id, function, ctx);
        //tableManager.displayAllTablesCurrentScope();

        visit(ctx.block());// scope & type checking in current func scope

        if (!functionHasReturnAssignment) {
            reportError(ctx, "Missing result assignment in Function: %s", function);
        }

        tableManager.allTablesExitNewScope();

        if (!isFunctionImpl) define(id, function, ctx);
        return null;
    }

    /**
     * functionDeclaration
     * : FUNCTION identifier (formalParameterList)? COLON resultType SEMI block #functionDecl
     * | functionHeading SEMI directive #functionPrototypeDecl
     * | FUNCTION identifier SEMI block #functionImpl
     * ;
     *
     * @param ctx
     * @return
     */
    @Override
    public TypeDescriptor visitFunctionPrototypeDecl(PascalParser.FunctionPrototypeDeclContext ctx) {
        String id = ctx.functionHeading().identifier().getText();
        TypeDescriptor resultType = visit(ctx.functionHeading().resultType());
        ArrayList<TypeDescriptor> params = new ArrayList<>();

        tableManager.allTablesEnterNewScope();

        // if the procedure has formal parameters
        PascalParser.FormalParameterListContext formalParameterListContext = ctx.functionHeading().formalParameterList();
        if (formalParameterListContext != null) {
            List<PascalParser.FormalParameterSectionContext> formalParameterSectionList = formalParameterListContext.formalParameterSection();
            for (PascalParser.FormalParameterSectionContext paramSection : formalParameterSectionList) {
                // define parameter group in current scope of type Param(Type,String:label)
                visit(paramSection);
            }
            // all formal params set up
            Map<Object, TypeDescriptor> allParams = symbolTable.getAllVarInCurrentScope();
            allParams.forEach((k, v) -> params.add(v));
        }
        Function function = new Function(params, resultType);
        define(id, function, ctx);

        //tableManager.displayAllTablesCurrentScope();

        tableManager.allTablesExitNewScope();
        define(id, function, ctx);

        // insert new entry into the tracking map
        // check whether there exist implementation corresponds to current function prototype declaration
        // Ignore Case
        prototypeImplTrackingMap.put(id.toLowerCase(), Pair.of(function, ctx));

        return null;
    }

    /**
     * Check single formal parameter and actual parameter
     * Whether these are the same or not
     *
     * @param formalParam
     * @param actualParam
     * @return
     */
    private boolean checkFormalAndActual(FormalParam formalParam, ActualParam actualParam) {
        if (formalParam.getLabel() == null) return formalParam.getHostType().equiv(actualParam.getHostType());
        if (!(formalParam.getLabel().equals(actualParam.getLabel()))) return false;
        return formalParam.getHostType().equiv(actualParam.getHostType());
    }

    /**
     * @param signature
     * @param actualParameters
     * @return BooleanMessage - contains {boolean flag, List<String> messageSequence}
     */
    private ErrorMessage checkSignature(TypeDescriptor signature, List<PascalParser.ActualParameterContext> actualParameters, ParserRuleContext ctx) {
        String name;
        if (ctx instanceof PascalParser.ProcedureStatementContext) {
            name = ((PascalParser.ProcedureStatementContext) ctx).identifier().getText();
        } else {
            name = ((PascalParser.FunctionDesignatorContext) ctx).identifier().getText();
        }
        ErrorMessage errorMessage = new ErrorMessage();
        // default - set the flag to false, i.e. exist errors to report
        //errorMessage.setFlag(false);
        List<TypeDescriptor> formalParameters = new ArrayList<>();
        if (signature instanceof Procedure) {
            formalParameters.addAll(((Procedure) signature).getFormalParams());
            //formalParameters = ((Procedure) signature).getFormalParams();
        }
        if (signature instanceof Function) {
            formalParameters.addAll(((Function) signature).getFormalParams());
        }

        //List<Type> formalParameters = signature.getFormalParams();

        StringBuilder stringBuilder = new StringBuilder();
        // if actual parameters length cannot match the formal parameters, directly return the error message
        if (actualParameters.size() != formalParameters.size()) {
            //List<TypeDescriptor> actualParametersContent = new ArrayList<>();
            stringBuilder.append(String.format(
                    "The number of actual parameters cannot match the signature of [%s]!",
                    name));
            stringBuilder.append(String.format("\nExpected: [size: %d],", formalParameters.size()));

            formalParameters.forEach(each -> {
                stringBuilder.append("\n- ").append(each);
            });
            stringBuilder.append(String.format("\nActual: [%s - size: %d]", ctx.getText(), actualParameters.size()));
            actualParameters.forEach(each -> {
                stringBuilder.append("\n- ").append(visit(each));
            });

            errorMessage.setMessageSequence(stringBuilder);
        } else {
            for (int i = 0; i < formalParameters.size(); i++) {
                TypeDescriptor formal = formalParameters.get(i);
                TypeDescriptor actual = visit(actualParameters.get(i));
                ActualParam _actual = (ActualParam) actual;
                FormalParam _formal = (FormalParam) formal;
                if (!checkFormalAndActual(_formal, _actual)) {
                    if (stringBuilder.length() == 0) {
                        stringBuilder.append(String.format("Actual parameter cannot match the formal parameter of [%s]!", name));
                    }
                    stringBuilder.append(String.format(
                            "\n- Pos[%d]\n" +
                                    " Expected: %s,\nActual: %s",
                            i, _formal, _actual
                    ));
                }
            }
            errorMessage.setMessageSequence(stringBuilder);
        }
        return errorMessage;
    }

    private boolean isProcedure(TypeDescriptor type) {
        return type instanceof Procedure || type instanceof RuntimeProcedure;
    }

    /**
     * procedureStatement
     * : identifier (LPAREN parameterList RPAREN)?
     * ;
     *
     * @param ctx
     * @return
     */
    @Override
    public TypeDescriptor visitProcedureStatement(PascalParser.ProcedureStatementContext ctx) {
        // if trivial procedure statement
        if (ctx.identifier() != null) {
            String id = ctx.identifier().getText();
            TypeDescriptor signature = retrieve(id, ctx);
            symbolTable.displayCurrentScope();

            // suppress error, proc identifier not defined
            if (signature.equiv(ErrorType.UNDEFINED_TYPE)) {
                return null;
            }

            TypeDescriptor _signature = signature;
            // if Procedure itself as parameter
            if (signature instanceof FormalParam) {
                _signature = ((FormalParam) signature).getHostType();
            }

            // Only report while proc id is defined but is used with other type
            if (!(_signature.equiv(ErrorType.UNDEFINED_TYPE)) && !isProcedure(_signature)) {
                reportError(ctx, "Illegal procedure statement [%s] which is not a procedure",
                        ctx.getText());
                return ErrorType.INVALID_PROCEDURE_TYPE;
            } else if (isProcedure(_signature)) {

                //check signatures if has actual parameters
                List<PascalParser.ActualParameterContext> actualParameterContextList = new ArrayList<>();
                if (ctx.parameterList() != null) {
                    actualParameterContextList = ctx.parameterList().actualParameter();
                }
                actualParameterContextList.forEach(each -> {
                });

                // if is a trivial procedure
                if (_signature instanceof Procedure) {
                    ErrorMessage errorMessage = checkSignature(_signature, actualParameterContextList, ctx);
                    if (errorMessage.hasErrors()) {
                        reportError(ctx, errorMessage.getMessageSequence());
                    }
                } else if (_signature instanceof RuntimeProcedure) {

                    // check signature of runtime procedure
                    ArrayList<Class<? extends TypeDescriptor>> _acutalParams = new ArrayList<>();
                    actualParameterContextList.forEach(each -> {
                        TypeDescriptor actual = visit(each);
                        if (actual instanceof ActualParam) {
                            Class<? extends TypeDescriptor> aClass = ((ActualParam) actual).getHostType().getClass();
                            _acutalParams.add(aClass);
                        }
                    });
                    Set<List<Class<? extends TypeDescriptor>>> formalParamsMap = ((RuntimeProcedure) _signature).getFormalParamsMap();
                    // only check if runtime proc/func has signatures defined
                    if (formalParamsMap != null && !formalParamsMap.contains(_acutalParams)) {
                        StringBuilder sb = new StringBuilder(String.format("Actual parameter cannot match the formal parameter of the runtime procedure [%s].\nGiven actual parameters:\n%s.\nAvailable signatures:", id, _acutalParams));
                        formalParamsMap.forEach(each -> {
                            sb.append(String.format("\n%s", each));
                        });
                        reportError(ctx, sb.toString());
                    }
                }
            }
        }

        // if io procedure
        if (ctx.writeProcedureStatement() != null) {
            visit(ctx.writeProcedureStatement());
        }
        if (ctx.readProcedureStatement() != null) {
            visit(ctx.readProcedureStatement());
        }
        return null;
    }

    @Override
    public TypeDescriptor visitReadProcedureStatement(PascalParser.ReadProcedureStatementContext ctx) {
        visit(ctx.readParameters());
        return null;
    }

    @Override
    public TypeDescriptor visitReadParameters(PascalParser.ReadParametersContext ctx) {
        // if no operation here, token would be missing...
        for (PascalParser.InputValueContext each : ctx.inputValue()) {
            String id = each.variable().variableHead().getText().toLowerCase();
            retrieve(id, ctx);
        }
        return null;
    }

    @Override
    public TypeDescriptor visitWriteProcedureStatement(PascalParser.WriteProcedureStatementContext ctx) {
        StringBuilder sb = null;
        int pos = 0;

        // non-args write,directly return
        if (ctx.writeParameters() == null) return null;

        List<PascalParser.OutputValueContext> outputValueContexts = ctx.writeParameters().outputValue();
        for (PascalParser.OutputValueContext each : outputValueContexts) {
            List<PascalParser.ExpressionContext> expressionContexts = each.expression();
            for (PascalParser.ExpressionContext eachExpr : expressionContexts) {
                TypeDescriptor outputType = visit(eachExpr);
                TypeDescriptor _outputType = outputType;
                if (outputType instanceof FormalParam) {
                    _outputType = ((FormalParam) outputType).getHostType();
                }
                if (outputType instanceof Function) {
                    _outputType = ((Function) outputType).getResultType();
                }
                if (!isSimpleType(_outputType) && !(isStringType(_outputType))) {
                    if (sb == null) {
                        sb = new StringBuilder();
                        sb.append(
                                String.format("Invalid write/writeln call [%s]", ctx.getText())
                        );
                    }
                    sb.append(String.format("\n- Pos [%d] with type: %s", pos++, outputType));
                }
            }
            //TypeDescriptor outputType = visit(each);
        }
        ErrorMessage errorMessage = new ErrorMessage(sb);
        if (errorMessage.hasErrors()) {
            reportError(ctx, errorMessage.getMessageSequence());
        }
        return null;
    }

    private boolean isFunction(TypeDescriptor type) {
        return type instanceof Function || type instanceof RuntimeFunction;
    }

    /**
     * Function Call
     * functionDesignator
     * : identifier LPAREN parameterList RPAREN
     * ;
     *
     * @param ctx
     * @return
     */
    @Override
    public TypeDescriptor visitFunctionDesignator(PascalParser.FunctionDesignatorContext ctx) {
        String id = ctx.identifier().getText();
        tableManager.displayAllTablesCurrentScope();
        //suppress errors
        TypeDescriptor signature = retrieve(id, false, ctx);
        TypeDescriptor _signature = signature;

        tableManager.displayAllTablesCurrentScope();

        // report error in upper node
        if (signature.equiv(ErrorType.UNDEFINED_TYPE)) {
            return signature;
        }

        // if nested type(FormalParam), extract the hostType
        // i.e. Function itself as parameter
        if (signature instanceof FormalParam) {
            _signature = ((FormalParam) signature).getHostType();
        }

        // Only report while function id is defined but is used with other type: proc .etc
        if (!(_signature.equiv(ErrorType.UNDEFINED_TYPE)) && !isFunction(_signature)) {
            reportError(ctx, "%s is not a valid function.", id);
            return ErrorType.INVALID_FUNCTION_TYPE;
        }

        // if is trivial function
        if (_signature instanceof Function) {
            //check signatures
            List<PascalParser.ActualParameterContext> actualParameterContextList = new ArrayList<>();
            if (ctx.parameterList() != null) {
                actualParameterContextList = ctx.parameterList().actualParameter();
            }
            ErrorMessage errorMessage = checkSignature(_signature, actualParameterContextList, ctx);
            if (errorMessage.hasErrors()) {
                reportError(ctx, errorMessage.getMessageSequence());
            }
            return ((Function) _signature).getResultType();
        }

        // if Runtime function
        if (_signature instanceof RuntimeFunction) {
            // check signature of runtime procedure
            ArrayList<Class<? extends TypeDescriptor>> _acutalParams = new ArrayList<>();
            List<PascalParser.ActualParameterContext> actualParameterContextList = null;
            if (ctx.parameterList() != null) {
                actualParameterContextList = ctx.parameterList().actualParameter();
            }
            if (actualParameterContextList != null) {
                actualParameterContextList.forEach(each -> {
                    TypeDescriptor actual = visit(each);
                    if (actual instanceof ActualParam) {
                        Class<? extends TypeDescriptor> aClass = ((ActualParam) actual).getHostType().getClass();
                        _acutalParams.add(aClass);
                    }
                });
            }

            Set<List<Class<? extends TypeDescriptor>>> formalParamsMap = ((RuntimeFunction) _signature).getFormalParamsMap();
            TypeDescriptor resultType = ((RuntimeFunction) _signature).getResultType();
            // only check if runtime proc/func has signatures defined
            if (formalParamsMap != null && !formalParamsMap.contains(_acutalParams)) {
                StringBuilder sb = new StringBuilder(String.format("Actual parameter cannot match the formal parameter of the runtime function [%s].\nGiven actual parameters:\n%s.\nAvailable signatures:", id, _acutalParams));
                formalParamsMap.forEach(each -> {
                    sb.append(String.format("\n%s", each));
                });
                reportError(ctx, sb.toString());
            }

            return resultType;
        }
        return null;
    }

    /**
     * parameterList
     * : actualParameter (COMMA actualParameter)*
     * ;
     * <p>
     * Used in FunctionDesignator(Func call) & ProcedureStatement(Proc Call)
     * Detailed processing is handled to the parent node Func/Proc call
     *
     * @param ctx
     * @return
     */
    @Override
    public TypeDescriptor visitParameterList(PascalParser.ParameterListContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * actualParameter
     * : expression parameterwidth*
     * ;
     * expression
     * : simpleExpression (relationaloperator e2=expression)?
     * ;
     * simpleExpression
     * : term (additiveoperator simpleExpression)?
     * ;
     * <p>
     * term
     * : signedFactor (multiplicativeoperator term)?
     * ;
     * <p>
     * signedFactor
     * : monadicOp=(PLUS | MINUS)? factor
     * ;
     * factor
     * : variable   # factorVar
     * | LPAREN expression RPAREN #factorExpr
     * | functionDesignator #factorFuncDesignator
     * | unsignedConstant #factorUnConst
     * | set_ #factorSet
     * | NOT factor #notFactor
     * | bool_ #factorBool
     * ;
     * <p>
     * variable
     * : (AT identifier | identifier) (LBRACK expression (COMMA expression)* RBRACK | LBRACK2 expression (COMMA expression)* RBRACK2 | DOT identifier | POINTER)*
     * ;
     * <p>
     * ISO 7185
     * actual-parameter = expression | variable-access |
     * procedure-identifier | function-identifier
     * Note: parameterwidth not in standard Pascal
     *
     * @param ctx
     * @return
     */
    @Override
    public TypeDescriptor visitActualParameter(PascalParser.ActualParameterContext ctx) {
        TypeDescriptor actualParamType = visit(ctx.expression());
        String label = null;
        Token relationalOperator = ctx.expression().relationalOperator;
        Token additiveOperator = ctx.expression().simpleExpression().additiveOperator;
        Token multiplicativeOperator = ctx.expression().simpleExpression().term().multiplicativeOperator;
        Token monadicOperator = ctx.expression().simpleExpression().term().signedFactor().monadicOperator;

        // if no operator involved
        if (relationalOperator == null && additiveOperator == null &&
                multiplicativeOperator == null && monadicOperator == null) {
            PascalParser.FactorContext factorContext = ctx.expression().simpleExpression().term().signedFactor().factor();
            if (factorContext instanceof PascalParser.FactorVarContext) {
                // variable as actual parameter
                // TODO: FUNC, PROC as parameter not implemented
                label = "var";
            }
        }
        return new ActualParam(actualParamType, label);
    }

    /**
     * whileStatement
     * : WHILE expression DO statement
     * ;
     * Expression must be boolean
     *
     * @param ctx
     * @return
     */
    @Override
    public TypeDescriptor visitWhileStatement(PascalParser.WhileStatementContext ctx) {
        TypeDescriptor conditionType = visit(ctx.expression());
        if (!(conditionType instanceof Boolean)) {
            reportError(ctx, "Condition [%s: %s] of while statement must be boolean",
                    ctx.expression().getText(), conditionType);
        }
        visit(ctx.statement());
        return null;
    }

    /**
     * repeatStatement
     * : REPEAT statements UNTIL expression
     * ;
     * Expression must be boolean
     *
     * @param ctx
     * @return
     */
    @Override
    public TypeDescriptor visitRepeatStatement(PascalParser.RepeatStatementContext ctx) {
        TypeDescriptor exType = visit(ctx.expression());
        if (!(exType instanceof Boolean)) {
            reportError(ctx, "Expression [%s: %s] of" +
                            " Repeat Statement must be boolean",
                    ctx.expression().getText(), exType);
        }
        visit(ctx.statements());
        return null;
    }

    /**
     * forStatement
     * : FOR identifier ASSIGN forList DO statement
     * ;
     * <p>
     * forList
     * : initialValue (TO | DOWNTO) finalValue
     * ;
     * <p>
     * initialValue
     * : expression
     * ;
     * finalValue
     * : expression
     * ;
     * The type of control var must be ordinal type
     * The initialValue & finalValue must yield values
     * of the same ordinal type as the control var
     *
     * @param ctx
     * @return
     */
    @Override
    public TypeDescriptor visitForStatement(PascalParser.ForStatementContext ctx) {
        String forHeader = concatenateChildrenText(ctx, 0, 4);
        TypeDescriptor controlType = retrieve(ctx.identifier().getText(), ctx);
        if (!isOrdinalType(controlType)) {
            // form the text of FOR header
            reportError(ctx, "Control variable [%s: %s] of For " +
                            "statement [%s] must be Ordinal",
                    ctx.identifier().getText(), controlType.toString(), forHeader);
        } else {
            // check initialValue
            TypeDescriptor initType = visit(ctx.forList().initialValue());
            TypeDescriptor finalType = visit(ctx.forList().finalValue());
            if (!initType.equiv(controlType) ||
                    (initType instanceof FloatBaseType && controlType instanceof IntegerBaseType)) {
                // TODO: SUBRANGE CHECK, might need to introduce Enumerated type checking here
                if (controlType instanceof Subrange) {
                    if (!isValidSubrange((Subrange) controlType, initType)) {
                        reportError(ctx, "Initial Expression " +
                                        "of For Statement [%s] " +
                                        "must be the same ordinal type as control variable %s:\nExpected: %s,\nActual: %s",
                                forHeader,
                                ctx.identifier().getText(), controlType, initType);
                    }
                } else {
                    reportError(ctx, "Initial Expression " +
                                    "of For Statement [%s] " +
                                    "must be the same ordinal type as control variable %s:\nExpected: %s,\nActual: %s",
                            forHeader,
                            ctx.identifier().getText(), controlType, initType);
                }
            } else if (!finalType.equiv(controlType) ||
                    (finalType instanceof FloatBaseType && controlType instanceof IntegerBaseType)) {
                // TODO: SUBRANGE CHECK, might need to introduce Enumerated type checking here
                if (controlType instanceof Subrange) {
                    if (!isValidSubrange((Subrange) controlType, finalType)) {
                        reportError(ctx, "Final Expression" +
                                        "of For Statement [%s] " +
                                        "must be the same ordinal type as control variable %s:\nExpected: %s,\nActual: %s",
                                forHeader,
                                ctx.identifier().getText(), controlType, finalType);
                    }
                } else {
                    reportError(ctx, "Final Expression" +
                                    "of For Statement [%s] " +
                                    "must be the same ordinal type as control variable %s:\nExpected: %s,\nActual: %s",
                            forHeader,
                            ctx.identifier().getText(), controlType, finalType);
                }
            }
        }

        visit(ctx.statement());
        return null;
    }


    private String concatenateChildrenText(PascalParser.ForStatementContext ctx, int start, int end) {
        StringBuilder sb = new StringBuilder();
        for (int i = start; i < end; i++) {
            sb.append(ctx.getChild(i).getText()).append(" ");
        }
        return sb.toString();
    }

    TypeDescriptor structuredTypeToBeCheckNext;
    String structuredTypeId;

    /**
     * This node is only for structuredType
     * Used to reset the global indexCount, for tracking current index of an array
     * Also, check and return the correct Type of record field designator
     *
     * <p>
     * variable
     * : variableHead
     * (
     * arrayScripting
     * | fieldDesignator
     * | POINTER
     * )*
     * </p>
     *
     * @param ctx
     * @return
     */
    @Override
    public TypeDescriptor visitVariable(PascalParser.VariableContext ctx) {
        structuredTypeId = ctx.variableHead().identifier().getText();
        // retrieve current
        structuredTypeToBeCheckNext = retrieve(structuredTypeId, ctx);
        if (structuredTypeToBeCheckNext instanceof NestedBaseType) {
            structuredTypeToBeCheckNext = ((NestedBaseType) structuredTypeToBeCheckNext).getHostType();
        }
        indexCount = 0;
        //List<PascalParser.ArrayScriptingContext> arrayScriptingContexts = ctx.arrayScripting();
        //if (!arrayScriptingContexts.isEmpty()) {
        //    return super.visitVariable(ctx);
        //}

        List<ParseTree> children = ctx.children;
        ParseTree variableHead = children.get(0);
        boolean returnAddressType = false;
        if (variableHead instanceof PascalParser.VariableHeadContext) {
            if (((PascalParser.VariableHeadContext) variableHead).AT() != null) {
                returnAddressType = true;
            }
        }
        // determine after examine all the fielding/indexing operations
        // whether to return the address () of the variable

        // remove the variableHead node, the remaining nodes should be processed circularly
        if (!children.isEmpty()) children.remove(0);

        // if any errors, report in this node and throw errorType to upper node, suppressing the errors
        for (ParseTree eachChild : children) {

            if (eachChild instanceof TerminalNode) {
                Token symbol = ((TerminalNode) eachChild).getSymbol();
                if (symbol.getText().equals("^")) {
                    if (structuredTypeToBeCheckNext instanceof PointerType) {
                        structuredTypeToBeCheckNext = ((PointerType) structuredTypeToBeCheckNext).getPointedType();
                    } else {
                        structuredTypeToBeCheckNext = ErrorType.INVALID_DEREFERENCE_OPERATION;
                        reportError(ctx, "Invalid dereference operation on identifier [%s] with type %s",
                                structuredTypeId, structuredTypeToBeCheckNext);
                    }
                }
            } else structuredTypeToBeCheckNext = visit(eachChild);


            if (structuredTypeToBeCheckNext instanceof ErrorType) {
                return structuredTypeToBeCheckNext;
            }
        }
        if (returnAddressType) {
            structuredTypeToBeCheckNext = new AddressType();
        }

        return structuredTypeToBeCheckNext;
    }


    /**
     * Used for array scripting, several expressions takes up one arrayScripting node
     * There are might be several arrayScripting nodes
     * <p>
     * There are three types of scripting:
     * ! arr[1]
     * ! arr[1][0]
     * ! arr[1,0]
     * <p>
     * ? Perform array index range checking
     *
     * <p>
     * variable
     * : (AT identifier | identifier)
     * (
     * arrayScripting
     * | DOT identifier
     * | POINTER
     * )*
     * </p>
     *
     * <p>
     * arrayScripting
     * : LBRACK expression (COMMA expression)* RBRACK // array scripting
     * | LBRACK2 expression (COMMA expression)* RBRACK2 // array scripting
     * ;
     * </p>
     *
     * @param ctx
     * @return
     */
    @Override
    public TypeDescriptor visitArrayScripting(PascalParser.ArrayScriptingContext ctx) {
        List<PascalParser.ExpressionContext> expression = ctx.expression();
        if (structuredTypeToBeCheckNext instanceof ArrayType
                || structuredTypeToBeCheckNext instanceof FormalParam) {

            for (PascalParser.ExpressionContext eachExpr : expression) {
                ArrayType _arrayType = null;
                if (structuredTypeToBeCheckNext instanceof ArrayType)
                    _arrayType = (ArrayType) structuredTypeToBeCheckNext;
                if (structuredTypeToBeCheckNext instanceof FormalParam) {
                    if (((FormalParam) structuredTypeToBeCheckNext).getHostType() instanceof ArrayType) {
                        _arrayType = (ArrayType) ((FormalParam) structuredTypeToBeCheckNext).getHostType();
                    } else {
                        reportError(ctx, "Invalid array scripting operation [%s]. [%s] is not an array type.\nActual: %s",
                                ctx.parent.getText(), structuredTypeId, structuredTypeToBeCheckNext);
                        return ErrorType.INVALID_ARRAY_TYPE;
                    }
                }

                List<TypeDescriptor> indexList = _arrayType.getIndexList();
                structuredTypeToBeCheckNext = getArrayContent(0, _arrayType);
                // there are various combinations of indexing,
                // but if number of expr > size of indexList, directly report error
                //if (expression.size() > indexList.size() || indexCount > indexList.size()) {
                //    reportError(ctx, "Invalid array scripting operation [%s]. Operations dimension cannot match the declaration [%s]",
                //            ctx.parent.getText(), structuredTypeToBeCheckNext);
                //    return ErrorType.INVALID_ARRAY_SCRIPTING;
                //}
                TypeDescriptor expressionType = visit(eachExpr);
                TypeDescriptor declaredType = indexList.get(0);//get first

                if (!expressionType.equiv(declaredType) || (
                        declaredType instanceof Character && expressionType instanceof StringLiteral
                )) {
                    // updated if match to perform further checking
                    if (expressionType instanceof StringLiteral) {
                        String stringContent = ((StringLiteral) expressionType).getValue().replace("'", "");
                        if (stringContent.length() == 1) continue;
                    }

                    if (declaredType instanceof Subrange) {
                        if (isValidSubrange((Subrange) declaredType, expressionType)) {
                            continue;
                        }

                        // right expression evaluated to null, i.e. RUNTIME CHECK
                        //FIXME
                        if (expressionType instanceof IntegerBaseType &&
                                (((IntegerBaseType) expressionType).getValue() == null)
                        ) {
                            //return null;
                            continue;
                        }
                    }

                    reportError(ctx, "Illegal operation [%s%s] with invalid array scripting [%s].\nExpected: %s.\nActual: %s",
                            structuredTypeId, ctx.parent.getText(), ctx.getText(), declaredType, expressionType);
                    //return ErrorType.INVALID_ARRAY_SCRIPTING;
                    return structuredTypeToBeCheckNext;
                }
            }
            return structuredTypeToBeCheckNext;
        }
        reportError(ctx, "Illegal array scripting operation [%s] on the type: %s",
                ctx.parent.getText(), structuredTypeToBeCheckNext);
        return ErrorType.INVALID_ARRAY_TYPE;
    }

    /**
     * @param recordType - record type to be check
     * @param fieldName  - field name
     * @return ErrorType - is invalid, TypeDescriptor - is valid
     */
    private TypeDescriptor isRecordFieldDesignatorValid(TypeDescriptor recordType, String fieldName) {
        String _fieldName = fieldName.toLowerCase();
        if (recordType instanceof RecordType || recordType instanceof FormalParam) {
            TypeDescriptor _recordType = recordType;
            if (recordType instanceof FormalParam) {
                _recordType = ((FormalParam) recordType).getHostType();
            }
            if (_recordType instanceof RecordType) {
                Map<String, TypeDescriptor> fieldsMap = ((RecordType) _recordType).getFieldsMap();
                return fieldsMap.getOrDefault(_fieldName, ErrorType.INVALID_RECORD_FIELD);
            }
        }
        return ErrorType.INVALID_RECORD_FIELD;
    }

    /**
     * Represents the record field designator
     * Check whether the field reference is valid or not
     *
     * <p>
     * fieldDesignator
     * : DOT identifier
     * ;
     * </p>
     *
     * @param ctx
     * @return
     */
    @Override
    public TypeDescriptor visitFieldDesignator(PascalParser.FieldDesignatorContext ctx) {
        String fieldName = ctx.identifier().getText();
        Map<String, TypeDescriptor> fieldsMap = null;
        TypeDescriptor recordFieldDesignatorValid = isRecordFieldDesignatorValid(structuredTypeToBeCheckNext, fieldName);
        if (recordFieldDesignatorValid instanceof ErrorType) {
            reportError(ctx, "Illegal operation [%s%s] with invalid record field [%s].",
                    structuredTypeId, ctx.parent.getText(), ctx.getText()
            );
        }
        return recordFieldDesignatorValid;
    }

    private ErrorMessage isValidStringAssignment(ArrayType left, StringLiteral right, String ctx) {
        StringBuilder stringBuilder = new StringBuilder();
        ErrorMessage errorMessage = new ErrorMessage(stringBuilder);
        TypeDescriptor componentType = (left).getComponentType();
        if (left.isPacked() && componentType instanceof Character) {
            TypeDescriptor type = left.getIndexList().get(0);
            if (type instanceof Subrange) {
                TypeDescriptor lowerBound = ((Subrange) type).getLowerBound();
                TypeDescriptor upperBound = ((Subrange) type).getUpperBound();
                // calculate the expected string length
                Long expectedLength = null;
                if (lowerBound instanceof IntegerBaseType && upperBound instanceof IntegerBaseType) {
                    Long lowerValue = ((IntegerBaseType) lowerBound).getValue();
                    Long upperValue = ((IntegerBaseType) upperBound).getValue();
                    if (lowerValue != 1) {
                        stringBuilder.append(String.format("Illegal assignment [%s] with incompatible types.\nExpected(lType): %s,\nActual(rType): %s",
                                ctx, left, right));
                        return errorMessage;// valid string type index must start from 1}
                    }
                    expectedLength = upperValue - lowerValue + 1;
                    // check whether right operand is a string type with the same length
                    int actualLength = right.getValue().replace("'", "").length();
                    if (expectedLength.intValue() != actualLength) {
                        stringBuilder.append(String.format("Illegal string assignment [%s] with incompatible length.\nExpected: %s,\nActual: %s",
                                ctx, expectedLength, actualLength));
                    }
                }
            }
        } else {
            stringBuilder.append(String.format("Illegal assignment [%s] with incompatible types.\nExpected(lType): %s,\nActual(rType): %s",
                    ctx, left, right));
        }
        return errorMessage;
    }

    @Override
    public TypeDescriptor visitAssignmentStatement(PascalParser.AssignmentStatementContext ctx) {
        String assignmentCtx = ctx.getText();

        String id = ctx.variable().variableHead().getText();
        String expression = ctx.expression().getText();


        // suppress errors
        TypeDescriptor leftType = retrieve(id, false, ctx);
        TypeDescriptor _leftType = leftType;

        // extract the host type of nested type - formal parameter
        if (leftType instanceof FormalParam) {
            _leftType = ((FormalParam) leftType).getHostType();
        }

        if (_leftType instanceof Function &&
                id.toLowerCase().equals(selectedFunction.toLowerCase())) {
            functionHasReturnAssignment = true;
        }

        // if left is structured type: array, records, file, set
        // or left is pointer type
        if (_leftType instanceof StructuredBaseType || _leftType instanceof PointerType) {
            // check whether array scripting is valid, if any
            _leftType = visit(ctx.variable());
        }

        if (_leftType.equiv(ErrorType.UNDEFINED_TYPE) && !typeTable.containsKey(id)) {
            reportError(ctx, "%s is undeclared", id);
            return null;
        }

        // suppress errors as already reported in other nodes
        //if (_leftType.equiv(ErrorType.INVALID_ARRAY_SCRIPTING)
        //        || _leftType.equiv(ErrorType.INVALID_RECORD_FIELD)) {
        //    return null;
        //}

        // if left identifier is an defined type identifier or enumerated type identifier
        // report errors
        if (_leftType instanceof EnumeratedIdentifier || typeTable.containsKey(id)) {
            if (_leftType.equiv(ErrorType.UNDEFINED_TYPE)) _leftType = typeTable.get(id);
            reportError(ctx, "Illegal assignment [%s]. Assigning value to identifier [%s] is not allowed.\n Type: %s",
                    ctx.getText(), id, _leftType);
            return null;
        }

        TypeDescriptor rightType = visit(ctx.expression());
        if (rightType instanceof FormalParam) {
            rightType = ((FormalParam) rightType).getHostType();
        }

        // if expected enumerated type, suppress errors
        // i.e. skip visit expression node in trivial way
        // check whether right identifier is valid in the predefined value Map
        if (_leftType instanceof EnumeratedType) {
            EnumeratedType __leftType = (EnumeratedType) _leftType;
            Map<String, Integer> valueMap = __leftType.getValueMap();

            String expressionValue = null;

            if (rightType instanceof EnumeratedIdentifier) {
                expressionValue = ((EnumeratedIdentifier) rightType).getValue();
            } else if (rightType instanceof EnumeratedType) {
                if (__leftType.equiv(rightType)) return null;
            } else if (rightType instanceof ErrorType) {
                //    suppress errors
                //return null;
            } else {
                expressionValue = expression.toLowerCase();
            }

            if (!valueMap.containsKey(expressionValue)) {
                //TypeDescriptor typeDescriptor = symbolTable.get(expression.toLowerCase());
                TypeDescriptor typeDescriptor = retrieve(expression.toLowerCase(), false, ctx);

                // if enumerated constant defined
                if (typeDescriptor instanceof EnumeratedIdentifier) {
                    EnumeratedIdentifier constant = (EnumeratedIdentifier) typeDescriptor;
                    if (constant.isConstant() && valueMap.containsKey(constant.getValue())) return null;
                }
                if (typeDescriptor.equiv(ErrorType.UNDEFINED_TYPE)) {
                    reportError(ctx, "Illegal enumerated type assignment [%s]. Right operand [%s] is not valid.\nExpected: %s,\nActual: %s",
                            assignmentCtx, expression, valueMap.keySet(), rightType);
                } else
                    reportError(ctx, "Illegal enumerated type assignment [%s]. Right operand [%s] is not valid.\nExpected: %s,\nActual: %s",
                            assignmentCtx, expression, valueMap.keySet(), typeDescriptor);
            }
            return null;
        }

        if (rightType.equiv(ErrorType.UNDEFINED_TYPE)) {
            reportError(ctx, "Illegal assignment [%s]. Right operand [%s] is not defined.",
                    assignmentCtx, ctx.expression().getText());
            return null;
        }

        if (rightType.equiv(ErrorType.INVALID_EXPRESSION)
                || rightType.equiv(ErrorType.INVALID_ARRAY_SCRIPTING)) {
            // suppress errors in this node (error already reported in other nodes)
            return null;
        }

        if (_leftType instanceof BaseType) {
            boolean isConstant = ((BaseType) _leftType).isConstant();
            if (isConstant) {
                reportError(ctx, "Illegal assignment [%s], constant value reassigning is not allowed",
                        assignmentCtx);
                return null;
            }
        }

        if (rightType.equiv(ErrorType.INVALID_MONADIC_OPERATION)) {
            reportError(ctx, "Illegal assignment [%s], monadic operator cannot be applied on right operand: %s.\nRtype: %s",
                    assignmentCtx, expression, rightType);
            return null;
        }

        //Check whether value is assigned to function in non-function body
        if (_leftType instanceof Function && (ctx.parent.parent.parent.parent.parent
                .parent.parent) instanceof PascalParser.ProgramContext) {
            reportError(ctx, "Illegal assignment [%s], assigning value to a function is not allowed here: %s ",
                    assignmentCtx, _leftType);
            return null;
        }

        // cannot assign value to a procedure both in trivial assignment
        // and within procedure body
        if (_leftType instanceof Procedure) {
            reportError(ctx, "Illegal assignment [%s], assigning value to a procedure is not allowed: %s ",
                    assignmentCtx, leftType);
            return null;
        }

        if (rightType instanceof Procedure) {
            reportError(ctx, "Illegal assignment [%s], %s returns no result",
                    assignmentCtx, rightType);
            return null;
        }

        if (rightType.equiv(ErrorType.INVALID_CONSTANT_TYPE) ||
                rightType.equiv(ErrorType.INTEGER_OVERFLOW) ||
                rightType.equiv(ErrorType.INTEGER_UNDERFLOW)
        ) {
            reportError(ctx, "Illegal assignment [%s] with invalid constant right operand [%s] " +
                            "which must be between [%d] and [%d].\nRtype:%s",
                    assignmentCtx, expression, defaultIntegerType.MIN_VALUE, defaultIntegerType.MAX_VALUE,
                    rightType);
            return null;
        }

        // right operand defined but is not a function
        if (rightType.equiv(ErrorType.INVALID_FUNCTION_TYPE)) {
            reportError(ctx, "Illegal assignment [%s]. Right operand [%s] is not a function",
                    assignmentCtx, expression);
            return null;
        }

        if (_leftType instanceof Subrange) {
            if (isValidSubrange((Subrange) _leftType, rightType)) return null;
            // right expression evaluated to null, i.e. RUNTIME CHECK
            if (rightType instanceof IntegerBaseType && ((IntegerBaseType) rightType).getValue() == null) {
                return null;
            }

            reportError(ctx, "invalid subrange [%s],\nExpected: %s,\nActual: %s",
                    assignmentCtx, _leftType, rightType);
            return null;
        }

        // check when left is a [packed] character array [index start from 1] where expected a string type
        // direct string type assignment where the string must has the same length
        if (_leftType instanceof ArrayType && rightType instanceof StringLiteral) {
            ErrorMessage validStringAssignment = isValidStringAssignment((ArrayType) _leftType, (StringLiteral) rightType, assignmentCtx);
            if (validStringAssignment.hasErrors())
                reportError(ctx, validStringAssignment.getMessageSequence().toString());
            return null;
        }

        if (!_leftType.equiv(rightType)) {
            // FIXME? this already put in to the class itself?
            // exception case when left is real, it is acceptable though right is int
            //if (_leftType.equiv(Type.REAL) && rightType.equiv(Type.INTEGER)) return null;
            //// exception case when left is character, and right is string literal(length must be 1)
            //if (_leftType.equiv(Type.CHARACTER) && rightType.equiv(Type.STRING)) {
            //    String content = expression.replace("'", "");
            //    if (content.length() == 1) return null;
            //}

            // check result type of a Function
            // this assignment only allowed within Function body
            if (_leftType instanceof Function) {
                Function function = (Function) _leftType;
                TypeDescriptor resultType = function.getResultType();
                if (!resultType.equiv(rightType)) {

                    //TODO realint, charstr - func
                    // exception case when left is real, it is acceptable though right is int
                    //if (resultType.equiv(Type.REAL) && rightType.equiv(Type.INTEGER)) return function;
                    //// exception case when left is character, and right is string literal(length must be 1)
                    //if (resultType.equiv(Type.CHARACTER) && rightType.equiv(Type.STRING)) {
                    //    String content = expression.replace("'", "");
                    //    if (content.length() == 1) return function;
                    //}

                    reportError(ctx, "Illegal assignment: [%s]. Right operand cannot be assigned to function [%s].\nExpected: %s,\nActual: %s",
                            assignmentCtx, id, resultType, rightType);
                }
                return function; // return Function itself for further check in the upper nodes
            }

            // check type compatibility when right operand is evaluated from function
            if (rightType instanceof Function) {
                Function function = (Function) rightType;
                TypeDescriptor resultType = function.getResultType();
                if (!_leftType.equiv(resultType)) {

                    //// exception case when left is real, it is acceptable though right is int
                    //if (_leftType.equiv(Type.REAL) && resultType.equiv(Type.INTEGER)) return function;
                    //// exception case when left is character, and right is string literal(length must be 1)
                    //if (_leftType.equiv(Type.CHARACTER) && resultType.equiv(Type.STRING)) {
                    //    String content = expression.replace("'", "");
                    //    if (content.length() == 1) return function;
                    //}

                    reportError(ctx, "Illegal assignment [%s]. Function [%s] result type cannot match the left operand.\nExpected: %s, Actual: %s",
                            assignmentCtx, id, _leftType, resultType);
                }
                return null;
            }

            reportError(ctx, "Illegal assignment [%s] with incompatible types.\nExpected(lType): %s,\nActual(rType): %s",
                    assignmentCtx, _leftType.toString(), rightType.toString());
        }
        return null;
    }

    private boolean isValidSubrange(Subrange leftType, TypeDescriptor rightType) {
        Class<? extends TypeDescriptor> hostType = leftType.getHostType();
        TypeDescriptor lowerBound = leftType.getLowerBound();
        TypeDescriptor upperBound = leftType.getUpperBound();

        if (rightType instanceof Subrange) {
            return leftType.equiv(rightType);
        }

        // check whether rightType is valid value of the enumerated subrange
        if (hostType == EnumeratedType.class) {
            // if right is not a enumerated value, directly return false
            if (!(rightType instanceof EnumeratedIdentifier)) return false;
            // perform further checking, whether right value is in the valueMap
            EnumeratedIdentifier enumLowerBound = (EnumeratedIdentifier) lowerBound;
            Map<String, Integer> valueMap = enumLowerBound.getBelongsTo().getValueMap();
            String rightValue = ((EnumeratedIdentifier) rightType).getValue();
            return valueMap.containsKey(rightValue.toLowerCase());
        }

        // check other ordinal types
        if (rightType.getClass() == hostType ||
                (rightType.getClass() == IntegerBaseType.class && hostType == Integer32.class)) {
            if (rightType instanceof Boolean) {
                java.lang.Boolean rightValue = ((Boolean) rightType).getValue();
                java.lang.Boolean lowerValue = ((Boolean) lowerBound).getValue();
                java.lang.Boolean upperValue = ((Boolean) upperBound).getValue();

                // if right is null, then would be checked at the runtime
                if (rightValue==null) return true;

                return (rightValue == lowerValue || rightValue == upperValue);
            }

            if (rightType instanceof IntegerBaseType) {
                Long rightValue = ((IntegerBaseType) rightType).getValue();

                // right expression evaluated to null, i.e. RUNTIME CHECK
                if (rightValue == null) {
                    return true;
                }

                Long lowerValue = ((IntegerBaseType) lowerBound).getValue();
                Long upperValue = ((IntegerBaseType) upperBound).getValue();
                return rightValue >= lowerValue && rightValue <= upperValue;
            }

            // char subrange
            if (rightType instanceof Character) {
                java.lang.Character rightValue = ((Character) rightType).getValue();
                java.lang.Character lowerValue = ((Character) lowerBound).getValue();
                java.lang.Character upperValue = ((Character) upperBound).getValue();
                //if null, checked at the runtime
                if (rightValue == null) return true;
                return rightValue >= lowerValue && rightValue <= upperValue;
            }
        } else if (hostType == Character.class && rightType instanceof Character) {
            return true;
        }
        return false;
    }

    /**
     * ifStatement
     * : IF expression THEN statement (: ELSE statement)?
     *
     * @param ctx
     * @return
     */
    @Override
    public TypeDescriptor visitIfStatement(PascalParser.IfStatementContext ctx) {
        TypeDescriptor type = visit(ctx.expression());
        if (!(type instanceof Boolean)) {
            reportError(ctx,
                    "Invalid condition type of if statement [%s] which must be boolean type.\nActual: %s",
                    ctx.expression().getText(), type);
        }
        ctx.statement().forEach(this::visit);
        return null;
    }

    /**
     * Case statement:
     * <P>
     * Valid constant as expression:
     * - character(size 1 stringliteral)
     * - int
     * - boolean
     * - subrange
     * - enumerated types
     * </P>
     *
     * <p>
     * ! Expression(selector) and all the constant labels must be of the same type
     * ! Duplicated labels are not allowed
     * </p>
     * <p>
     * caseStatement
     * : CASE expression OF caseListElement (SEMI caseListElement)* (SEMI ELSE statements)? (SEMI)? END
     * ;
     * <p>
     * caseListElement
     * : constList COLON statement
     * ;
     */
    @Override
    public TypeDescriptor visitCaseStatement(PascalParser.CaseStatementContext ctx) {
        TypeDescriptor expression = visit(ctx.expression());

        // pure string as the case expression is not standard
        if (expression instanceof StringLiteral) {
            reportError(ctx, "Invalid case expression type: %s",
                    expression);
            return null;
        }

        HashSet<String> checkDuplicates = new HashSet<>();
        List<PascalParser.CaseListElementContext> caseListElementContexts = ctx.caseListElement();
        for (PascalParser.CaseListElementContext caseListElementContext : caseListElementContexts) {
            List<PascalParser.ConstantContext> constantList = caseListElementContext.constList().constant();
            for (PascalParser.ConstantContext eachConstant : constantList) {
                TypeDescriptor each = visit(eachConstant);

                // check type compatibility
                if (expression instanceof Subrange) {
                    Class<? extends TypeDescriptor> hostType = ((Subrange) expression).getHostType();
                    if (each.getClass() != hostType) {
                        reportError(eachConstant, "Constant and case type does not match.\nExpected: %s\nActual: %s",
                                expression, each);
                    }
                } else if (!expression.equiv(each)) {
                    reportError(eachConstant, "Constant and case type does not match.\nExpected: %s\nActual: %s",
                            expression, each);
                }

                // check duplicates (case insensitive)
                if (checkDuplicates.contains(eachConstant.getText().toLowerCase())) {
                    reportError(eachConstant, "duplicates case labels %s", eachConstant.getText());
                } else {
                    checkDuplicates.add(eachConstant.getText().toLowerCase());
                }
            }
            visit(caseListElementContext.statement());
        }

        return null;
    }

    /**
     * With statement gives quick operation to record type
     * Nested scopes are allowed where the variables are tried from last to first
     * Till the field reference is found, Otherwise raise compile time errors
     * <p>
     * withStatement
     * : WITH recordVariableList DO statement
     * ;
     *
     * @param ctx
     * @return
     */
    @Override
    public TypeDescriptor visitWithStatement(PascalParser.WithStatementContext ctx) {
        TypeDescriptor variableNum = visit(ctx.recordVariableList());
        long num = 0;
        if (variableNum instanceof IntegerBaseType) {
            num = ((IntegerBaseType) variableNum).getValue();
        }
        visit(ctx.statement());
        for (int i = 0; i < num; i++) {
            symbolTable.exitLocalScope();
        }
        return null;
    }

    /**
     * Open scope for each variable, putting all the fields into current scope
     * <p>
     * recordVariableList
     * : variable (COMMA variable)*
     * ;
     *
     * @param ctx
     * @return
     */
    @Override
    public TypeDescriptor visitRecordVariableList(PascalParser.RecordVariableListContext ctx) {
        int withStatementVaraibleNum = 0;

        List<PascalParser.VariableContext> variableContexts = ctx.variable();
        for (PascalParser.VariableContext variableContext : variableContexts) {
            TypeDescriptor type = visit(variableContext);
            if (!(type instanceof RecordType)) {
                reportError(ctx, "Invalid record type [%s]: %s", variableContext.getText(), type);
                continue;
            }

            Map<String, TypeDescriptor> fieldsMap = ((RecordType) type).getFieldsMap();
            // open new scope
            symbolTable.enterLocalScope();
            fieldsMap.forEach((k, v) -> {
                symbolTable.put(k, v);
            });
            withStatementVaraibleNum++;
        }
        return new Integer32((long) withStatementVaraibleNum);
    }

    /**
     * simple-type = ordinal-type | real-type
     * ordinal-type = enumerated-type | subrange-type | ordinal-type(int, boolean, char)
     *
     * @param type
     * @return
     */
    private boolean isSimpleType(TypeDescriptor type) {
        return (type instanceof FloatBaseType || isOrdinalType(type));
    }

    /**
     * ordinal-type = new-ordinal-type(enumerated,subrange) | ordinal-type(int, boolean, char)
     *
     * @param type
     * @return
     */
    private boolean isOrdinalType(TypeDescriptor type) {
        if (type instanceof Subrange) {
            // if subrange type defined, already pass the bounds checking,
            // choose the first bound as the host type instance
            type = ((Subrange) type).getLowerBound();
        } else if (type instanceof NestedBaseType) {
            // if checked operand is a formal parameter,
            // check the inner type
            type = ((NestedBaseType) type).getHostType();
        }
        return (type instanceof IntegerBaseType || type instanceof Character
                || type instanceof Boolean || type instanceof EnumeratedIdentifier);
    }

    private boolean isStringType(TypeDescriptor type) {
        if (type instanceof StringLiteral) return true;
        if (type instanceof ArrayType) {
            return ((ArrayType) type).getComponentType() instanceof Character
                    && ((ArrayType) type).isPacked();
        }
        return false;
    }

    /**
     * expression
     * : simpleExpression (relationaloperator expression)?
     * Current Implementation:
     * - Only for simple types (Primitives & Str)
     *
     * @param ctx
     * @return
     */
    @Override
    public TypeDescriptor visitExpression(PascalParser.ExpressionContext ctx) {
        TypeDescriptor lType = visit(ctx.simpleExpression());
        TypeDescriptor rType = null;

        if (ctx.e2 != null) {
            rType = visit(ctx.e2);
            String operator = ctx.relationalOperator.getText();

            // suppress errors
            if (rType.equiv(ErrorType.INVALID_EXPRESSION)) {
                return rType;
            }

            // check whether operands are simple types
            if (!(isSimpleType(lType)) && !(isStringType(lType))
                    && !(lType instanceof PointerType || lType instanceof NilType)) {
                reportError(ctx, "Illegal expression [%s]. Relational operator [%s] cannot be applied on left operand [%s].\nLtype: %s",
                        ctx.getText(), operator, ctx.simpleExpression().getText(), lType);
                // suppress error
                return ErrorType.INVALID_EXPRESSION;
            }

            if (!(isSimpleType(rType)) && !(isStringType(rType))
                    && !(rType instanceof PointerType || rType instanceof NilType)
            ) {
                reportError(ctx, "Illegal expression [%s]. Relational operator [%s] cannot be applied on left operand [%s].\nLtype: %s",
                        ctx.getText(), operator, ctx.e2.getText(), rType);
                // suppress error
                return ErrorType.INVALID_EXPRESSION;
            }

            // relational expression
            if (!lType.equiv(rType)) {
                if (lType instanceof FloatBaseType || rType instanceof FloatBaseType) {
                    //if (lType.equiv(Type.INTEGER) || rType.equiv(Type.INTEGER)) return Type.BOOLEAN;
                    if (lType instanceof IntegerBaseType || rType instanceof IntegerBaseType) return new Boolean();
                }
                if (lType instanceof Character || rType instanceof Character) {
                    //if (lType.equiv(Type.STRING_LITERAL) || rType.equiv(Type.STRING_LITERAL)) return Type.BOOLEAN;
                    //if (lType instanceof StringLiteral || rType instanceof StringLiteral) return new Boolean();
                }
                // FIXME: nested type classes, might be refactored
                if (lType instanceof Subrange) {
                    Class<? extends TypeDescriptor> hostType = ((Subrange) lType).getHostType();
                    if (hostType == rType.getClass()) return new Boolean();
                }
                if (rType instanceof Subrange) {
                    Class<? extends TypeDescriptor> hostType = ((Subrange) rType).getHostType();
                    if (hostType == lType.getClass()) return new Boolean();
                }
                if (lType instanceof Param) {
                    TypeDescriptor type = ((Param) lType).getHostType();
                    if (type.equiv(rType)) return new Boolean();
                }
                if (rType instanceof Param) {
                    TypeDescriptor type = ((Param) rType).getHostType();
                    if (type.equiv(lType)) return new Boolean();
                }

                reportError(ctx, "Expression [%s] types are incompatible.\nLtype: %s,\nRtype: %s",
                        ctx.getText(), lType, rType);
                return ErrorType.INVALID_EXPRESSION;
            }

        }
        // if looping statement, return bool otherwise return type of simpleExpression()
        return rType != null ? new Boolean() : lType;
    }

    /**
     * SimpleExpression
     * 1. single var - return visit children
     * 2. var1 operator var2 - check on two sides
     * - operator could be
     * - additive operator: + - or(logical)
     *
     * @param ctx
     * @return
     */
    @Override
    public TypeDescriptor visitSimpleExpression(PascalParser.SimpleExpressionContext ctx) {
        TypeDescriptor lType = visit(ctx.term());
        TypeDescriptor _lType = lType;

        //if (lType instanceof Function) {
        //    _lType = ((Function) lType).getResultType();
        //}

        TypeDescriptor rType;

        // if it involves 2 operands, need to check the type on both sides
        // then return the specific type
        if (null != ctx.simpleExpression()) {
            rType = visit(ctx.simpleExpression());
            TypeDescriptor _rType = rType;
            if (rType instanceof Function) {
                _rType = ((Function) rType).getResultType();
            }

            String operator = ctx.additiveOperator.getText();

            // if is Logical operator OR
            if (operator.equalsIgnoreCase("or")) {
                // TODO subrange
                if (_lType instanceof Subrange) {
                    return checkLogicalOpOperand(ctx, ((Subrange) _lType).getLowerBound(), ctx.term().getText(), _rType, ctx.simpleExpression().getText(), operator);
                }
                return checkLogicalOpOperand(ctx, _lType, ctx.term().getText(), _rType, ctx.simpleExpression().getText(), operator);
            }

            //below are arithmetic operation, operands must be number

            // if left operand is not int nor real
            if (!(_lType instanceof IntegerBaseType) && !(_lType instanceof FloatBaseType)) {

                // TODO Nested Type Refactor
                if (_lType instanceof Subrange) {
                    TypeDescriptor lowerBound = ((Subrange) _lType).getLowerBound();
                    if (!(lowerBound instanceof IntegerBaseType) && !(lowerBound instanceof FloatBaseType)) {
                        reportError(ctx, "Additive operator [%s] cannot be applied on the left operand of [%s]. Actual: %s",
                                operator, ctx.getText(), _lType);
                        return ErrorType.INVALID_TYPE;
                    }
                } else if (_lType instanceof NestedBaseType) {
                    TypeDescriptor hostType = ((NestedBaseType) _lType).getHostType();
                    if (!(hostType instanceof IntegerBaseType) && !(hostType instanceof FloatBaseType)) {
                        reportError(ctx, "Additive operator [%s] cannot be applied on the left operand of [%s]. Actual: %s",
                                operator, ctx.getText(), _lType);
                        return ErrorType.INVALID_TYPE;
                    }
                } else {
                    reportError(ctx, "Additive operator [%s] cannot be applied on the left operand of [%s]. Actual: %s",
                            operator, ctx.getText(), _lType);
                    return ErrorType.INVALID_TYPE;
                }
            }
            // if right operand is not int nor real
            if (!(_rType instanceof IntegerBaseType) && !(_rType instanceof FloatBaseType)) {
                // TODO Nested Type Refactor
                if (_rType instanceof Subrange) {
                    TypeDescriptor lowerBound = ((Subrange) _rType).getLowerBound();
                    if (!(lowerBound instanceof IntegerBaseType) && !(lowerBound instanceof FloatBaseType)) {
                        reportError(ctx, "Additive Operator [%s] cannot be applied on the right operand of [%s]. Actual: %s",
                                operator, ctx.getText(), _rType);
                        return ErrorType.INVALID_TYPE;
                    }
                } else if (_rType instanceof NestedBaseType) {
                    TypeDescriptor hostType = ((NestedBaseType) _rType).getHostType();
                    if (!(hostType instanceof IntegerBaseType) && !(hostType instanceof FloatBaseType)) {
                        reportError(ctx, "Additive Operator [%s] cannot be applied on the right operand of [%s]. Actual: %s",
                                operator, ctx.getText(), _rType);
                        return ErrorType.INVALID_TYPE;
                    }
                } else {
                    reportError(ctx, "Additive Operator [%s] cannot be applied on the right operand of [%s]. Actual: %s",
                            operator, ctx.getText(), _rType);
                    return ErrorType.INVALID_TYPE;
                }
            }

            //if (_lType.equiv(Type.REAL) || _rType.equiv(Type.REAL)) return Type.REAL;
            //else return Type.INTEGER;
            if (_lType instanceof FloatBaseType || _rType instanceof FloatBaseType) {
                return new FloatBaseType();
                //}  else return IntegerBaseType.copy(defaultIntegerType);
            } else {

                // subrange, params evaluation would be evaluated at the runtime
                if (!(_lType instanceof Subrange) && !(_rType instanceof Subrange)
                        && !(_lType instanceof NestedBaseType) && !(_rType instanceof NestedBaseType)
                ) {
                    if (((IntegerBaseType) _lType).isConstant() && ((IntegerBaseType) _rType).isConstant()) {
                        // only checks for constant & literal
                        Long lValue = ((IntegerBaseType) _lType).getValue();
                        Long rValue = ((IntegerBaseType) _rType).getValue();
                        return DefaultIntegerType.of(lValue + rValue);
                    }
                }
                //return DefaultIntegerType.of();
                return IntegerBaseType.copy(defaultIntegerType);
            }

        }
        // only 1 term
        return _lType;
    }

    /**
     * TODO: RUNTIME INTEGER arithmetic operation overflow/underflow checking
     * <p>
     * Represents:
     * 1. single signedFactor - return visitChildren
     * 2. var1 multiplicative operator var2 - check both operands
     * - operator could be:
     * - *
     * - /
     * - div
     * - mod
     * - and (logical)
     * <p>
     * term
     * : signedFactor (multiplicativeOperator=(STAR| SLASH| DIV| MOD| AND) term)?
     * ;
     *
     * @param ctx
     * @return
     */
    @Override
    public TypeDescriptor visitTerm(PascalParser.TermContext ctx) {
        TypeDescriptor lType = visit(ctx.signedFactor());
        TypeDescriptor rType = null;
        if (ctx.term() != null) {
            rType = visit(ctx.term());
        }

        // if it involves 2 operands, need to check the type on both sides
        // then return the specific type
        if (null != ctx.multiplicativeOperator) {
            String operator = ctx.multiplicativeOperator.getText();

            // if is Logical operator: AND
            if (operator.equalsIgnoreCase("and")) {
                return checkLogicalOpOperand(ctx, lType, ctx.signedFactor().getText(), rType, ctx.term().getText(), operator);
            }

            // TODO SUbrange, ArrayType,
            // extract hostType if nestedType
            TypeDescriptor _lType = lType;
            if (lType instanceof NestedBaseType) {
                _lType = ((NestedBaseType) lType).getHostType();
            }
            if (lType instanceof Subrange) {
                _lType = ((Subrange) lType).getLowerBound();
            }

            // TODO SUbrange, ArrayType,
            // extract hostType if nestedType
            TypeDescriptor _rType = rType;
            if (rType instanceof NestedBaseType) {
                _rType = ((NestedBaseType) rType).getHostType();
            }
            if (rType instanceof Subrange) {
                 _rType= ((Subrange) rType).getLowerBound();
            }

            // other multiplicative operators(arithmetic)
            // if left operand is not int nor real
            if (!(_lType instanceof IntegerBaseType) && !(_lType instanceof FloatBaseType)) {
                reportError(ctx, "Multiplicative Operator [%s] cannot be applied on the " +
                        "left operand [%s] of expression [%s]: ", operator, ctx.signedFactor().getText(), ctx.getText());
                return ErrorType.INVALID_TYPE;
            }
            // if right operand is not int nor real
            if (!(_rType instanceof IntegerBaseType) && !(_rType instanceof FloatBaseType)) {
                reportError(ctx, "Multiplicative Operator [%s] cannot be applied on the " +
                        "right operand [%s] of expression [%s]: ", operator, ctx.term().getText(), ctx.getText());
                return ErrorType.INVALID_TYPE;
            }

            // integer division, operands must be integer
            switch (operator) {
                case "div":
                case "DIV":
                    if (!(_lType instanceof IntegerBaseType) || !(_rType instanceof IntegerBaseType)) {
                        reportError(ctx, "All the operands of integer division must be Integer.\nLtype: %s\nRtype: %s",
                                lType, rType);
                    }
                    return new Integer32();
                case "/":
                    //if (_lType instanceof FloatBaseType || _rType instanceof FloatBaseType) {
                    return new Real();
                //}
                // real division, operands could be int/real
                //return DefaultFloatType.instance;
                case "mod":
                case "MOD":
                    // modulus reminder division, operands must be integer
                    if (!(_lType instanceof IntegerBaseType) || !(_rType instanceof IntegerBaseType)) {
                        reportError(ctx, "All the operands of modulus must be Integer.\nLtype: %s,\nRtype: %s",
                                lType, rType);
                    }
                    //return IntegerBaseType.copy(defaultIntegerType);
                    return new Integer32();
                default:
                    // other multiplicative operators: * return specific type
                    if (_lType instanceof FloatBaseType || _rType instanceof FloatBaseType)
                        return new Real();
                    else return new Integer32();
            }
        }
        return lType;
    }

    private TypeDescriptor checkLogicalOpOperand(ParserRuleContext ctx,
                                                 TypeDescriptor lType, String lOp,
                                                 TypeDescriptor rType, String rOp,
                                                 String operator) {
        if (!(lType instanceof Boolean)) {
            reportError(ctx, String.format("Logical operator [%s] cannot" +
                            " be applied on left operand [%s] of expression [%s]: %s",
                    operator, lOp, ctx.getText(), lType));
            return ErrorType.INVALID_TYPE;
        }
        if (!(rType instanceof Boolean)) {
            reportError(ctx, String.format("Logical operator [%s] cannot" +
                            " be applied on right operand [%s] of expression [%s]: %s",
                    operator, rOp, ctx.getText(), rType));
            return ErrorType.INVALID_TYPE;
        }
        //return Type.BOOLEAN;
        return new Boolean();
    }

    /**
     * signedFactor
     * : (PLUS | MINUS)? factor
     * ;
     * <p>
     * Check Monadic arithmetic operations:
     * -x
     * +x
     *
     * @param ctx
     * @return
     */
    @Override
    public TypeDescriptor visitSignedFactor(PascalParser.SignedFactorContext ctx) {
        TypeDescriptor factor = visit(ctx.factor());
        // involves monadic arithmetic expression, check the operand factor
        // i.e. signedFactor
        if (ctx.monadicOperator != null) {
            String monadicOp = ctx.monadicOperator.getText();
            if (factor instanceof ErrorType && ctx.parent.parent.parent.parent instanceof PascalParser.AssignmentStatementContext)
                return factor;

            if (!(factor instanceof IntegerBaseType) && !(factor instanceof FloatBaseType)) {
                return ErrorType.INVALID_MONADIC_OPERATION;
            } else if (factor instanceof IntegerBaseType) {
                Long numberValue = ((IntegerBaseType) factor).getValue();
                RuleContext parentContext = ctx.parent.parent.parent.parent;

                // update numberValue accords to the monadic operator
                switch (monadicOp) {
                    case "-":
                        if (numberValue != null) numberValue = -numberValue;
                        break;
                    case "+":
                        break;
                }

                // if is the last constant node, gathering all the errors if any
                //if (!(parentContext instanceof PascalParser.FactorExprContext)) {
                if (!hasNoOverflow(numberValue) && ctx.parent.parent.parent.parent instanceof PascalParser.AssignmentStatementContext)
                    return ErrorType.INTEGER_OVERFLOW;
                if (!hasNoUnderflow(numberValue) && ctx.parent.parent.parent.parent instanceof PascalParser.AssignmentStatementContext)
                    return ErrorType.INTEGER_UNDERFLOW;
                // no errors directly return factor
                return new Integer32(numberValue);
                //} else {
                //    // FIXME:this might cause potential problems
                //    IntegerBaseType copy = IntegerBaseType.copy((IntegerBaseType) factor);
                //    copy.setValue(numberValue);
                //    return copy;
                //}
            }
        }

        // unsigned factor
        if (factor instanceof IntegerBaseType) {
            // integer overflow checking
            Long numberValue = null;
            // if right operand is var or unsigned constant
            if (ctx.factor() instanceof PascalParser.FactorVarContext ||
                    ctx.factor() instanceof PascalParser.FactorUnConstContext) {
                numberValue = ((IntegerBaseType) factor).getValue();
            }

            // if numberValue == null, then it should be checked at runtime
            if (numberValue != null && !hasNoOverflowOrUnderflow(numberValue, true, false)) {
                // error reported in assignment node
                return ErrorType.INTEGER_OVERFLOW;
            }
            if (numberValue != null && !hasNoOverflowOrUnderflow(numberValue, false, true)) {
                // error reported in assignment node
                return ErrorType.INTEGER_UNDERFLOW;
            }
            return new Integer32(numberValue);
        }
        return factor;
    }

    private int arrayIndexCount(PascalParser.VariableContext ctx) {
        int count = 0;
        List<PascalParser.ArrayScriptingContext> arrayScriptingContexts = ctx.arrayScripting();
        for (PascalParser.ArrayScriptingContext arrayScriptingContext : arrayScriptingContexts) {
            count += arrayScriptingContext.expression().size();
        }
        return count;
    }

    /**
     * FIXME always return the new array type with the first index element removed...
     *
     * @param arrayIndexCount
     * @param arrayType
     * @return
     */
    private TypeDescriptor getArrayContent(int arrayIndexCount, ArrayType arrayType) {
        TypeDescriptor componentType = arrayType.getComponentType();
        List<TypeDescriptor> indexList = arrayType.getIndexList();
        int indexListSize = indexList.size();
        if (arrayIndexCount == indexListSize - 1) {
            return componentType;
        }
        //concatenate and return the new array type
        List<TypeDescriptor> newIndexList = new ArrayList<>(List.copyOf(indexList));
        for (int i = 0; i <= arrayIndexCount; i++) {
            newIndexList.remove(i);
        }
        return new ArrayType(componentType, newIndexList);
    }

    /**
     * Children of Term-signedFactor
     * Represents a variable(identifier) or structure type (operations involved if any)
     * <p>
     * variable
     * : variableHead
     * (
     * arrayScripting
     * | DOT identifier
     * | POINTER
     * )*
     *
     * @param ctx
     * @return
     */
    @Override
    public TypeDescriptor visitFactorVar(PascalParser.FactorVarContext ctx) {
        // suppress errors

        if (ctx.variable().children.size() > 1 || ctx.variable().variableHead().AT() != null) {
            return visit(ctx.variable());
        }
        TypeDescriptor type = retrieve(ctx.getText(), false, ctx);
        return type;
    }

    @Override
    public TypeDescriptor visitFactorExpr(PascalParser.FactorExprContext ctx) {
        return visit(ctx.expression());
    }

    //@Override
    //public TypeDescriptor visitFactorFuncDesignator(PascalParser.FactorFuncDesignatorContext ctx) {
    //    TypeDescriptor returnType = visit(ctx.functionDesignator());
    //    return returnType;
    //}

    /**
     * NOT factor #notFactor
     * Logical operator
     *
     * @param ctx
     * @return boolean if valid
     */
    @Override
    public TypeDescriptor visitNotFactor(PascalParser.NotFactorContext ctx) {
        TypeDescriptor type = visit(ctx.factor());
        if (!(type instanceof Boolean)) {
            reportError(ctx, "Relational operator [NOT] cannot be applied on [%s] operand [%s]: %s", ctx.getText(), ctx.factor().getText(), type.toString());
            return ErrorType.INVALID_TYPE;
        }
        return type;
    }

    @Override
    public TypeDescriptor visitUnsignedConstant(PascalParser.UnsignedConstantContext ctx) {
        if (ctx.NIL() != null) return new NilType();
        if (ctx.unsignedNumber() != null) return visit(ctx.unsignedNumber());
        if (ctx.constantChr() != null) return visit(ctx.constantChr());
        if (ctx.string() != null) return visit(ctx.string());
        return null;
    }


    /**
     * unsignedNumber
     * :
     * type=(NUM_INT | NUM_REAL)
     * ;
     *
     * @param ctx
     * @return
     */
    @Override
    public TypeDescriptor visitUnsignedNumber(PascalParser.UnsignedNumberContext ctx) {
        switch (ctx.type.getType()) {
            case PascalParser.NUM_INT:
                //return Integer32.of(String.valueOf(ctx.NUM_INT()));
                // integer literal set to be constant
                Integer32 integer32 = new Integer32(String.valueOf(ctx.NUM_INT().getText()), true);
                return integer32;
            case PascalParser.NUM_REAL:
                //return Type.REAL;
                Real real = new Real(String.valueOf(ctx.NUM_REAL().getText()));
                real.setConstant(true);
                return real;
        }
        return null;
    }

    /**
     * NOTE: Int Overflows/Underflow shouldn't be checked in this node
     * <p>
     * Check whether unsigned int is smaller than maxInt
     * <p>
     * unsignedNumber
     * : unsignedInteger
     * | unsignedReal
     * ;
     *
     * @param ctx
     * @return
     */
    //@Override
    //@Deprecated
    //public TypeDescriptor visitUnsignedInteger(PascalParser.UnsignedIntegerContext ctx) {
    //    Integer32 maxInt = getMaxInt(ctx);
    //    Integer maxIntMaxValue = maxInt.getMaxValue();
    //    String rightOperand = ctx.getText();
    //    int rightOperandLength = ctx.getText().length();
    //
    //    // if overflows, set to default value of 0
    //    int rightOperandValue = NumberUtils.toInt(ctx.getText(), 0);
    //    // when value==0 && 0 value is derived from overflow
    //    if (rightOperandValue==0 && rightOperandLength!=1) {
    //        return Type.INVALID_CONSTANT_TYPE;
    //    }
    //    return Type.INTEGER;
    //}

    //@Override
    //public TypeDescriptorDescriptor visitUnsignedReal(PascalParser.UnsignedRealContext ctx) {
    //    return Type.REAL;
    //}
    @Override
    public TypeDescriptor visitString(PascalParser.StringContext ctx) {
        String text = ctx.getText();
        String replace = text.replace("'", "");
        int length = replace.length();
        if (length == 1) return new Character(replace.charAt(0));
        return new StringLiteral(replace);
    }

    @Override
    public TypeDescriptor visitTrue(PascalParser.TrueContext ctx) {
        return new Boolean(true);
    }

    @Override
    public TypeDescriptor visitFalse(PascalParser.FalseContext ctx) {
        return new Boolean(false);
    }


}
