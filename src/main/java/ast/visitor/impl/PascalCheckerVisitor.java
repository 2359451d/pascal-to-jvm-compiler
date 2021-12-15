package ast.visitor.impl;

import ast.visitor.PascalBaseVisitor;
import ast.visitor.PascalParser;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.Interval;
import runtime.RunTimeLibFactory;
import type.*;
import utils.ErrorMessage;
import type.SymbolTable;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class PascalCheckerVisitor extends PascalBaseVisitor<Type> {


    // Contextual errors

    private int errorCount = 0;

    private CommonTokenStream tokens;

    private SymbolTable<Type> symbolTable = new SymbolTable<>();

    // Constructor

    public PascalCheckerVisitor(CommonTokenStream toks) {
        tokens = toks;
    }

    private void reportError(String message,
                             ParserRuleContext ctx) {
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

    private void predefine() {
        // Add predefined procedures to the type table.
        //BuiltInUtils.fillTable(symbolTable);
        RunTimeLibFactory.fillTable(symbolTable);
    }

    private void define(String id, Type type,
                        ParserRuleContext decl) {
        // Add id with its type to the type table, checking
        // that id is not already declared in the same scope.
        boolean ok = symbolTable.put(id, type);
        if (!ok)
            reportError(id + " is redeclared", decl);
    }

    private Type retrieve(String id, ParserRuleContext occ) {
        // Retrieve id's type from the type table.
        Type type = symbolTable.get(id);
        if (type == null) {
            reportError(id + " is undeclared", occ);
            return Type.UNDEFINED_TYPE;
        } else
            return type;
    }

    @Override
    public Type visitProgram(PascalParser.ProgramContext ctx) {
        predefine();
        return super.visitProgram(ctx);
    }

    @Override
    public Type visitBlock(PascalParser.BlockContext ctx) {
        System.out.println("Block Starts*************************");
        symbolTable.displayCurrentScope();
        visitChildren(ctx);
        System.out.println("Block Ends*************************");
        return null;
    }

    @Override
    public Type visitIdentifier(PascalParser.IdentifierContext ctx) {
        //skip if the parent node is ProgramHeading, do not process the ProgramHeading ID
        ParserRuleContext parent = ctx.getParent();
        if (parent instanceof PascalParser.ProgramHeadingContext) return null;
        return retrieve(ctx.getText(), ctx);
    }

    @Override
    public Type visitIdentifierList(PascalParser.IdentifierListContext ctx) {
        return super.visitIdentifierList(ctx);
    }

    @Override
    public Type visitVariableDeclaration(PascalParser.VariableDeclarationContext ctx) {
        System.out.println("Variable Decl Starts*************");
        List<PascalParser.IdentifierContext> identifierContextList = ctx.identifierList().identifier();
        for (PascalParser.IdentifierContext identifierContext : identifierContextList) {
            String id = identifierContext.IDENT().getText();
            Type type = visit(ctx.type_());
            define(id, type, ctx);
        }
        symbolTable.displayCurrentScope();
        System.out.println("Variable Decl ends\n*************");
        return null;
    }

    @Override
    public Type visitPrimitiveType(PascalParser.PrimitiveTypeContext ctx) {
        switch (ctx.primitiveType.getType()) {
            case PascalParser.INTEGER:
                return Type.INTEGER;
            case PascalParser.STRING:
                return Type.STR;
            case PascalParser.CHAR:
                return Type.CHARACTER;
            case PascalParser.BOOLEAN:
                return Type.BOOLEAN;
            case PascalParser.REAL:
                return Type.REAL;
        }
        return super.visitPrimitiveType(ctx);
    }

    @Override
    public Type visitFileType(PascalParser.FileTypeContext ctx) {
        Type type = visit(ctx.type_());

        //return super.visitFileType(ctx);
        return new File(type);
    }

    private void checkAcceptableType(Set<String> acceptableTypes, Signature signature,
                                     ParserRuleContext ctx) {
        System.out.println("=======checkAcceptableTypes starts=========");
        System.out.println("acceptableTypes = " + acceptableTypes);
        System.out.println("actualSignature given = " + signature);
        List<String> paramList = signature.getParamList();
        for (String param : paramList) {
            //System.out.println("current param = " + param);
            if (!acceptableTypes.contains(param)) {
                reportError("type: " + param
                        + " is not supported", ctx);
            }
        }
        System.out.println("=======checkAcceptableTypes ends=========");
    }

    private void checkAcceptableSignature(SignatureSet signatureSet, SignatureSet signature,
                                          ParserRuleContext ctx) {
        System.out.println("=======checkAcceptableSignature starts=========");
        System.out.println("actualSignature given = " + signature);
        Set<String> typeOrderToBeChecked = signatureSet.getTypeOrderToBeChecked();
        System.out.println("typeOrderToBeChecked = " + typeOrderToBeChecked);
        Set<Signature> acceptableSignatures = signatureSet.getAcceptableSignatures();
        System.out.println("acceptableSignatures = " + acceptableSignatures);

        ArrayList<Signature> actualSignatures = new ArrayList<>(signature.getAcceptableSignatures());
        Signature actualSignature = actualSignatures.get(0);
        List<String> paramList = actualSignature.getParamList();
        System.out.println("paramList = " + paramList);

        // trim the signature (as there are variable number of params)
        List<String> trimmedParamList = paramList.stream().distinct().collect(Collectors.toList());
        Signature trimmedSignature = new Signature(trimmedParamList);
        //System.out.println("trimmedParamList = " + trimmedParamList);
        System.out.println("trimmedSignature = " + trimmedSignature);

        if (!acceptableSignatures.contains(trimmedSignature)) {
            Set<String> acceptableTypes = signatureSet.getAcceptableTypes();
            System.out.println("acceptableTypes = " + acceptableTypes);
            Set<String> coveredTypes = signature.getAcceptableTypes();
            System.out.println("acceptableTypes1 = " + coveredTypes);
            boolean orderCheck = false;
            for (String s : coveredTypes) {
                if (acceptableTypes.contains(s)) {
                    orderCheck = true;
                    break;
                }
            }

            if (orderCheck) {
                boolean notDeclared = true;
                for (int i = 0; i < trimmedParamList.size(); i++) {
                    String actualParam = trimmedParamList.get(i);
                    for (Signature acceptableSignature : acceptableSignatures) {
                        List<String> acceptableSignatureParamList = acceptableSignature.getParamList();
                        if (typeOrderToBeChecked.contains(actualParam) && !acceptableSignatureParamList.get(i).equals(actualParam)) {
                            reportError("signature " + signature
                                    + " is not supported", ctx);
                        }
                        //} else if (!typeOrderToBeChecked.contains(actualParam)){
                        //    if (trimmedParamList.size() <= acceptableSignatureParamList.size()) {
                        //
                        //    }
                        //}
                    }
                }
            } else {
                for (String coveredType : signature.getAcceptableTypes()) {
                    if (!acceptableTypes.contains(coveredType))
                        reportError("signature " + signature + " is not supported", ctx);
                }
            }
        }


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

        System.out.println("=======checkAcceptableSignature ends=========");

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
    public Type visitNoLabelParam(PascalParser.NoLabelParamContext ctx) {
        Type type = visit(ctx.parameterGroup().typeIdentifier());
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
    public Type visitVarLabelParam(PascalParser.VarLabelParamContext ctx) {
        Type type = visit(ctx.parameterGroup().typeIdentifier());
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
    public Type visitProcedureDeclaration(PascalParser.ProcedureDeclarationContext ctx) {
        String id = ctx.identifier().getText().toLowerCase();
        ArrayList<Type> params = new ArrayList<>();

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
            Map<String, Type> allParams = symbolTable.getAllVarInCurrentScope();
            allParams.forEach((k, v) -> params.add(v));
        }
        visit(ctx.block()); // scope & type checking in current proc scope
        symbolTable.exitLocalScope(); // back to last scope
        define(id, new Proc(params), ctx);
        System.out.println("PROC DECL ENDS*************************");
        symbolTable.displayCurrentScope();
        return null;
    }

    /**
     * Check single formal parameter and actual parameter
     * Whether these are the same or not
     * @param formalParam
     * @param actualParam
     * @return
     */
    private boolean checkFormalAndActual(FormalParam formalParam, ActualParam actualParam) {
        if (formalParam.getLabel() == null) return formalParam.getType().equiv(actualParam.getType());
        if (!(formalParam.getLabel().equals(actualParam.getLabel()))) return false;
        return formalParam.equiv(actualParam.getType());
    }

    /**
     * @param signature
     * @param actualParameters
     * @return BooleanMessage - contains {boolean flag, List<String> messageSequence}
     */
    private ErrorMessage checkSignature(Proc signature, List<PascalParser.ActualParameterContext> actualParameters) {
        ErrorMessage errorMessage = new ErrorMessage();
        // default - set the flag to false, i.e. exist errors to report
        //errorMessage.setFlag(false);
        List<Type> formalParameters = signature.getFormalParams();
        // if actual parameters length cannot match the formal parameters, directly return the error message
        if (actualParameters.size() < formalParameters.size()) {
            errorMessage.setMessageSequence(new StringBuilder(String.format(
                    "The number of actual parameters cannot match the signature! " +
                            "Actual: %d, Expected: %d",
                    actualParameters.size(),
                    formalParameters.size())));
        } else {
            AtomicInteger count = new AtomicInteger(0);
            //List<String> messageSequence = new ArrayList<>();
            StringBuilder stringBuilder = new StringBuilder();
            formalParameters.forEach(
                    formal->{
                        Type actual = visit(actualParameters.get(count.getAndIncrement()));
                        ActualParam _actual = (ActualParam) actual;
                        FormalParam _formal = (FormalParam) formal;
                        if (!checkFormalAndActual(_formal, _actual)) {
                            if (stringBuilder.length() == 0) {
                                stringBuilder.append("Actual parameter cannot match the formal parameter!\n");
                            }
                            stringBuilder.append(String.format(
                                    "- Pos[%d]\n" +
                                            " Actual: %s, Expected: %s",
                                    count.get(), _actual, _formal
                            ));
                        }
                    }
            );
            errorMessage.setMessageSequence(stringBuilder);
        }
        return errorMessage;
    }

    @Override
    public Type visitProcedureStatement(PascalParser.ProcedureStatementContext ctx) {
        System.out.println("*******************PROC CALL");
        String id = ctx.identifier().getText().toLowerCase();
        System.out.println("id = " + id);
        Type signature = retrieve(id, ctx);
        symbolTable.displayCurrentScope();
        //System.out.println("retrieve = " + retrieve);

        if (!(signature instanceof Proc)) {
            reportError(id + " is not a procedure", ctx);
            return Type.INVALID_TYPE;
        } else {

            System.out.println("Signature = " + signature);
            Proc _signature = (Proc) signature;
            //check signatures
            List<PascalParser.ActualParameterContext> actualParameterContextList = ctx.parameterList().actualParameter();
            ErrorMessage errorMessage = checkSignature(_signature, actualParameterContextList);
            System.out.println("booleanMessage = " + errorMessage);
            if (errorMessage.hasErrors()) {
                reportError(errorMessage.getMessageSequence(), ctx);
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
    public Type visitParameterList(PascalParser.ParameterListContext ctx) {
        return visitChildren(ctx);

        //ArrayList<Type> types = new ArrayList<>();
        //List<PascalParser.ActualParameterContext> actualParameters = ctx.actualParameter();
        //for (PascalParser.ActualParameterContext actualParameter : actualParameters) {
        //    Type type = visit(actualParameter);
        //    types.add(type);
        //}
        //return new Sequence(types);

        //List<String> paramList = new ArrayList<>();
        //Set<String> typeSets = new HashSet<>();
        //List<PascalParser.ActualParameterContext> actualParameters = ctx.actualParameter();
        //for (PascalParser.ActualParameterContext actualParameter : actualParameters) {
        //    Type type = visit(actualParameter);
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
    public Type visitActualParameter(PascalParser.ActualParameterContext ctx) {
        Type actualParamType = visit(ctx.expression());
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
    public Type visitWhileStatement(PascalParser.WhileStatementContext ctx) {
        Type conditionType = visit(ctx.expression());
        if (!conditionType.equiv(Type.BOOLEAN)) {
            reportError(ctx, "Condition 【%s: %s】 of while statement must be boolean",
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
    public Type visitRepeatStatement(PascalParser.RepeatStatementContext ctx) {
        Type exType = visit(ctx.expression());
        if (!exType.equiv(Type.BOOLEAN)) {
            reportError(ctx, "Expression 【%s: %s】 of" +
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
    public Type visitForStatement(PascalParser.ForStatementContext ctx) {
        String forHeader = concatenateChildrenText(ctx, 0, 4);
        Type controlType = retrieve(ctx.identifier().getText(), ctx);
        if (!isOrdinalType(controlType)) {
            // form the text of FOR header
            reportError(ctx, "Control variable 【%s: %s】 of For " +
                            "statement 【%s】 must be Ordinal",
                    ctx.identifier().getText(), controlType.toString(), forHeader);
        } else {
            // check initialValue
            Type initType = visit(ctx.forList().initialValue());
            Type finalType = visit(ctx.forList().finalValue());
            if (!initType.equiv(controlType)) {
                reportError(ctx, "Initial Expression 【%s: %s】 " +
                                "of For Statement 【%s】\n " +
                                "must be the same ordinal type as control variable 【%s: %s】",
                        ctx.forList().initialValue().getText(),
                        initType.toString(), forHeader,
                        ctx.identifier().getText(), controlType.toString());
            } else if (!finalType.equiv(controlType)) {
                reportError(ctx, "Final Expression 【%s: %s】 " +
                                "of For Statement 【%s】\n " +
                                "must be the same ordinal type as control variable 【%s: %s】",
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
    public Type visitAssignmentStatement(PascalParser.AssignmentStatementContext ctx) {
        Type rightType = visit(ctx.expression());
        String id = ctx.variable().getText();
        Type leftType = retrieve(id, ctx);
        if (!leftType.equiv(rightType)) {
            // exception case when left is real, it is acceptable though right is int
            if (leftType.equiv(Type.REAL) && rightType.equiv(Type.INTEGER)) return null;
            // exception case when left is character, and right is string literal(length must be 1)
            if (leftType.equiv(Type.CHARACTER) && rightType.equiv(Type.STR)) {
                //System.out.println( + "sssssssssssss");
                String content = ctx.expression().getText().replace("'", "");
                if (content.length() == 1) return null;
            }
            //reportError("Assignment 【】 types are incompatible! Expected(lType): "
            //        + leftType + " Actual(rType): " + rightType, ctx);
            reportError(ctx, "Assignment 【%s】 types are incompatible! Expected(lType): %s Actual(rType): %s", ctx.getText(), leftType.toString(), rightType.toString());
        }
        return null;
    }

    /**
     * ifStatement
     * : IF expression THEN statement (: ELSE statement)?
     *
     * @param ctx
     * @return
     */
    @Override
    public Type visitIfStatement(PascalParser.IfStatementContext ctx) {
        Type type = visit(ctx.expression());
        if (!type.equiv(Type.BOOLEAN)) {
            reportError(ctx, "Invalid condition type of if statement 【%s】", ctx.expression().getText());
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
    private boolean isSimpleType(Type type) {
        return (type.equiv(Type.REAL) || isOrdinalType(type));
    }

    /**
     * TODO: Enumerated types & Subrange NOT IMPLEMENTED
     * ordinal-type = new-ordinal-type(enumerated,subrange) | ordinal-type(int, boolean, char)
     *
     * @param type
     * @return
     */
    private boolean isOrdinalType(Type type) {
        return (type.equiv(Type.INTEGER) || type.equiv(Type.CHARACTER)
                || type.equiv(Type.BOOLEAN));
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
    public Type visitExpression(PascalParser.ExpressionContext ctx) {
        Type lType = visit(ctx.simpleExpression());
        Type rType = null;
        if (ctx.expression() != null) {
            rType = visit(ctx.expression());
            String operator = ctx.relationalOperator.getText();

            // check whether operands are simple types
            if (!(isSimpleType(lType))) {
                reportError(String.format(
                        "Relational operator 【%s】 cannot" +
                                " be applied on left operand 【%s】 of expression 【%s】: %s",
                        operator, ctx.simpleExpression().getText(), ctx.getText(), lType), ctx);
                return Type.INVALID_TYPE;
            }
            if (!(isSimpleType(rType))) {
                reportError(String.format("Relational operator 【%s】 cannot" +
                                " be applied on right operand 【%s】 of expression 【%s】: %s",
                        operator, ctx.expression().getText(), ctx.getText(), rType), ctx);
                return Type.INVALID_TYPE;
            }

            // relational expression
            if (!lType.equiv(rType)) {
                if (lType.equiv(Type.REAL) || rType.equiv(Type.REAL)) {
                    if (lType.equiv(Type.INTEGER) || rType.equiv(Type.INTEGER)) return Type.BOOLEAN;
                }
                if (lType.equiv(Type.CHARACTER) || rType.equiv(Type.CHARACTER)) {
                    if (lType.equiv(Type.STR) || rType.equiv(Type.STR)) return Type.BOOLEAN;
                }
                reportError("Expression 【" + ctx.getText() + "】 types are incompatible! Type: " +
                        lType + " rType: " + rType, ctx);
                return Type.INVALID_TYPE;
            }

        }
        // if looping statement, return bool otherwise return type of simpleExpression()
        return rType != null ? Type.BOOLEAN : lType;
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
    public Type visitSimpleExpression(PascalParser.SimpleExpressionContext ctx) {
        Type lType = visit(ctx.term());
        Type rType = null;

        // if it involves 2 operands, need to check the type on both sides
        // then return the specific type
        if (null != ctx.simpleExpression()) {
            rType = visit(ctx.simpleExpression());
            String operator = ctx.additiveOperator.getText();

            // if is Logical operator OR
            if (operator.equalsIgnoreCase("or")) {
                return checkLogicalOpOperand(ctx, lType, ctx.term().getText(), rType, ctx.simpleExpression().getText(), operator);
            }

            // if left operand is not int nor real
            if (!lType.equiv(Type.INTEGER) && !lType.equiv(Type.REAL)) {
                reportError("Additive Operator " + operator +
                        " cannot be applied on the left operand: " + lType, ctx);
                return Type.INVALID_TYPE;
            }
            // if right operand is not int nor real
            if (!rType.equiv(Type.INTEGER) && !rType.equiv(Type.REAL)) {
                reportError("Additive Operator " + operator +
                        " cannot be applied on the right operand: " + rType, ctx);
                return Type.INVALID_TYPE;
            }
            if (lType.equiv(Type.REAL) || rType.equiv(Type.REAL)) return Type.REAL;
            else return Type.INTEGER;
        }
        // only 1 term
        return lType;
    }

    /**
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
    public Type visitTerm(PascalParser.TermContext ctx) {
        Type lType = visit(ctx.signedFactor());
        Type rType = null;
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
                reportError(ctx, "Multiplicative Operator 【%s】 cannot be applied on the " +
                        "left operand 【%s】 of expression 【%s】: ", operator, ctx.signedFactor().getText(), ctx.getText());
                return Type.INVALID_TYPE;
            }
            // if right operand is not int nor real
            if (!rType.equiv(Type.INTEGER) && !rType.equiv(Type.REAL)) {
                reportError(ctx, "Multiplicative Operator 【%s】 cannot be applied on the " +
                        "right operand 【%s】 of expression 【%s】: ", operator, ctx.term().getText(), ctx.getText());
                return Type.INVALID_TYPE;
            }
            // integer division, operands must be integer
            if (operator.equals("div") || operator.equals("DIV")) {
                System.out.println("lType.equiv(Type.INTEGER) = " + lType.equiv(Type.INTEGER));
                System.out.println("rType.equiv(Type.INTEGER) = " + rType.equiv(Type.INTEGER));
                if (!lType.equiv(Type.INTEGER) || !rType.equiv(Type.INTEGER)) {
                    reportError("The operands of integer division must be Integer: " +
                            "with lType: " + lType +
                            " with rType: " + rType, ctx);
                }
                return Type.INTEGER;
                // real division, operands could be int/real
            } else if (operator.equals("/")) {
                return Type.REAL;
            } else if (operator.equals("mod") || operator.equals("MOD")) {
                // modulus reminder division, operands must be integer
                if (!lType.equiv(Type.INTEGER) || !rType.equiv(Type.INTEGER)) {
                    reportError("The operands of modulus must be Integer: " +
                            "with lType: " + lType +
                            " with rType: " + rType, ctx);
                }
                return Type.INTEGER;
            } else {
                // other multiplicative operators: *
                // return specific type
                if (lType.equiv(Type.REAL) || rType.equiv(Type.REAL)) return Type.REAL;
                else return Type.INTEGER;
            }
        }
        return lType;
    }

    private Type checkLogicalOpOperand(ParserRuleContext ctx, Type lType, String lOp, Type rType, String rOp, String operator) {
        if (!lType.equiv(Type.BOOLEAN)) {
            reportError(String.format("Logical operator 【%s】 cannot" +
                            " be applied on left operand 【%s】 of expression 【%s】: %s",
                    operator, lOp, ctx.getText(), lType), ctx);
            return Type.INVALID_TYPE;
        }
        if (!rType.equiv(Type.BOOLEAN)) {
            reportError(String.format("Logical operator 【%s】 cannot" +
                            " be applied on right operand 【%s】 of expression 【%s】: %s",
                    operator, rOp, ctx.getText(), rType), ctx);
            return Type.INVALID_TYPE;
        }
        return Type.BOOLEAN;
    }

    /**
     * signedFactor
     * : (PLUS | MINUS)? factor
     * ;
     * Check Monadic arithmetic operations:
     * -x
     * +x
     *
     * @param ctx
     * @return
     */
    @Override
    public Type visitSignedFactor(PascalParser.SignedFactorContext ctx) {
        Type type = visit(ctx.factor());
        // involves monadic arithmetic expression, check the operand type
        if (ctx.monadicOperator != null) {
            String monadicOp = ctx.monadicOperator.getText();
            if (!type.equiv(Type.INTEGER) && !type.equiv(Type.REAL)) {
                reportError("Monadic arithmetic operator " + monadicOp +
                        " cannot be applied on the operand: " + type, ctx);
                return Type.INVALID_TYPE;
            }
        }
        return type;
    }

    /**
     * Children of Term-signedFactor
     * Represents a variable(identifier)
     *
     * @param ctx
     * @return
     */
    @Override
    public Type visitFactorVar(PascalParser.FactorVarContext ctx) {
        return retrieve(ctx.getText(), ctx);
    }

    @Override
    public Type visitFactorExpr(PascalParser.FactorExprContext ctx) {
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
    public Type visitNotFactor(PascalParser.NotFactorContext ctx) {
        Type type = visit(ctx.factor());
        if (!type.equiv(Type.BOOLEAN)) {
            reportError(ctx, "Relational operator 【NOT】 cannot be applied on 【%s】 operand 【%s】: %s", ctx.getText(), ctx.factor().getText(), type.toString());
            return Type.INVALID_TYPE;
        }
        return type;
    }

    @Override
    public Type visitUnsignedInteger(PascalParser.UnsignedIntegerContext ctx) {
        return Type.INTEGER;
    }

    @Override
    public Type visitUnsignedReal(PascalParser.UnsignedRealContext ctx) {
        return Type.REAL;
    }

    @Override
    public Type visitString(PascalParser.StringContext ctx) {
        return Type.STR;
    }

    @Override
    public Type visitBool_(PascalParser.Bool_Context ctx) {
        return Type.BOOLEAN;
    }
}
