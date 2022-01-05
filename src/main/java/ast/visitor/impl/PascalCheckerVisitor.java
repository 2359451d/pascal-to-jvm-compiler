package ast.visitor.impl;

import ast.visitor.PascalBaseVisitor;
import ast.visitor.PascalParser;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.Interval;
import org.apache.commons.lang3.StringUtils;
import runtime.RunTimeLibFactory;
import type.*;
import type.enumerated.EnumeratedIdentifier;
import type.enumerated.EnumeratedType;
import type.error.ErrorType;
import type.param.ActualParam;
import type.param.FormalParam;
import type.primitive.Boolean;
import type.primitive.Character;
import type.primitive.Primitive;
import type.primitive.floating.DefaultFloatType;
import type.primitive.floating.FloatBaseType;
import type.primitive.integer.DefaultIntegerType;
import type.primitive.integer.Integer32;
import type.primitive.integer.IntegerBaseType;
import type.utils.SymbolTable;
import type.utils.Table;
import type.utils.TypeTable;
import util.ErrorMessage;

import java.util.*;

public class PascalCheckerVisitor extends PascalBaseVisitor<TypeDescriptor> {


    // Contextual errors
    private int errorCount = 0;

    private CommonTokenStream tokens;

    private SymbolTable<TypeDescriptor> symbolTable = new SymbolTable<>();
    private TypeTable<TypeDescriptor> typeTable = new TypeTable<>();

    // Max Builtin Integer Type (default - Integer32)
    static final IntegerBaseType defaultIntegerType = new Integer32();

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
        symbolTable.put("maxint", new Integer32());

