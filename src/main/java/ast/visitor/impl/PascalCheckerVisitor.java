package ast.visitor.impl;

import ast.visitor.PascalBaseVisitor;
import ast.visitor.PascalParser;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
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
import type.primitive.integer.DefaultIntegerType;
import type.primitive.integer.Integer32;
import type.primitive.integer.IntegerBaseType;
import type.procOrFunc.ProcFuncBaseType;
import type.structured.ArrayType;
import type.structured.File;
import type.structured.RecordType;
import type.structured.StructuredBaseType;
import type.utils.SymbolTable;
import type.utils.Table;
import type.utils.TableManager;
import type.utils.TypeTable;
import util.ErrorMessage;

import java.util.*;

public class PascalCheckerVisitor extends PascalBaseVisitor<TypeDescriptor> {


    // Contextual errors
    private int errorCount = 0;

    private CommonTokenStream tokens;

    /**
     * Table & Table Manager fields
     */
    private Table<Object, TypeDescriptor> symbolTable = new SymbolTable<>();
    private Table<Object, TypeDescriptor> typeTable = new TypeTable<>();
    private TableManager<Object, TypeDescriptor> tableManager = TableManager.getInstance(); // quick reference


    // Max Builtin Integer Type (default - Integer32)
    static final IntegerBaseType defaultIntegerType = new Integer32();
    private int indexCount = 0;

    // Constructor

    public PascalCheckerVisitor(CommonTokenStream toks) {
        tokens = toks;
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
        System.err.println(startLine + ":" + startCol + "-" +
                finishLine + ":" + finishCol
                + " " + message);
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
        // Print an error message relating to the given
        // part of the AST.
        Interval interval = ctx.getSourceInterval();
        Token start = tokens.get(interval.a);
        Token finish = tokens.get(interval.b);
        int startLine = start.getLine();
        int startCol = start.getCharPositionInLine();
        int finishLine = finish.getLine();
        int finishCol = finish.getCharPositionInLine();
        System.err.println(startLine + ":" + startCol + "-" +
                finishLine + ":" + finishCol
                + " " + String.format(message, args));
        errorCount++;
    }

    public int getNumberOfContextualErrors() {
        // Return the total number of errors so far detected.
        return errorCount;
    }

    private boolean hasNoOverflow(Long number) {
        return number <= defaultIntegerType.MAX_VALUE;
    }

    private boolean hasNoUnderflow(Long number) {
        return number >= defaultIntegerType.MIN_VALUE;
    }

    private boolean hasNoOverflowOrUnderflow(Long number, boolean checkOverflow, boolean checkUnderflow) {
        if (checkOverflow && checkUnderflow) {
            return hasNoOverflow(number) && hasNoUnderflow(number);
        } else if (checkOverflow) return hasNoOverflow(number);
        return hasNoUnderflow(number);
    }

    private void predefine() {
        // Add predefined procedures to the type table.
        //BuiltInUtils.fillTable(symbolTable);
        symbolTable.put("maxint", DefaultIntegerType.of((long) Integer.MAX_VALUE, true));

        //Table table = tableManager.selectTable(null);
        //System.out.println("table? = " + table);
        //tableManager.addTable(null,new SymbolTable<>());

        //RunTimeLibFactory.fillTable(symbolTable);
    }

    private void define(String id, TypeDescriptor type,
                        ParserRuleContext ctx) {
        //select the specific selectedTable with corresponding usage context
        Table<Object, TypeDescriptor> selectedTable = tableManager.selectTable(ctx.getClass());
        System.out.println("selectedTable = " + selectedTable);

        // Add id with its type to the selectedTable
        // Checking whether id is already declared in the same scope.
        // IGNORE CASE
        boolean isDuplicatedInOtherTable = false;
        Map<Class<? extends ParserRuleContext>, Table<Object, TypeDescriptor>> otherTables = tableManager.selectAllTablesExcludedToClass(ctx.getClass());
        for (Table<Object, TypeDescriptor> eachTable : otherTables.values()) {
            if (eachTable.contains(id.toLowerCase())) isDuplicatedInOtherTable = true;
        }

        boolean putSuccessfully = false;
        if (!isDuplicatedInOtherTable) putSuccessfully = selectedTable.put(id.toLowerCase(), type);

        if (isDuplicatedInOtherTable || !putSuccessfully)
            reportError(ctx, id + " is redeclared");
    }

    private TypeDescriptor retrieve(String id, boolean notSuppressError,
                                    ParserRuleContext ctx) {
        Table<Object, TypeDescriptor> selectedTable = tableManager.selectTable(ctx.getClass());
        System.out.println("selectedTable = " + selectedTable);

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
     * 2. After visit all the proc/func declarations,
     * <p>
     * Check whether there exist implementation block for every declared func/proc prototype
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
        System.out.println("Block Starts*************************");
        tableManager.displayAllTablesCurrentScope();
        visitChildren(ctx);
        System.out.println("Block Ends*************************");
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
        System.out.println("id = " + id);
        System.out.println("type = " + type);
        System.out.println("typeTable.get(id.toLowerCase()) = " + typeTable.get(id.toLowerCase()));
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
        System.out.println("Defin new type");
        String id = ctx.identifier().getText();
        TypeDescriptor type = visit(ctx.type_());
        define(id, type, ctx);
        return null;
    }

    @Override
    public TypeDescriptor visitVariableDeclaration(PascalParser.VariableDeclarationContext ctx) {
        System.out.println("Vaeriable Decl Starts*************");
        System.out.println("var decl ctx.getText() = " + ctx.getText());

        tableManager.displayAllTablesCurrentScope();

        List<PascalParser.IdentifierContext> identifierContextList = ctx.identifierList().identifier();
        for (PascalParser.IdentifierContext identifierContext : identifierContextList) {
            String id = identifierContext.IDENT().getText();
            TypeDescriptor type = visit(ctx.type_());
            define(id, type, ctx);
        }
        //symbolTable.displayCurrentScope();
        tableManager.displayAllTablesCurrentScope();
        System.out.println("Variable Decl ends\n*************");
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
        System.out.println("*************Define new Const");
        System.out.println("ctx.getText() = " + ctx.getText());
        String id = ctx.identifier().getText();
        TypeDescriptor type = visit(ctx.constant());
        //System.out.println("constant type = " + type);
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
        if (unsignedNumber.equiv(Type.INTEGER)) {
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
        if (unsignedNumber.equiv(Type.INTEGER)) {
            Long numberValue = ((IntegerBaseType) unsignedNumber).getValue();
            switch (monadicOperator) {
                case "-":
                    numberValue = -numberValue;
                    if (!hasNoUnderflow(numberValue)) {
                        reportError(ctx, "Illegal constant definition [%s] with right operand [%s]" +
                                        "which must be between [%d] and [%d]",
                                ctx.getParent().getText(), numberValue, defaultIntegerType.MIN_VALUE, defaultIntegerType.MAX_VALUE);
                        //return ErrorType.INTEGER_UNDERFLOW;
                        break;
                    }
                case "+":
                    if (!hasNoOverflow(numberValue)) {
                        reportError(ctx, "Illegal constant definition [%s] with right operand [%s]" +
                                        "which must be between [%d] and [%d]",
                                ctx.getParent().getText(), numberValue, defaultIntegerType.MIN_VALUE, defaultIntegerType.MAX_VALUE);
                        //return ErrorType.INTEGER_OVERFLOW;
                        break;
                    }
            }
            //return ErrorType.INVALID_CONSTANT_TYPE;
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
        System.out.println("Define new enumerated type " + ctx.getText());
        List<PascalParser.IdentifierContext> identifierContextList = ctx.identifierList().identifier();
        EnumeratedType enumeratedType = new EnumeratedType();
        HashMap<String, Integer> valueMap = new LinkedHashMap<>();
        for (int i = 0; i < identifierContextList.size(); i++) {
            // store lower case, (simulating case insensitive)
            String id = identifierContextList.get(i).getText().toLowerCase();
            valueMap.put(id,
                    i);
            // insert each enumerated identifier into the symbol table
            symbolTable.put(id, new EnumeratedIdentifier(enumeratedType, id));
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
        System.out.println("subrange.getHostType() = " + subrange.getHostType());
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
            //System.out.println("lowerValue = " + lowerValue);
            //System.out.println("upperValue = " + upperValue);
            //System.out.println("StringUtils.compare(lowerValue, upperValue) = " + StringUtils.compare(lowerValue, upperValue));
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
        System.out.println("********Defin array type");
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
        System.out.println("arrayType = " + arrayType);
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
        System.out.println("record section ctx.getText() = " + ctx.getText());
        List<PascalParser.IdentifierContext> identifierList = ctx.identifierList().identifier();
        TypeDescriptor type = visit(ctx.type_());
        identifierList.forEach(each -> define(each.getText(), type, ctx));
        return null;
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
        System.out.println("ctx = " + ctx.getText());
        TypeDescriptor type = visit(ctx.parameterGroup().typeIdentifier());
        List<PascalParser.IdentifierContext> identifiers = ctx.parameterGroup().identifierList().identifier();
        for (PascalParser.IdentifierContext identifier : identifiers) {
            // for each group, define the corresponding formal parameter with null label
            // (x,y,...:Type)
            System.out.println("Defin no label Param!!!!!!!!!!!!!");
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
            System.out.println("Defin var label Param!!!!!!!!!!!!!");
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
        System.out.println("Defin func label Param!!!!!!!!!!!!!");
        System.out.println("funcparam ctx.getText() = " + ctx.getText());

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
        tableManager.allTablesExitNewScope();
        Function function = new Function(formalParams, resultType);
        System.out.println("function param = " + function);
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
        System.out.println("Defin proc label Param!!!!!!!!!!!!!");
        System.out.println("procparam ctx.getText() = " + ctx.getText());

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
        System.out.println("procedure param = " + procedure);
        ;
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

        System.out.println("PROC DECL Starts*************************");
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
        }
        define(id, new Procedure(params), ctx);
        System.out.println("Define Proc signature");

        tableManager.displayAllTablesCurrentScope();
        //symbolTable.displayCurrentScope();
        System.out.println("id = " + id);
        System.out.println("symbolTable.get(id) = " + symbolTable.get(id));
        visit(ctx.block()); // scope & type checking in current proc scope
        //symbolTable.exitLocalScope(); // back to last scope
        //typeTable.exitLocalScope();
        tableManager.allTablesExitNewScope();
        define(id, new Procedure(params), ctx);
        System.out.println("PROC DECL ENDS*************************");
        //symbolTable.displayCurrentScope();
        tableManager.displayAllTablesCurrentScope();
        return null;
    }

    @Override
    public TypeDescriptor visitProcedurePrototypeDecl(PascalParser.ProcedurePrototypeDeclContext ctx) {
        String id = ctx.procedureHeading().identifier().getText();
        ArrayList<TypeDescriptor> params = new ArrayList<>();

        System.out.println("Proc Prototype DECL Starts*************************");
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
            System.out.println("allParams = " + allParams);
            allParams.forEach((k, v) -> params.add(v));
        }
        Procedure procedure = new Procedure(params);
        define(id, procedure, ctx);
        System.out.println("Define Proc Prototype signature");
        System.out.println("procedure = " + procedure);

        //tableManager.displayAllTablesCurrentScope();

        tableManager.allTablesExitNewScope();
        define(id, procedure, ctx);
        System.out.println("PROC Prototype DECL ENDS*************************");

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
    @Override
    public TypeDescriptor visitProcedureImpl(PascalParser.ProcedureImplContext ctx) {
        System.out.println("PROC IMpl Starts*************************");
        String id = ctx.identifier().getText().toLowerCase();

        if (prototypeImplTrackingMap.containsKey(id)) {
            Pair<TypeDescriptor, ParserRuleContext> functionPrototypePair = prototypeImplTrackingMap.get(id);
            TypeDescriptor type = functionPrototypePair.getLeft();
            // if prototype is of Function type which the implementation cannot match
            // report errors
            if (!(type instanceof Procedure)) {
                reportError(ctx, "Implantation header cannot match previous prototype declaration: %s",
                        type);
            }
            // remove the entry in the tracking map, no matter whether the implementation can match the header or not
            prototypeImplTrackingMap.remove(id);
        }

        // enter new scope for scope checking
        tableManager.allTablesEnterNewScope();

        TypeDescriptor procedureOrFunction = retrieve(id, ctx); // get predefined procedure prototype
        define(id, procedureOrFunction, ctx);

        //extract all the defined formals, inserting into current scope
        if (procedureOrFunction instanceof ProcFuncBaseType) {
            List<TypeDescriptor> formalParams = ((ProcFuncBaseType) procedureOrFunction).getFormalParams();
            formalParams.forEach(each -> {
                if (each instanceof FormalParam) {
                    String formalName = ((FormalParam) each).getName();
                    define(formalName, each, ctx);
                }
            });
        }

        visit(ctx.block()); // scope & type checking in current proc scope
        tableManager.allTablesExitNewScope();

        System.out.println("PROC Implementation ENDS*************************");
        return null;
    }

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

        String id = ctx.identifier().getText();
        selectedFunction = id;
        TypeDescriptor resultType = visit(ctx.resultType());
        ArrayList<TypeDescriptor> params = new ArrayList<>();

        System.out.println("Func DECL Starts*************************");
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
            System.out.println("allParams = " + allParams);
            allParams.forEach((k, v) -> params.add(v));
        }
        Function function = new Function(params, resultType);
        define(id, function, ctx);
        System.out.println("Define Func signature");
        System.out.println("function = " + function);

        //tableManager.displayAllTablesCurrentScope();

        visit(ctx.block());// scope & type checking in current func scope

        if (!functionHasReturnAssignment) {
            reportError(ctx, "Missing result assignment in Function: %s", function);
        }

        tableManager.allTablesExitNewScope();
        define(id, function, ctx);
        System.out.println("FUNC DECL ENDS*************************");
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

        System.out.println("Func Prototype DECL Starts*************************");
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
            System.out.println("allParams = " + allParams);
            allParams.forEach((k, v) -> params.add(v));
        }
        Function function = new Function(params, resultType);
        define(id, function, ctx);
        System.out.println("Define Func Prototype signature");
        System.out.println("function = " + function);

        //tableManager.displayAllTablesCurrentScope();

        tableManager.allTablesExitNewScope();
        define(id, function, ctx);
        System.out.println("FUNC Prototype DECL ENDS*************************");

        // insert new entry into the tracking map
        // check whether there exist implementation corresponds to current function prototype declaration
        // Ignore Case
        prototypeImplTrackingMap.put(id.toLowerCase(), Pair.of(function, ctx));

        return null;
    }