        RunTimeLibFactory.fillTable(symbolTable);
    }

    private void define(String id, TypeDescriptor type,
                        ParserRuleContext ctx) {

        Table<String, TypeDescriptor> table = null;
        if (ctx instanceof PascalParser.TypeDefinitionContext) {
            // new entry is inserted into the type table
            table = typeTable;
        } else table = symbolTable;

        // Add id with its type to the type/symbol table, checking
        // that id is not already declared in the same scope.
        // IGNORE CASE
        boolean ok = table.put(id.toLowerCase(), type);
        if (!ok)
            reportError(ctx, id + " is redeclared");
    }

    private TypeDescriptor retrieve(String id, boolean notSuppressError,
                                    ParserRuleContext ctx) {
        Table<String, TypeDescriptor> table = null;

        if (ctx instanceof PascalParser.TypeIdentifierContext) {
            table = typeTable;
        } else table = symbolTable;

        // Retrieve id's type from the symbol table.
        // Case insensitive
        TypeDescriptor type = table.get(id.toLowerCase());
        if (type == null) {
            if (notSuppressError) reportError(ctx, "Identifier %s is undeclared", id);
            return ErrorType.UNDEFINED_TYPE;
        } else
            return type;
    }

    private TypeDescriptor retrieve(String id, ParserRuleContext occ) {
        // default: do not suppress identifier undeclared errors
        return retrieve(id, true, occ);
    }

    @Override
    public TypeDescriptor visitProgram(PascalParser.ProgramContext ctx) {
        predefine();
        return super.visitProgram(ctx);
    }

    @Override
    public TypeDescriptor visitBlock(PascalParser.BlockContext ctx) {
        System.out.println("Block Starts*************************");
        symbolTable.displayCurrentScope();
        visitChildren(ctx);
        System.out.println("Block Ends*************************");
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
        System.out.println("Variable Decl Starts*************");

        typeTable.showAllTheTypes();

        List<PascalParser.IdentifierContext> identifierContextList = ctx.identifierList().identifier();
        for (PascalParser.IdentifierContext identifierContext : identifierContextList) {
            String id = identifierContext.IDENT().getText();
            TypeDescriptor type = visit(ctx.type_());
            define(id, type, ctx);
        }
        symbolTable.displayCurrentScope();
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
        BaseType constantType = (BaseType) visit(ctx.constant());
        constantType.setConstant(true);
        define(id, constantType, ctx);
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
                return IntegerBaseType.copy(defaultIntegerType);
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

    @Override
    public TypeDescriptor visitFileType(PascalParser.FileTypeContext ctx) {
        TypeDescriptor type = visit(ctx.type_());
        return new File(type);
    }

    //private void checkAcceptableType(Set<String> acceptableTypes, Signature signature,
    //                                 ParserRuleContext ctx) {
    //    System.out.println("=======checkAcceptableTypes starts=========");
    //    System.out.println("acceptableTypes = " + acceptableTypes);
    //    System.out.println("actualSignature given = " + signature);
    //    List<String> paramList = signature.getParamList();
    //    for (String param : paramList) {
    //        //System.out.println("current param = " + param);
    //        if (!acceptableTypes.contains(param)) {
    //            reportError(ctx, "type: " + param
    //                    + " is not supported");
    //        }
    //    }
    //    System.out.println("=======checkAcceptableTypes ends=========");
    //}

    //@Deprecated
    //private void checkAcceptableSignature(SignatureSet signatureSet, SignatureSet signature,
    //                                      ParserRuleContext ctx) {
    //    System.out.println("=======checkAcceptableSignature starts=========");
    //    System.out.println("actualSignature given = " + signature);
    //    Set<String> typeOrderToBeChecked = signatureSet.getTypeOrderToBeChecked();
    //    System.out.println("typeOrderToBeChecked = " + typeOrderToBeChecked);
    //    Set<Signature> acceptableSignatures = signatureSet.getAcceptableSignatures();
    //    System.out.println("acceptableSignatures = " + acceptableSignatures);
    //
    //    ArrayList<Signature> actualSignatures = new ArrayList<>(signature.getAcceptableSignatures());
    //    Signature actualSignature = actualSignatures.get(0);
    //    List<String> paramList = actualSignature.getParamList();
    //    System.out.println("paramList = " + paramList);
    //
    //    // trim the signature (as there are variable number of params)
    //    List<String> trimmedParamList = paramList.stream().distinct().collect(Collectors.toList());
    //    Signature trimmedSignature = new Signature(trimmedParamList);
    //    //System.out.println("trimmedParamList = " + trimmedParamList);
    //    System.out.println("trimmedSignature = " + trimmedSignature);
    //
    //    if (!acceptableSignatures.contains(trimmedSignature)) {
    //        Set<String> acceptableTypes = signatureSet.getAcceptableTypes();
    //        System.out.println("acceptableTypes = " + acceptableTypes);
    //        Set<String> coveredTypes = signature.getAcceptableTypes();
    //        System.out.println("acceptableTypes1 = " + coveredTypes);
    //        boolean orderCheck = false;
    //        for (String s : coveredTypes) {
    //            if (acceptableTypes.contains(s)) {
    //                orderCheck = true;
    //                break;
    //            }
    //        }
    //
    //        if (orderCheck) {
    //            boolean notDeclared = true;
    //            for (int i = 0; i < trimmedParamList.size(); i++) {
    //                String actualParam = trimmedParamList.get(i);
    //                for (Signature acceptableSignature : acceptableSignatures) {
    //                    List<String> acceptableSignatureParamList = acceptableSignature.getParamList();
    //                    if (typeOrderToBeChecked.contains(actualParam) && !acceptableSignatureParamList.get(i).equals(actualParam)) {
    //                        reportError(ctx, "signature " + signature
    //                                + " is not supported");
    //                    }
    //                    //} else if (!typeOrderToBeChecked.contains(actualParam)){
    //                    //    if (trimmedParamList.size() <= acceptableSignatureParamList.size()) {
    //                    //
    //                    //    }
    //                    //}
    //                }
    //            }
    //        } else {
    //            for (String coveredType : signature.getAcceptableTypes()) {
    //                if (!acceptableTypes.contains(coveredType))
    //                    reportError(ctx, "signature " + signature + " is not supported");
    //            }
    //        }
    //    }


    // further checking, specifically for builtin overloading proc/func
    //if (!acceptableSignatures.contains(signature)) {
    //    for (int i = 0; i < paramList.size(); i++) {
    //        Type actualParam = paramList.get(i);
    //        for (Signature acceptableSignature : acceptableSignatures) {
    //            List<Type> acceptableSignatureParamList = acceptableSignature.getParamList();
    //            Set<Type> acceptableTypes = acceptableSignature.getAcceptableTypes();
    //            if (typeOrderToBeChecked.contains(actualParam.getClass().getName())) {
    //                System.out.println("TO be checked");
    //                if (!acceptableSignatureParamList.get(i).equiv(actualParam)) {
    //                    System.out.println("actualParam = " + actualParam);
    //                    System.out.println("acceptableSignatureParamList.get(i) = " + acceptableSignatureParamList.get(i));
    //                    reportError("signature " + signature
    //                            + " is not supported", ctx);
    //                }
    //            } else if (!acceptableTypes.contains(actualParam)){
    //                reportError("type " + actualParam +" in "+ "signature " + signature
    //                        + " is not supported", ctx);
    //            }
    //        }
    //    }
    //for (Type actualParam : paramList) {
    //}
    //}
    //
    //System.out.println("=======checkAcceptableSignature ends=========");
    //
    //}

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
            define(identifier.getText(), new FormalParam(type, null), ctx);
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
            define(identifier.getText(), new FormalParam(type, "var"), ctx);
        }
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
    public TypeDescriptor visitProcedureDeclaration(PascalParser.ProcedureDeclarationContext ctx) {
        String id = ctx.identifier().getText().toLowerCase();
        ArrayList<TypeDescriptor> params = new ArrayList<>();

        System.out.println("PROC DECL Starts*************************");
        symbolTable.enterLocalScope();

        // if the procedure has formal parameters
        if (ctx.formalParameterList() != null) {
            List<PascalParser.FormalParameterSectionContext> formalParameterSectionList = ctx.formalParameterList().formalParameterSection();
            for (PascalParser.FormalParameterSectionContext paramSection : formalParameterSectionList) {
                // define parameter group in current scope of type Param(Type,String:label)
                visit(paramSection);
            }
            // all formal params set up
            Map<String, TypeDescriptor> allParams = symbolTable.getAllVarInCurrentScope();
            allParams.forEach((k, v) -> params.add(v));
        }
        define(id, new Procedure(params), ctx);
        System.out.println("Define Proc signature");
        symbolTable.displayCurrentScope();
        System.out.println("id = " + id);
        System.out.println("symbolTable.get(id) = " + symbolTable.get(id));
        visit(ctx.block()); // scope & type checking in current proc scope
        symbolTable.exitLocalScope(); // back to last scope
        define(id, new Procedure(params), ctx);
        System.out.println("PROC DECL ENDS*************************");
        symbolTable.displayCurrentScope();
        return null;
    }

    /**
     * Check whether a Function has result assignment or not
     * Report errors if the result assignment statement is missing
     *
     * @param ctx - function rule context
     * @return boolean - true if function has result assignment, otherwise return false
     */
    private boolean functionHasResultAssignment(PascalParser.FunctionDeclarationContext ctx) {
        List<PascalParser.StatementContext> statementContextList = ctx.block().compoundStatement().statements().statement();
        boolean functionHasResultAssignment = false;
        for (PascalParser.StatementContext statementContext : statementContextList) {
            if (visit(statementContext) instanceof Function) {
                functionHasResultAssignment = true;
            }
        }
        return functionHasResultAssignment;
    }

    /**
     * functionDeclaration
     * : FUNCTION identifier (formalParameterList)? COLON resultType SEMI block
     * ;
     *
     * @param ctx
     * @return
     */
    @Override
    public TypeDescriptor visitFunctionDeclaration(PascalParser.FunctionDeclarationContext ctx) {
        String id = ctx.identifier().getText();
        TypeDescriptor resultType = visit(ctx.resultType());
        ArrayList<TypeDescriptor> params = new ArrayList<>();

        System.out.println("Func DECL Starts*************************");
        symbolTable.enterLocalScope();

        // if the procedure has formal parameters
        if (ctx.formalParameterList() != null) {
            List<PascalParser.FormalParameterSectionContext> formalParameterSectionList = ctx.formalParameterList().formalParameterSection();
            for (PascalParser.FormalParameterSectionContext paramSection : formalParameterSectionList) {
                // define parameter group in current scope of type Param(Type,String:label)
                visit(paramSection);
            }
            // all formal params set up
            Map<String, TypeDescriptor> allParams = symbolTable.getAllVarInCurrentScope();
            allParams.forEach((k, v) -> params.add(v));
        }
        Function function = new Function(params, resultType);
        define(id, function, ctx);
        System.out.println("Define Func signature");
        symbolTable.displayCurrentScope();
        System.out.println("id = " + id);
        System.out.println("symbolTable.get(id) = " + symbolTable.get(id));

        if (!functionHasResultAssignment(ctx)) {
            reportError(ctx, "Missing result assignment in Function: %s", function);
        }

        symbolTable.exitLocalScope(); // back to last scope
        define(id, new Function(params, resultType), ctx);
        System.out.println("FUNC DECL ENDS*************************");
        symbolTable.displayCurrentScope();

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
        if (formalParam.getLabel() == null) return formalParam.getType().equiv(actualParam.getType());
        if (!(formalParam.getLabel().equals(actualParam.getLabel()))) return false;
        return formalParam.getType().equiv(actualParam.getType());
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
        if (actualParameters.size() < formalParameters.size()) {
            //List<TypeDescriptor> actualParametersContent = new ArrayList<>();
            stringBuilder.append(String.format(
                    "The number of actual parameters cannot match the signature of [%s]!",
                    name));
            stringBuilder.append(String.format("\nActual[%s - size: %d]", ctx.getText(), actualParameters.size()));
            actualParameters.forEach(each -> {
                stringBuilder.append("\n- ").append(visit(each));
            });
            stringBuilder.append(String.format("\nExpected[size: %d]", formalParameters.size()));
            formalParameters.forEach(each -> {
                stringBuilder.append("\n- ").append(each);
            });

            errorMessage.setMessageSequence(stringBuilder);
        } else {
            //AtomicInteger count = new AtomicInteger(0);
            //List<String> messageSequence = new ArrayList<>();
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
                                    " Actual: %s, Expected: %s",
                            i, _actual, _formal
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
        String id = ctx.identifier().getText();
        System.out.println("id = " + id);
        TypeDescriptor signature = retrieve(id, ctx);
        System.out.println("signature type = " + signature);
        symbolTable.displayCurrentScope();

        // suppress error, proc identifier not defined
        if (signature.equiv(ErrorType.UNDEFINED_TYPE)) {
            return null;
        }

        // Only report while proc id is defined but is used with other type
        if (!(signature.equiv(ErrorType.UNDEFINED_TYPE)) && !(signature instanceof Procedure)) {
            reportError(ctx, "Illegal procedure statement [%s] which is not a procedure",
                    ctx.getText());
            return ErrorType.INVALID_PROCEDURE_TYPE;
        } else {

            System.out.println("Signature = " + signature);
            Procedure _signature = (Procedure) signature;
            //check signatures if has actual parameters
            List<PascalParser.ActualParameterContext> actualParameterContextList = new ArrayList<>();
            if (ctx.parameterList() != null) {
                actualParameterContextList = ctx.parameterList().actualParameter();
            }
            ErrorMessage errorMessage = checkSignature(_signature, actualParameterContextList, ctx);
            System.out.println("booleanMessage = " + errorMessage);
            if (errorMessage.hasErrors()) {
                reportError(ctx, errorMessage.getMessageSequence());
            }

            //// type checking
            //SignatureSet signatureSet = (SignatureSet) signature;
            //Set<String> acceptableTypes = signatureSet.getAcceptableTypes();
            ////System.out.println("acceptableTypes = " + acceptableTypes);
            ////Type expectedParamSeq = type.domain;
            //
            //// check actual_params == definition in the symbol table
            //SignatureSet actualSignatureSet = (SignatureSet) visit(ctx.parameterList());
            ////System.out.println("actualSignature = " + actualSignature);
            //
            //// first checking whether the actual params given are supported or not
            ////checkAcceptableType(acceptableTypes, actualSignature, ctx);
            //// then check whether there are limitations on params order & number or not
            //checkAcceptableSignature(signatureSet, actualSignatureSet, ctx);
            //
            //
            ////Set<String> set = new HashSet<>();
            ////set.add(s.getClass().getName());
            ////System.out.println("set = " + set);
            ////System.out.println(set.contains(Str.class.getName()));
            //
            ////Type actualParamSeq = visit(ctx.parameterList());
            ////if (!actualParamSeq.equiv(expectedParamSeq)) {
            ////    reportError("type is " + actualParamSeq
            ////            + ", should be " + expectedParamSeq, ctx);
            ////}
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
        String id = ctx.identifier().getText();
        System.out.println("id = " + id);
        TypeDescriptor signature = retrieve(id, ctx);
        symbolTable.displayCurrentScope();
        //System.out.println("retrieve = " + retrieve);

        // Only report while function id is defined but is used with other type: proc .etc
        if (!(signature.equiv(ErrorType.UNDEFINED_TYPE)) && !(signature instanceof Function)) {
            //reportError(ctx, id + " is not a function");
            return ErrorType.INVALID_FUNCTION_TYPE;
        } else {
            System.out.println("Signature = " + signature);
            Function _signature = (Function) signature;
            //check signatures
            List<PascalParser.ActualParameterContext> actualParameterContextList = ctx.parameterList().actualParameter();
            ErrorMessage errorMessage = checkSignature(_signature, actualParameterContextList, ctx);
            System.out.println("booleanMessage = " + errorMessage);
            if (errorMessage.hasErrors()) {
                reportError(ctx, errorMessage.getMessageSequence());
            }

            //// type checking
            //SignatureSet signatureSet = (SignatureSet) signature;
            //Set<String> acceptableTypes = signatureSet.getAcceptableTypes();
            ////System.out.println("acceptableTypes = " + acceptableTypes);
            ////Type expectedParamSeq = type.domain;
            //
            //// check actual_params == definition in the symbol table
            //SignatureSet actualSignatureSet = (SignatureSet) visit(ctx.parameterList());
            ////System.out.println("actualSignature = " + actualSignature);
            //
            //// first checking whether the actual params given are supported or not
            ////checkAcceptableType(acceptableTypes, actualSignature, ctx);
            //// then check whether there are limitations on params order & number or not
            //checkAcceptableSignature(signatureSet, actualSignatureSet, ctx);
            //
            //
            ////Set<String> set = new HashSet<>();
            ////set.add(s.getClass().getName());
            ////System.out.println("set = " + set);
            ////System.out.println(set.contains(Str.class.getName()));
            //
            ////Type actualParamSeq = visit(ctx.parameterList());
            ////if (!actualParamSeq.equiv(expectedParamSeq)) {
            ////    reportError("type is " + actualParamSeq
            ////            + ", should be " + expectedParamSeq, ctx);
            ////}
        }
        System.out.println("*******************Function CALL ENDS");
        return ((Function) signature).getResultType();
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

        //ArrayList<Type> types = new ArrayList<>();
        //List<PascalParser.ActualParameterContext> actualParameters = ctx.actualParameter();
        //for (PascalParser.ActualParameterContext actualParameter : actualParameters) {
        //    TypeDescriptor type = visit(actualParameter);
        //    types.add(type);
        //}
        //return new Sequence(types);

        //List<String> paramList = new ArrayList<>();
        //Set<String> typeSets = new HashSet<>();
        //List<PascalParser.ActualParameterContext> actualParameters = ctx.actualParameter();
        //for (PascalParser.ActualParameterContext actualParameter : actualParameters) {
        //    TypeDescriptor type = visit(actualParameter);
        //    paramList.add(type.toString());
        //    typeSets.add(type.getClass().getName());
        //}
        //Signature signature = new Signature(paramList);
        //HashSet<Signature> signatures = new HashSet<>();
        //signatures.add(signature);
        //return new SignatureSet(signatures, typeSets);
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
                reportError(ctx, "Initial Expression [%s: %s] " +
                                "of For Statement [%s]\n " +
                                "must be the same ordinal type as control variable [%s: %s]",
                        ctx.forList().initialValue().getText(),
                        initType.toString(), forHeader,
                        ctx.identifier().getText(), controlType.toString());
            } else if (!finalType.equiv(controlType)) {
                reportError(ctx, "Final Expression [%s: %s] " +
                                "of For Statement [%s]\n " +
                                "must be the same ordinal type as control variable [%s: %s]",
                        ctx.forList().finalValue().getText(),
                        finalType.toString(), forHeader,
                        ctx.identifier().getText(), controlType.toString());
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


    @Override
    public TypeDescriptor visitAssignmentStatement(PascalParser.AssignmentStatementContext ctx) {
        String assignmentCtx = ctx.getText();
        System.out.println("ctx.getText() = " + assignmentCtx);

        String id = ctx.variable().getText();
        String expression = ctx.expression().getText();

        // suppress errors
        TypeDescriptor leftType = retrieve(id, false, ctx);
        System.out.println("leftType = " + leftType);

        if (leftType.equiv(ErrorType.UNDEFINED_TYPE) && !typeTable.containsKey(id)) {
            reportError(ctx, "%s is undeclared", id);
            return null;
        }

        // if left identifier is an defined type identifier or enumerated type identifier
        // report errors
        if (leftType instanceof EnumeratedIdentifier || typeTable.containsKey(id)) {
            if (leftType.equiv(ErrorType.UNDEFINED_TYPE)) leftType = typeTable.get(id);
            reportError(ctx, "Illegal assignment [%s]. Assigning value to identifier [%s - type: %s]\nis not allowed",
                    ctx.getText(), id, leftType);
            return null;
        }

        // if expected enumerated type, suppress errors
        // i.e. skip visit expression node in trivial way
        // check whether right identifier is valid in the predefined value Map
        if (leftType instanceof EnumeratedType) {
            EnumeratedType _leftType = (EnumeratedType) leftType;
            Map<String, Integer> valueMap = _leftType.getValueMap();
            System.out.println("valueMap.containsKey(expression.toLowerCase()) = " + valueMap.containsKey(expression.toLowerCase()));
            if (!valueMap.containsKey(expression.toLowerCase())) {
                TypeDescriptor typeDescriptor = symbolTable.get(expression.toLowerCase());
                // if enumerated constant defined
                if (typeDescriptor != null && typeDescriptor instanceof EnumeratedIdentifier) {
                    EnumeratedIdentifier constant = (EnumeratedIdentifier) typeDescriptor;
                    if (constant.isConstant() && valueMap.containsKey(constant.getValue())) return null;
                }
                if (typeDescriptor == null) {
                    reportError(ctx, "Illegal enumerated type assignment [%s]. Right operand [%s] is not defined.\nExpected: [%s]",
                            assignmentCtx, expression, valueMap.keySet());
                } else
                    reportError(ctx, "Illegal enumerated type assignment [%s]. Right operand [%s] is not valid.\nExpected: [%s]",
                            assignmentCtx, expression, valueMap.keySet());
            }
            return null;
        }

        TypeDescriptor rightType = visit(ctx.expression());
        System.out.println("expression rightType = " + rightType);

        if (rightType.equiv(ErrorType.UNDEFINED_TYPE)) {
            reportError(ctx, "Illegal assignment [%s]. Right operand [%s] is not defined.",
                    assignmentCtx, ctx.expression().getText());
            return null;
        }

        if (rightType.equiv(ErrorType.INVALID_EXPRESSION)) {
            // suppress errors in this node (error already reported in other nodes)
            return null;
        }

        // TODO: constant reassignemnt, ignore ErrorType
        if (leftType instanceof BaseType) {
            boolean isConstant = ((BaseType) leftType).isConstant();
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
        if (leftType instanceof Function && (ctx.parent.parent.parent.parent.parent
                .parent.parent) instanceof PascalParser.ProgramContext) {
            reportError(ctx, "Illegal assignment [%s], assigning value to a function is not allowed here: %s ",
                    assignmentCtx, leftType);
            return null;
        }

        // cannot assign value to a procedure both in trivial assignment
        // and within procedure body
        if (leftType instanceof Procedure) {
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

        if (leftType instanceof Subrange) {
            if (isValidSubrange((Subrange) leftType, rightType, ctx)) return null;
            // right expression evaluated to null, i.e. RUNTIME CHECK
            if (rightType instanceof IntegerBaseType && ((IntegerBaseType) rightType).getValue() == null) {
                return null;
            }
            reportError(ctx, "invalid subrange %s", assignmentCtx);
            return null;
        }

        if (!leftType.equiv(rightType)) {
            // exception case when left is real, it is acceptable though right is int
            if (leftType.equiv(Type.REAL) && rightType.equiv(Type.INTEGER)) return null;
            // exception case when left is character, and right is string literal(length must be 1)
            if (leftType.equiv(Type.CHARACTER) && rightType.equiv(Type.STRING)) {
                String content = expression.replace("'", "");
                if (content.length() == 1) return null;
            }

            // check result type of a Function
            // this assignment only allowed within Function body
            if (leftType instanceof Function) {

                Function function = (Function) leftType;
                TypeDescriptor resultType = function.getResultType();
                if (!rightType.equiv(resultType)) {

                    // exception case when left is real, it is acceptable though right is int
                    if (resultType.equiv(Type.REAL) && rightType.equiv(Type.INTEGER)) return function;
                    // exception case when left is character, and right is string literal(length must be 1)
                    if (resultType.equiv(Type.CHARACTER) && rightType.equiv(Type.STRING)) {
                        String content = expression.replace("'", "");
                        if (content.length() == 1) return function;
                    }

                    reportError(ctx, "Assignment: [%s] failed, right operand type: %s cannot assigns to Function: %s",
                            assignmentCtx, rightType, function);
                }
                return function; // return Function itself for further check in the upper nodes
            }

            // check type compatibility when right operand is evaluated from function
            if (rightType instanceof Function) {
                Function function = (Function) rightType;
                TypeDescriptor resultType = function.getResultType();
                if (!leftType.equiv(resultType)) {

                    // exception case when left is real, it is acceptable though right is int
                    if (leftType.equiv(Type.REAL) && resultType.equiv(Type.INTEGER)) return function;
                    // exception case when left is character, and right is string literal(length must be 1)
                    if (leftType.equiv(Type.CHARACTER) && resultType.equiv(Type.STRING)) {
                        String content = expression.replace("'", "");
                        if (content.length() == 1) return function;
                    }

                    reportError(ctx, "Illegal assignment [%s]. Function: %s result type cannot match the left operand: %s",
                            assignmentCtx, function, leftType);
                }
                return null;
            }

            System.out.println("ctx.getText(); = " + assignmentCtx);
            reportError(ctx, "Illegal assignment [%s] with incompatible types. Expected(lType): %s, Actual(rType): %s",
                    assignmentCtx, leftType.toString(), rightType.toString());
        }
        return null;
    }

    private boolean isValidSubrange(Subrange leftType, TypeDescriptor rightType, PascalParser.AssignmentStatementContext ctx) {
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
        if (!type.equiv(Type.BOOLEAN)) {
            reportError(ctx, "Invalid condition type of if statement [%s]", ctx.expression().getText());
        }
        ctx.statement().forEach(this::visit);
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
        System.out.println("********Expression Starts********");
        TypeDescriptor lType = visit(ctx.simpleExpression());
        TypeDescriptor rType = null;

        if (ctx.e2 != null) {
            rType = visit(ctx.e2);
            String operator = ctx.relationalOperator.getText();
            System.out.println("ctx.getText() = " + ctx.getText());
            System.out.println("lType = " + lType);
            System.out.println("rType = " + rType);

            // suppress errors
            if (rType.equiv(ErrorType.INVALID_EXPRESSION)) {
                return rType;
            }

            // check whether operands are simple types
            if (!(isSimpleType(lType))) {
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
                if (lType instanceof Subrange) {
                    Class<? extends TypeDescriptor> hostType = ((Subrange) lType).getHostType();
                    if (hostType == rType.getClass()) return new Boolean();
                }
                if (rType instanceof Subrange) {
                    Class<? extends TypeDescriptor> hostType = ((Subrange) rType).getHostType();
                    if (hostType == lType.getClass()) return new Boolean();
                }
                reportError(ctx, "Expression [" + ctx.getText() + "] types are incompatible! lType: " +
                        lType + " rType: " + rType);
                return ErrorType.INVALID_EXPRESSION;
            }

        }
        // if looping statement, return bool otherwise return type of simpleExpression()
        //return rType != null ? Type.BOOLEAN : lType;
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
                if (lType instanceof Subrange) {
                    TypeDescriptor lowerBound = ((Subrange) lType).getLowerBound();
                    if (!(lowerBound instanceof IntegerBaseType) && !(lowerBound instanceof FloatBaseType)) {
                        reportError(ctx, "Additive Operator " + operator +
                                " cannot be applied on the left operand: " + lType);
                        return ErrorType.INVALID_TYPE;
                    }
                }else{
                    reportError(ctx, "Additive Operator " + operator +
                        " cannot be applied on the left operand: " + lType);
                    return ErrorType.INVALID_TYPE;
                }
            }
            // if right operand is not int nor real
            if (!(rType instanceof IntegerBaseType) && !(rType instanceof FloatBaseType)) {
                reportError(ctx, "Additive Operator " + operator +
                        " cannot be applied on the right operand: " + rType);
                return ErrorType.INVALID_TYPE;
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

                if (!(lType instanceof Subrange) && !(rType instanceof Subrange)) {
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

            // other multiplicative operators(arithmetic)
            // if left operand is not int nor real
            if (!lType.equiv(Type.INTEGER) && !lType.equiv(Type.REAL)) {
                reportError(ctx, "Multiplicative Operator [%s] cannot be applied on the " +
                        "left operand [%s] of expression [%s]: ", operator, ctx.signedFactor().getText(), ctx.getText());
                return ErrorType.INVALID_TYPE;
            }
            // if right operand is not int nor real
            if (!rType.equiv(Type.INTEGER) && !rType.equiv(Type.REAL)) {
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
                    if (!lType.equiv(Type.INTEGER) || !rType.equiv(Type.INTEGER)) {
                        reportError(ctx, "The operands of integer division must be Integer: " +
                                "with lType: " + lType +
                                " with rType: " + rType);
                    }
                    return IntegerBaseType.copy(defaultIntegerType);
                //return Type.INTEGER;
                // real division, operands could be int/real
                case "/":
                    //return Type.REAL;
                    return new Primitive("real");
                case "mod":
                case "MOD":
                    // modulus reminder division, operands must be integer
                    if (!lType.equiv(Type.INTEGER) || !rType.equiv(Type.INTEGER)) {
                        reportError(ctx, "The operands of modulus must be Integer: " +
                                "with lType: " + lType +
                                " with rType: " + rType);
                    }
                    //return Type.INTEGER;
                    return IntegerBaseType.copy(defaultIntegerType);
                default:
                    // other multiplicative operators: *
                    // return specific type
                    //if (lType.equiv(Type.REAL) || rType.equiv(Type.REAL)) return Type.REAL;
                    if (lType.equiv(Type.REAL) || rType.equiv(Type.REAL)) return new Primitive("real");
                    else return IntegerBaseType.copy(defaultIntegerType);
                    //else return Type.INTEGER;

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
        return new Primitive("bool");
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
        TypeDescriptor number = visit(ctx.factor());
        System.out.println("visitSignedFactor = " + ctx.getText());
        System.out.println("ctx.factor().getClass() = " + ctx.factor().getClass());

        System.out.println("number type = " + number);
        // involves monadic arithmetic expression, check the operand number
        // i.e. signedFactor
        if (ctx.monadicOperator != null) {
            String monadicOp = ctx.monadicOperator.getText();

            if (!number.equiv(Type.INTEGER) && !number.equiv(Type.REAL)) {
                //reportError(ctx, "Monadic arithmetic operator " + monadicOp +
                //        " cannot be applied on the operand: " + number);
                //TODO suppress errors
                return ErrorType.INVALID_MONADIC_OPERATION;
            } else if (number.equiv(Type.INTEGER)) {
                System.out.println("ctx.factor().getText() = " + ctx.factor().getText());
                System.out.println("signed number = " + number);
                Long numberValue = ((IntegerBaseType) number).getValue();
                RuleContext parentContext = ctx.parent.parent.parent.parent;

                // update numberValue accords to the monadic operator
                switch (monadicOp) {
                    case "-":
                        System.out.println("numberValue -> = " + numberValue);
                        numberValue = -numberValue;
                        System.out.println("-numberValue -> = " + numberValue);
                        break;
                    // if is the last constant node, gathering all the errors if any
                    //if (!(parentContext instanceof PascalParser.FactorExprContext) &&
                    //        !hasNoUnderflow(numberValue)) {
                    //    return ErrorType.INTEGER_UNDERFLOW;
                    //} else {
                    //    // update value and return this node to be processed in the upper node
                    //    ((IntegerBaseType) number).setValue(numberValue);
                    //    System.out.println("pass to upper node = " + ((IntegerBaseType) number).getValue());
                    //    System.out.println("number updated = " + number);
                    //    return number;
                    //}
                    //if (!hasNoUnderflow(numberValue)) {
                    //    return ErrorType.INTEGER_UNDERFLOW;
                    //}
                    //  overflow checking
                    case "+":
                        //if (!hasNoOverflow(numberValue)) {
                        //    return ErrorType.INTEGER_OVERFLOW;
                        //}
                        break;


                }

                System.out.println("current numberValue = " + numberValue);
                System.out.println("parentContextTT = " + parentContext.getClass());
                // if is the last constant node, gathering all the errors if any
                if (!(parentContext instanceof PascalParser.FactorExprContext)) {
                    if (!hasNoOverflow(numberValue)) return ErrorType.INTEGER_OVERFLOW;
                    if (!hasNoUnderflow(numberValue)) return ErrorType.INTEGER_UNDERFLOW;
                    // no errors directly return number
                    return number;
                } else {
                    // update value(copy) and return this node to be processed in the upper node
                    // ((IntegerBaseType) number).setValue(numberValue);
                    // FIXME:this might cause potential problems
                    //
                    System.out.println("pass to upper node = " + ((IntegerBaseType) number).getValue());
                    System.out.println("number updated = " + number);
                    //IntegerBaseType updatedNumber = new IntegerBaseType();
                    //updatedNumber.setValue(numberValue);
                    IntegerBaseType copy = IntegerBaseType.copy((IntegerBaseType) number);
                    copy.setValue(numberValue);
                    return copy;
                }
            }
        } else {
            // unsigned factor
            if (number.equiv(Type.INTEGER)) {
                // integer overflow checking
                Long numberValue;
                // if right operand is var
                if (ctx.factor() instanceof PascalParser.FactorVarContext) {
                    numberValue = ((IntegerBaseType) number).getValue();
                } else numberValue = Long.valueOf(ctx.getText());
                System.out.println("numberValue = " + numberValue);
                if (!hasNoOverflowOrUnderflow(numberValue, true, false)) {
                    // error reported in assignment node
                    return ErrorType.INTEGER_OVERFLOW;
                }
            }
        }
        return number;
    }

    /**
     * Children of Term-signedFactor
     * Represents a variable(identifier)
     *
     * @param ctx
     * @return
     */
    @Override
    public TypeDescriptor visitFactorVar(PascalParser.FactorVarContext ctx) {
        // suppress errors
        return retrieve(ctx.getText(), false, ctx);
    }

    @Override
    public TypeDescriptor visitFactorExpr(PascalParser.FactorExprContext ctx) {
        return visit(ctx.expression());
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
                return new Primitive("real");
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