    /**
     * Function implementation part, must correspond to predefined forward declaration
     * <p>
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
    public TypeDescriptor visitFunctionImpl(PascalParser.FunctionImplContext ctx) {
        System.out.println("Function impl Starts");
        String id = ctx.identifier().getText().toLowerCase();

        if (prototypeImplTrackingMap.containsKey(id)) {
            Pair<TypeDescriptor, ParserRuleContext> functionPrototypePair = prototypeImplTrackingMap.get(id);
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

        // enter new scope to conduct the scope checking
        tableManager.allTablesEnterNewScope();

        TypeDescriptor functionOrProcedure = retrieve(id, ctx); // get predefined functionOrProcedure prototype
        define(id, functionOrProcedure, ctx);

        //extract all the defined formals, inserting into current scope
        if (functionOrProcedure instanceof ProcFuncBaseType) {
            List<TypeDescriptor> formalParams = ((ProcFuncBaseType) functionOrProcedure).getFormalParams();
            formalParams.forEach(each -> {
                if (each instanceof FormalParam) {
                    String formalName = ((FormalParam) each).getName();
                    define(formalName, each, ctx);
                }
            });
        }

        visit(ctx.block());
        tableManager.allTablesExitNewScope();
        System.out.println("Function impl ends");
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
            //formalParameters = ((Function) signature).getFormalParams();
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
                System.out.println("_actual = " + _actual);
                FormalParam _formal = (FormalParam) formal;
                System.out.println("_formal = " + _formal);
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
        System.out.println("*******************PROC CALL");
        System.out.println("ctx.getText() = " + ctx.getText());
        String id = ctx.identifier().getText();
        TypeDescriptor signature = retrieve(id, ctx);
        System.out.println("signature type = " + signature);
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
        if (!(_signature.equiv(ErrorType.UNDEFINED_TYPE)) && !(_signature instanceof Procedure)) {
            reportError(ctx, "Illegal procedure statement [%s] which is not a procedure",
                    ctx.getText());
            return ErrorType.INVALID_PROCEDURE_TYPE;
        } else if (_signature instanceof Procedure) {
            //check signatures if has actual parameters
            List<PascalParser.ActualParameterContext> actualParameterContextList = new ArrayList<>();
            if (ctx.parameterList() != null) {
                actualParameterContextList = ctx.parameterList().actualParameter();
            }
            actualParameterContextList.forEach(each -> {
                System.out.println("each.getText() = " + each.getText());
            });
            ErrorMessage errorMessage = checkSignature(_signature, actualParameterContextList, ctx);
            System.out.println("check signature");
            System.out.println("_signature = " + _signature);
            System.out.println("booleanMessage = " + errorMessage);
            if (errorMessage.hasErrors()) {
                reportError(ctx, errorMessage.getMessageSequence());
            }
        }
        System.out.println("*******************PROC CALL ENDS");
        return null;
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
        System.out.println("*******************FUNC CALL");
        System.out.println("ctx.getText() = " + ctx.getText());
        String id = ctx.identifier().getText();
        tableManager.displayAllTablesCurrentScope();
        //suppress errors
        TypeDescriptor signature = retrieve(id, false, ctx);
        TypeDescriptor _signature = signature;
        System.out.println("signature = " + signature);

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
        if (!(_signature.equiv(ErrorType.UNDEFINED_TYPE)) && !(_signature instanceof Function)) {
            reportError(ctx, id + " is not a function. Returns no value.");
            return ErrorType.INVALID_FUNCTION_TYPE;
        } else if (_signature instanceof Function) {
            //check signatures
            List<PascalParser.ActualParameterContext> actualParameterContextList = ctx.parameterList().actualParameter();
            ErrorMessage errorMessage = checkSignature(_signature, actualParameterContextList, ctx);
            System.out.println("booleanMessage = " + errorMessage);
            if (errorMessage.hasErrors()) {
                reportError(ctx, errorMessage.getMessageSequence());
            }
        }
        System.out.println("*******************Function CALL ENDS");
        return ((Function) _signature).getResultType();
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
        if (!conditionType.equiv(Type.BOOLEAN)) {
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
        if (!exType.equiv(Type.BOOLEAN)) {
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
            if (!initType.equiv(controlType)) {
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
            } else if (!finalType.equiv(controlType)) {
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
        System.out.println("variable ctx.getText() = " + ctx.getText());
        structuredTypeId = ctx.variableHead().identifier().getText();
        // retrieve current
        structuredTypeToBeCheckNext = retrieve(structuredTypeId, ctx);
        indexCount = 0;
        //List<PascalParser.ArrayScriptingContext> arrayScriptingContexts = ctx.arrayScripting();
        //if (!arrayScriptingContexts.isEmpty()) {
        //    return super.visitVariable(ctx);
        //}

        List<ParseTree> children = ctx.children;
        // remove the variableHead node, the remaining nodes should be processed circularly
        if (!children.isEmpty()) children.remove(0);

        // if any errors, report in this node and throw errorType to upper node, suppressing the errors
        for (ParseTree eachChild : children) {
            System.out.println("structuredTypeToBeCheckNext before= " + structuredTypeToBeCheckNext);
            structuredTypeToBeCheckNext = visit(eachChild);
            System.out.println("structuredTypeToBeCheckNext updated= " + structuredTypeToBeCheckNext);
            if (structuredTypeToBeCheckNext instanceof ErrorType) {
                return structuredTypeToBeCheckNext;
            }
        }
        System.out.println("var ctx.getText() = " + ctx.parent.getText());
        System.out.println("return structuredTypeToBeCheckNext = " + structuredTypeToBeCheckNext);

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
        System.out.println("current scaript ctx.getText() = " + ctx.getText());
        List<PascalParser.ExpressionContext> expression = ctx.expression();
        System.out.println("arraylists structuredTypeToBeCheckNext = " + structuredTypeToBeCheckNext);
        if (structuredTypeToBeCheckNext instanceof ArrayType
            || structuredTypeToBeCheckNext instanceof FormalParam) {

            for (PascalParser.ExpressionContext eachExpr : expression) {
                ArrayType _arrayType = null;

                if (structuredTypeToBeCheckNext instanceof ArrayType) _arrayType = (ArrayType) structuredTypeToBeCheckNext;
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
                System.out.println("updated if structuredTypeToBeCheckNext = " + structuredTypeToBeCheckNext);

                System.out.println("indexList looks = " + indexList);
                System.out.println("indexList.size() = " + indexList.size());
                // there are various combinations of indexing,
                // but if number of expr > size of indexList, directly report error
                //if (expression.size() > indexList.size() || indexCount > indexList.size()) {
                //    reportError(ctx, "Invalid array scripting operation [%s]. Operations dimension cannot match the declaration [%s]",
                //            ctx.parent.getText(), structuredTypeToBeCheckNext);
                //    return ErrorType.INVALID_ARRAY_SCRIPTING;
                //}

                TypeDescriptor expressionType = visit(eachExpr);
                TypeDescriptor declaredType = indexList.get(0);//get first
                System.out.println("declaredType = " + declaredType);
                System.out.println("expressionType = " + expressionType);
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
                        if (expressionType instanceof IntegerBaseType && ((IntegerBaseType) expressionType).getValue() == null) {
                            //return null;
                            continue;
                        }
                    }

                    reportError(ctx, "Illegal operation [%s%s] with invalid array scripting [%s].\nExpected: %s.\nActual: %s",
                            structuredTypeId, ctx.parent.getText(), ctx.getText(), declaredType, expressionType);
                    return ErrorType.INVALID_ARRAY_SCRIPTING;
                }
            }
            return structuredTypeToBeCheckNext;
        }
        reportError(ctx,"Illegal array scripting operation [%s] on the type: %s",
                ctx.parent.getText(),structuredTypeToBeCheckNext);
        return ErrorType.INVALID_ARRAY_TYPE;
    }



    //@Override
    //public TypeDescriptor visitArrayScripting(PascalParser.ArrayScriptingContext ctx) {
    //    RuleContext parent = ctx.parent;
    //    System.out.println("parent getText = " + parent.getText());
    //    if (parent instanceof PascalParser.VariableContext) {
    //        PascalParser.VariableContext _parent = (PascalParser.VariableContext) parent;
    //        // get the array identifier
    //        String id = _parent
    //                .variableHead()
    //                .identifier()
    //                .getText().toLowerCase();
    //        TypeDescriptor arrayType = retrieve(id, ctx);
    //        System.out.println("retrieve arrayType = " + arrayType);
    //        // perform array type index range checking
    //        // index order follow "parenthesis first, expression(inside the parenthesis) next"
    //        // FIXME Nested type ,must extract the host type for further checking
    //        if (arrayType instanceof ArrayType
    //                || arrayType instanceof FormalParam) {
    //            ArrayType _arrayType = null;
    //
    //            if (arrayType instanceof ArrayType) _arrayType = (ArrayType) arrayType;
    //            if (arrayType instanceof FormalParam) {
    //                if (((FormalParam) arrayType).getHostType() instanceof ArrayType) {
    //                    _arrayType = (ArrayType) ((FormalParam) arrayType).getHostType();
    //                } else {
    //                    reportError(ctx, "Invalid array scripting operation [%s]. [%s] is not an array type.\nActual: %s",
    //                            _parent.getText(), id, arrayType);
    //                    return ErrorType.INVALID_ARRAY_TYPE;
    //                }
    //            }
    //            List<TypeDescriptor> indexList = _arrayType.getIndexList();
    //            StringBuilder stringBuilder = new StringBuilder();
    //
    //            List<PascalParser.ExpressionContext> expressionContextList = ctx.expression();
    //
    //            // there are various combinations of indexing,
    //            // but if number of expr > size of indexList, directly report error
    //            if (expressionContextList.size() > indexList.size() || indexCount > indexList.size()) {
    //                reportError(ctx, "Invalid array scripting operation [%s]. Operations dimension cannot match the declaration [%s]",
    //                        _parent.getText(), _arrayType);
    //                return ErrorType.INVALID_ARRAY_SCRIPTING;
    //            }
    //
    //            for (int i = 0; i < expressionContextList.size(); i++) {
    //                TypeDescriptor expressionType = visit(expressionContextList.get(i));
    //                System.out.println("expressionType = " + expressionType);
    //                // if index type cannot match where as it declared, reporting the error
    //                TypeDescriptor declaredType = indexList.get(indexCount++);
    //                System.out.println("declaredType = " + declaredType);
    //
    //                if (!expressionType.equiv(declaredType) || (
    //                        declaredType instanceof Character && expressionType instanceof StringLiteral
    //                )) {
    //
    //                    if (expressionType instanceof StringLiteral) {
    //                        String stringContent = ((StringLiteral) expressionType).getValue().replace("'", "");
    //                        if (stringContent.length() == 1) continue;
    //                    }
    //
    //                    if (declaredType instanceof Subrange) {
    //                        if (isValidSubrange((Subrange) declaredType, expressionType)) {
    //                            continue;
    //                        }
    //
    //                        // right expression evaluated to null, i.e. RUNTIME CHECK
    //                        //FIXME
    //                        if (expressionType instanceof IntegerBaseType && ((IntegerBaseType) expressionType).getValue() == null) {
    //                            //return null;
    //                            continue;
    //                        }
    //                    }
    //
    //                    if (stringBuilder.length() == 0) {
    //                        stringBuilder.append(String.format("Invalid array scripting operation [%s].",
    //                                _parent.getText()));
    //                    }
    //
    //                    stringBuilder.append(String.format(
    //                            "\n- Pos[%d]\n" +
    //                                    "Expected: %s,\nActual: %s",
    //                            indexCount - 1, declaredType, expressionType
    //                    ));
    //                }
    //            }
    //
    //            // if any errors
    //            if (stringBuilder.length() > 0) {
    //                reportError(ctx, stringBuilder.toString());
    //                return ErrorType.INVALID_ARRAY_SCRIPTING;
    //            }
    //
    //            int arrayIndexCount = arrayIndexCount(_parent);
    //            TypeDescriptor arrayContent = getArrayContent(arrayIndexCount, _arrayType);
    //            System.out.println("arrayContent = " + arrayContent);
    //            return arrayContent;
    //        }
    //    }
    //    return null;
    //}

    /**
     * Check whether given fieldName is a valid record field
     *
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


    @Override
    public TypeDescriptor visitAssignmentStatement(PascalParser.AssignmentStatementContext ctx) {
        String assignmentCtx = ctx.getText();
        System.out.println("ctx.getText() = " + assignmentCtx);

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
        if (_leftType instanceof StructuredBaseType) {
            // check whether array scripting is valid, if any
            _leftType = visit(ctx.variable());
        }
        System.out.println("_leftType = " + _leftType);

        if (_leftType.equiv(ErrorType.UNDEFINED_TYPE) && !typeTable.contains(id)) {
            reportError(ctx, "%s is undeclared", id);
            return null;
        }

        // suppress errors as already reported in other nodes
        if (_leftType.equiv(ErrorType.INVALID_ARRAY_SCRIPTING)
                || _leftType.equiv(ErrorType.INVALID_RECORD_FIELD)) {
            return null;
        }

        // if left identifier is an defined type identifier or enumerated type identifier
        // report errors
        if (_leftType instanceof EnumeratedIdentifier || typeTable.contains(id)) {
            if (_leftType.equiv(ErrorType.UNDEFINED_TYPE)) _leftType = typeTable.get(id);
            reportError(ctx, "Illegal assignment [%s]. Assigning value to identifier [%s - type: %s]\nis not allowed",
                    ctx.getText(), id, _leftType);
            return null;
        }

        TypeDescriptor rightType = visit(ctx.expression());
        System.out.println("expression rightType = " + rightType);

        // if expected enumerated type, suppress errors
        // i.e. skip visit expression node in trivial way
        // check whether right identifier is valid in the predefined value Map
        if (_leftType instanceof EnumeratedType) {
            EnumeratedType __leftType = (EnumeratedType) _leftType;
            Map<String, Integer> valueMap = __leftType.getValueMap();
            System.out.println("valueMap.containsKey(expression.toLowerCase()) = " + valueMap.containsKey(expression.toLowerCase()));

            String expressionValue = null;

            if (rightType instanceof EnumeratedIdentifier) {
                expressionValue = ((EnumeratedIdentifier) rightType).getValue();
            } else if (rightType instanceof EnumeratedType) {
                if (_leftType.equiv(rightType)) return null;
            } else if (rightType instanceof ErrorType) {
                //    suppress errors
                return null;
            } else {
                expressionValue = expression.toLowerCase();
            }

            if (!valueMap.containsKey(expressionValue)) {
                TypeDescriptor typeDescriptor = symbolTable.get(expression.toLowerCase());
                // if enumerated constant defined
                if (typeDescriptor instanceof EnumeratedIdentifier) {
                    EnumeratedIdentifier constant = (EnumeratedIdentifier) typeDescriptor;
                    if (constant.isConstant() && valueMap.containsKey(constant.getValue())) return null;
                }
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
            reportError(ctx, "Illegal assignment [%s], monadic operator cannot be applied on right operand: %s ",
                    assignmentCtx, expression);
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
                            "which must be between [%d] and [%d]",
                    assignmentCtx, expression, defaultIntegerType.MIN_VALUE, defaultIntegerType.MAX_VALUE);
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
                System.out.println("func _leftType = " + _leftType);
                Function function = (Function) _leftType;
                TypeDescriptor resultType = function.getResultType();
                System.out.println("rightType = " + rightType);
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

            System.out.println("ctx.getText(); = " + assignmentCtx);
            reportError(ctx, "Illegal assignment [%s] with incompatible types.\nExpected(lType): %s,\nActual(rType): %s",
                    assignmentCtx, _leftType.toString(), rightType.toString());
        }
        return null;
    }

    private boolean isValidSubrange(Subrange leftType, TypeDescriptor rightType) {
        Class<? extends TypeDescriptor> hostType = leftType.getHostType();
        TypeDescriptor lowerBound = leftType.getLowerBound();
        TypeDescriptor upperBound = leftType.getUpperBound();

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

        System.out.println("hostType = " + hostType);
        System.out.println("rightType class = " + rightType);
        System.out.println("rightType = " + rightType.getClass());
        // check other ordinal types
        if (rightType.getClass() == hostType) {
            if (rightType instanceof Boolean) {
                boolean rightValue = ((Boolean) rightType).getValue();
                boolean lowerValue = ((Boolean) lowerBound).getValue();
                boolean upperValue = ((Boolean) upperBound).getValue();
                return rightValue == lowerValue || rightValue == upperValue;
            }

            if (rightType instanceof IntegerBaseType) {
                Long rightValue = ((IntegerBaseType) rightType).getValue();

                // right expression evaluated to null, i.e. RUNTIME CHECK
                if (rightType == null) {
                    return true;
                }

                Long lowerValue = ((IntegerBaseType) lowerBound).getValue();
                Long upperValue = ((IntegerBaseType) upperBound).getValue();
                System.out.println("rightValue = " + rightValue);
                System.out.println("lowerValue = " + lowerValue);
                System.out.println("upperValue = " + upperValue);
                return rightValue >= lowerValue && rightValue <= upperValue;
            }

            // char subrange
            if (rightType instanceof StringLiteral) {
                String rightValue = ((StringLiteral) rightType).getValue();
                String lowerValue = ((StringLiteral) lowerBound).getValue();
                String upperValue = ((StringLiteral) upperBound).getValue();
                //System.out.println("lowerValue = " + lowerValue);
                //System.out.println("upperValue = " + upperValue);
                //System.out.println("rightValue = " + rightValue);
                //System.out.println("rightValue.compareTo(upperValue = " + rightValue.compareTo(upperValue));
                //System.out.println("rightValue.compareTo(lowerValue) = " + rightValue.compareTo(lowerValue));
                return rightValue.compareTo(lowerValue) >= 0 && rightValue.compareTo(upperValue) <= 0;
            }
        } else if (hostType == StringLiteral.class && rightType instanceof Character) {
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
        System.out.println("if expression type = " + type);
        if (!(type instanceof Boolean)) {
            reportError(ctx, "Invalid condition type of if statement [%s].\nExpected: %s,\nActual: %s",
                    ctx.expression().getText(), Type.BOOLEAN, type);
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
        System.out.println("***********CASE STATEMENT STARTS");
        System.out.println("ctx.getText() = " + ctx.getText());
        TypeDescriptor expression = visit(ctx.expression());
        System.out.println("case expression = " + expression);

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
                System.out.println("each vs expression = " + each + " " + expression);
                System.out.println("expression.equiv(each) = " + expression.equiv(each));

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

        System.out.println("***********CASE STATEMENT ENDS");
        return null;
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
        System.out.println("type isordinal = " + type);
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
        System.out.println();
        System.out.println("********Expression Starts********");
        System.out.println("expression ctx.getText() = " + ctx.getText());
        TypeDescriptor lType = visit(ctx.simpleExpression());
        TypeDescriptor rType = null;

        if (ctx.e2 != null) {
            rType = visit(ctx.e2);
            String operator = ctx.relationalOperator.getText();
            System.out.println("lType = " + lType);
            System.out.println("rType = " + rType);

            // suppress errors
            if (rType.equiv(ErrorType.INVALID_EXPRESSION)) {
                return rType;
            }

            // check whether operands are simple types
            if (!(isSimpleType(lType))) {
                System.out.println("not a simple type lType = " + lType);
                reportError(ctx, String.format(
                        "Illegal expression [%s]: Relational operator [%s] cannot" +
                                " be applied on left operand [%s - type: %s]",
                        ctx.getText(), operator, ctx.simpleExpression().getText(), lType));
                // suppress error
                return ErrorType.INVALID_EXPRESSION;
            }

            if (!(isSimpleType(rType))) {
                reportError(ctx, String.format("Illegal expression [%s]: Relational operator [%s] cannot" +
                                " be applied on right operand [%s - type: %s]",
                        ctx.getText(), operator, ctx.e2.getText(), rType));
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
                    if (lType instanceof StringLiteral || rType instanceof StringLiteral) return new Boolean();
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

                reportError(ctx, "Expression [" + ctx.getText() + "] types are incompatible! lType: " +
                        lType + " rType: " + rType);
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
        System.out.println("simple expr ctx.getText() = " + ctx.getText());
        TypeDescriptor lType = visit(ctx.term());
        TypeDescriptor rType;

        // if it involves 2 operands, need to check the type on both sides
        // then return the specific type
        if (null != ctx.simpleExpression()) {
            rType = visit(ctx.simpleExpression());
            String operator = ctx.additiveOperator.getText();

            // if is Logical operator OR
            if (operator.equalsIgnoreCase("or")) {
                // TODO subrange
                if (lType instanceof Subrange) {
                    return checkLogicalOpOperand(ctx, ((Subrange) lType).getLowerBound(), ctx.term().getText(), rType, ctx.simpleExpression().getText(), operator);
                }
                return checkLogicalOpOperand(ctx, lType, ctx.term().getText(), rType, ctx.simpleExpression().getText(), operator);
            }

            // if left operand is not int nor real
            if (!(lType instanceof IntegerBaseType) && !(lType instanceof FloatBaseType)) {
                // TODO Nested Type Refactor
                if (lType instanceof Subrange) {
                    TypeDescriptor lowerBound = ((Subrange) lType).getLowerBound();
                    if (!(lowerBound instanceof IntegerBaseType) && !(lowerBound instanceof FloatBaseType)) {
                        reportError(ctx, "Additive operator [%s] cannot be applied on the left operand of [%s]. Actual: %s",
                                operator, ctx.getText(), lType);
                        return ErrorType.INVALID_TYPE;
                    }
                } else if (lType instanceof NestedBaseType) {
                    TypeDescriptor hostType = ((NestedBaseType) lType).getHostType();
                    if (!(hostType instanceof IntegerBaseType) && !(hostType instanceof FloatBaseType)) {
                        reportError(ctx, "Additive operator [%s] cannot be applied on the left operand of [%s]. Actual: %s",
                                operator, ctx.getText(), lType);
                        return ErrorType.INVALID_TYPE;
                    }
                } else {
                    reportError(ctx, "Additive operator [%s] cannot be applied on the left operand of [%s]. Actual: %s",
                            operator, ctx.getText(), lType);
                    return ErrorType.INVALID_TYPE;
                }
            }
            // if right operand is not int nor real
            if (!(rType instanceof IntegerBaseType) && !(rType instanceof FloatBaseType)) {
                // TODO Nested Type Refactor
                if (rType instanceof Subrange) {
                    TypeDescriptor lowerBound = ((Subrange) rType).getLowerBound();
                    if (!(lowerBound instanceof IntegerBaseType) && !(lowerBound instanceof FloatBaseType)) {
                        reportError(ctx, "Additive Operator [%s] cannot be applied on the right operand of [%s]. Actual: %s",
                                operator, ctx.getText(), rType);
                        return ErrorType.INVALID_TYPE;
                    }
                } else if (rType instanceof NestedBaseType) {
                    TypeDescriptor hostType = ((NestedBaseType) rType).getHostType();
                    if (!(hostType instanceof IntegerBaseType) && !(hostType instanceof FloatBaseType)) {
                        reportError(ctx, "Additive Operator [%s] cannot be applied on the right operand of [%s]. Actual: %s",
                                operator, ctx.getText(), rType);
                        return ErrorType.INVALID_TYPE;
                    }
                } else {
                    reportError(ctx, "Additive Operator [%s] cannot be applied on the right operand of [%s]. Actual: %s",
                            operator, ctx.getText(), rType);
                    return ErrorType.INVALID_TYPE;
                }
            }

            //if (lType.equiv(Type.REAL) || rType.equiv(Type.REAL)) return Type.REAL;
            //else return Type.INTEGER;
            if (lType instanceof FloatBaseType || rType instanceof FloatBaseType) {
                return new FloatBaseType();
                //}  else return IntegerBaseType.copy(defaultIntegerType);
            } else {
                System.out.println("ctx.getText() = " + ctx.getText());
                System.out.println("lType = " + lType);
                System.out.println("rType = " + rType);

                // subrange, params evaluation would be evaluated at the runtime
                if (!(lType instanceof Subrange) && !(rType instanceof Subrange)
                        && !(lType instanceof NestedBaseType) && !(rType instanceof NestedBaseType)
                ) {
                    if (((IntegerBaseType) lType).isConstant() && ((IntegerBaseType) rType).isConstant()) {
                        // only checks for constant & literal
                        Long lValue = ((IntegerBaseType) lType).getValue();
                        Long rValue = ((IntegerBaseType) rType).getValue();
                        return DefaultIntegerType.of(lValue + rValue);
                    }
                }
                //return DefaultIntegerType.of();
                return IntegerBaseType.copy(defaultIntegerType);
            }

        }
        // only 1 term
        return lType;
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
                _lType = ((NestedBaseType) _lType).getHostType();
            }

            // TODO SUbrange, ArrayType,
            // extract hostType if nestedType
            TypeDescriptor _rType = rType;
            if (rType instanceof NestedBaseType) {
                _rType = ((NestedBaseType) rType).getHostType();
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
                    System.out.println("lType.equiv(Type.INTEGER) = " + lType.equiv(Type.INTEGER));
                    System.out.println("rType.equiv(Type.INTEGER) = " + rType.equiv(Type.INTEGER));
                    if (!(_lType instanceof IntegerBaseType) || !(_rType instanceof IntegerBaseType)) {
                        reportError(ctx, "The operands of integer division must be Integer: " +
                                "with _Type: " + lType +
                                " with rType: " + rType);
                    }
                    return IntegerBaseType.copy(defaultIntegerType);
                case "/":
                    // real division, operands could be int/real
                    return DefaultFloatType.instance;
                case "mod":
                case "MOD":
                    // modulus reminder division, operands must be integer
                    if (!(_lType instanceof IntegerBaseType) || !(_rType instanceof IntegerBaseType)) {
                        reportError(ctx, "The operands of modulus must be Integer: " +
                                "with lType: " + lType +
                                " with rType: " + rType);
                    }
                    return IntegerBaseType.copy(defaultIntegerType);
                default:
                    // other multiplicative operators: * return specific type
                    if (_lType instanceof FloatBaseType || _rType instanceof FloatBaseType)
                        return DefaultFloatType.instance;
                    else return IntegerBaseType.copy(defaultIntegerType);
            }
        }
        return lType;
    }

    private TypeDescriptor checkLogicalOpOperand(ParserRuleContext ctx,
                                                 TypeDescriptor lType, String lOp,
                                                 TypeDescriptor rType, String rOp,
                                                 String operator) {
        if (!lType.equiv(Type.BOOLEAN)) {
            reportError(ctx, String.format("Logical operator [%s] cannot" +
                            " be applied on left operand [%s] of expression [%s]: %s",
                    operator, lOp, ctx.getText(), lType));
            return ErrorType.INVALID_TYPE;
        }
        if (!rType.equiv(Type.BOOLEAN)) {
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
        System.out.println("visitSignedFactor = " + ctx.getText());
        System.out.println("ctx.factor().getClass() = " + ctx.factor().getClass());

        System.out.println("factor type = " + factor);
        // involves monadic arithmetic expression, check the operand factor
        // i.e. signedFactor
        if (ctx.monadicOperator != null) {
            String monadicOp = ctx.monadicOperator.getText();

            if (!(factor instanceof IntegerBaseType) && !(factor instanceof FloatBaseType)) {
                //reportError(ctx, "Monadic arithmetic operator " + monadicOp +
                //        " cannot be applied on the operand: " + factor);
                //TODO suppress errors
                return ErrorType.INVALID_MONADIC_OPERATION;
            } else if (factor instanceof IntegerBaseType) {
                System.out.println("ctx.factor().getText() = " + ctx.factor().getText());
                System.out.println("signed factor = " + factor);
                Long numberValue = ((IntegerBaseType) factor).getValue();
                RuleContext parentContext = ctx.parent.parent.parent.parent;

                // update numberValue accords to the monadic operator
                switch (monadicOp) {
                    case "-":
                        System.out.println("numberValue -> = " + numberValue);
                        numberValue = -numberValue;
                        System.out.println("-numberValue -> = " + numberValue);
                        break;
                    case "+":
                        break;
                }

                System.out.println("current numberValue = " + numberValue);
                System.out.println("parentContextTT = " + parentContext.getClass());
                // if is the last constant node, gathering all the errors if any
                if (!(parentContext instanceof PascalParser.FactorExprContext)) {
                    if (!hasNoOverflow(numberValue)) return ErrorType.INTEGER_OVERFLOW;
                    if (!hasNoUnderflow(numberValue)) return ErrorType.INTEGER_UNDERFLOW;
                    // no errors directly return factor
                    return factor;
                } else {
                    // update value(copy) and return this node to be processed in the upper node
                    // ((IntegerBaseType) factor).setValue(numberValue);
                    // FIXME:this might cause potential problems
                    //
                    System.out.println("pass to upper node = " + ((IntegerBaseType) factor).getValue());
                    System.out.println("factor updated = " + factor);
                    //IntegerBaseType updatedNumber = new IntegerBaseType();
                    //updatedNumber.setValue(numberValue);
                    IntegerBaseType copy = IntegerBaseType.copy((IntegerBaseType) factor);
                    copy.setValue(numberValue);
                    return copy;
                }
            }
        } else {
            // unsigned factor
            if (factor instanceof IntegerBaseType) {
                // integer overflow checking
                Long numberValue = null;
                // if right operand is var
                if (ctx.factor() instanceof PascalParser.FactorVarContext) {
                    numberValue = ((IntegerBaseType) factor).getValue();
                }
                System.out.println("numberValue = " + numberValue);

                // if numberValue == null, then it should be checked at runtime
                if (numberValue != null && !hasNoOverflowOrUnderflow(numberValue, true, false)) {
                    // error reported in assignment node
                    return ErrorType.INTEGER_OVERFLOW;
                }
            }
        }
        return factor;
    }

    private int arrayIndexCount(PascalParser.VariableContext ctx) {
        int count = 0;
        System.out.println("ctx.getText() = " + ctx.getText());
        List<PascalParser.ArrayScriptingContext> arrayScriptingContexts = ctx.arrayScripting();
        for (PascalParser.ArrayScriptingContext arrayScriptingContext : arrayScriptingContexts) {
            count += arrayScriptingContext.expression().size();
        }
        System.out.println("index count-> " + count);
        return count;
    }

    /**
     * FIXME always return the new array type with the first index element removed...
     * @param arrayIndexCount
     * @param arrayType
     * @return
     */
    private TypeDescriptor getArrayContent(int arrayIndexCount, ArrayType arrayType) {
        TypeDescriptor componentType = arrayType.getComponentType();
        List<TypeDescriptor> indexList = arrayType.getIndexList();
        int indexListSize = indexList.size();
        System.out.println("indexListSize = " + indexListSize);
        if (arrayIndexCount == indexListSize-1) {
            return componentType;
        }
        //concatenate and return the new array type
        List<TypeDescriptor> newIndexList = new ArrayList<>(List.copyOf(indexList));
        System.out.println("old newIndexList = " + newIndexList);
        for (int i = 0; i <= arrayIndexCount; i++) {
            newIndexList.remove(i);
        }
        System.out.println("newIndexList = " + newIndexList);
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
        System.out.println("factor Var ctx.getText() = " + ctx.getText());
        System.out.println("ctx.variable().children.size() = " + ctx.variable().children.size());

        if (ctx.variable().children.size() > 1) {
            return visit(ctx.variable());
        }
        TypeDescriptor type = retrieve(ctx.getText(), false, ctx);
        System.out.println("type = " + type);
        return type;
    }

    @Override
    public TypeDescriptor visitFactorExpr(PascalParser.FactorExprContext ctx) {
        return visit(ctx.expression());
    }

    @Override
    public TypeDescriptor visitFactorFuncDesignator(PascalParser.FactorFuncDesignatorContext ctx) {
        System.out.println("factor func Designator");
        TypeDescriptor returnType = visit(ctx.functionDesignator());
        System.out.println("returnType = " + returnType);
        return returnType;
    }

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
        if (!type.equiv(Type.BOOLEAN)) {
            reportError(ctx, "Relational operator [NOT] cannot be applied on [%s] operand [%s]: %s", ctx.getText(), ctx.factor().getText(), type.toString());
            return ErrorType.INVALID_TYPE;
        }
        return type;
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
                return DefaultIntegerType.of(String.valueOf(ctx.NUM_INT()), true);
            case PascalParser.NUM_REAL:
                //return Type.REAL;
                return DefaultFloatType.instance;
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
        return new StringLiteral(ctx.getText());
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
