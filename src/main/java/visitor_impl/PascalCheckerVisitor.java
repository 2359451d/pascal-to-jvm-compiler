package visitor_impl;

import ast_visitor.PascalBaseVisitor;
import ast_visitor.PascalParser;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.Interval;
import runtime.RunTimeLibFactory;
import type.*;
import utils.SymbolTable;

import java.util.*;
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
            return Type.ERROR;
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
        symbolTable.enterLocalScope();
        visitChildren(ctx);
        return null;
    }

    @Override
    public Type visitIdentifier(PascalParser.IdentifierContext ctx) {
        return super.visitIdentifier(ctx);
    }

    @Override
    public Type visitIdentifierList(PascalParser.IdentifierListContext ctx) {

        return super.visitIdentifierList(ctx);
    }

    @Override
    public Type visitVariableDeclaration(PascalParser.VariableDeclarationContext ctx) {
        List<PascalParser.IdentifierContext> identifierContextList = ctx.identifierList().identifier();
        for (PascalParser.IdentifierContext identifierContext : identifierContextList) {
            String id = identifierContext.IDENT().getText();
            Type type = visit(ctx.type_());
            System.out.println("type = " + type);
            define(identifierContext.IDENT().getText(), type, ctx);
        }
        symbolTable.displayCurrentScope();
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

    @Override
    public Type visitProcedureStatement(PascalParser.ProcedureStatementContext ctx) {
        String id = ctx.identifier().getText().toLowerCase();
        Type typeProcDecl = retrieve(id, ctx);
        System.out.println("id = " + id);
        //System.out.println("retrieve = " + retrieve);

        if (!(typeProcDecl instanceof SignatureSet)) {
            reportError(id + " is not a procedure", ctx);
            return Type.ERROR;
        } else {
            // type checking
            SignatureSet signatureSet = (SignatureSet) typeProcDecl;
            Set<String> acceptableTypes = signatureSet.getAcceptableTypes();
            //System.out.println("acceptableTypes = " + acceptableTypes);
            //Type expectedParamSeq = type.domain;

            // check actual_params == definition in the symbol table
            SignatureSet actualSignatureSet = (SignatureSet) visit(ctx.parameterList());
            //System.out.println("actualSignature = " + actualSignature);

            // first checking whether the actual params given are supported or not
            //checkAcceptableType(acceptableTypes, actualSignature, ctx);
            // then check whether there are limitations on params order & number or not
            checkAcceptableSignature(signatureSet, actualSignatureSet, ctx);


            //Set<String> set = new HashSet<>();
            //set.add(s.getClass().getName());
            //System.out.println("set = " + set);
            //System.out.println(set.contains(Str.class.getName()));

            //Type actualParamSeq = visit(ctx.parameterList());
            //if (!actualParamSeq.equiv(expectedParamSeq)) {
            //    reportError("type is " + actualParamSeq
            //            + ", should be " + expectedParamSeq, ctx);
            //}
        }
        return super.visitProcedureStatement(ctx);
    }

    @Override
    public Type visitParameterList(PascalParser.ParameterListContext ctx) {
        //ArrayList<Type> types = new ArrayList<>();
        //List<PascalParser.ActualParameterContext> actualParameters = ctx.actualParameter();
        //for (PascalParser.ActualParameterContext actualParameter : actualParameters) {
        //    Type type = visit(actualParameter);
        //    types.add(type);
        //}
        //return new Sequence(types);

        List<String> paramList = new ArrayList<>();
        Set<String> typeSets = new HashSet<>();
        List<PascalParser.ActualParameterContext> actualParameters = ctx.actualParameter();
        for (PascalParser.ActualParameterContext actualParameter : actualParameters) {
            Type type = visit(actualParameter);
            paramList.add(type.toString());
            typeSets.add(type.getClass().getName());
        }
        Signature signature = new Signature(paramList);
        HashSet<Signature> signatures = new HashSet<>();
        signatures.add(signature);
        return new SignatureSet(signatures, typeSets);
    }

    @Override
    public Type visitActualParameter(PascalParser.ActualParameterContext ctx) {
        //ctx.expression().simpleExpression().term().signedFactor();
        //return retrieve(ctx.getText(), ctx);
        return visitChildren(ctx);
    }

    @Override
    public Type visitWhileStatement(PascalParser.WhileStatementContext ctx) {
        Type conditionType = visit(ctx.expression());
        if (!conditionType.equiv(Type.BOOLEAN)) {
            reportError("Condition type should be boolean", ctx);
        }
        return super.visitWhileStatement(ctx);
    }

    @Override
    public Type visitAssignmentStatement(PascalParser.AssignmentStatementContext ctx) {
        Type rightType = visit(ctx.expression());
        String id = ctx.variable().getText();
        Type leftType = retrieve(id, ctx);
        if (!leftType.equiv(rightType)) {
            // exception case when left is real, it is acceptable though right is int
            if (leftType.equiv(Type.REAL) && rightType.equiv(Type.INTEGER)) return null;
            reportError("assignment types are incompatible! Expected(lType): "
                    + leftType + " Actual(rType): " + rightType, ctx);
        }
        return null;
    }


    @Override
    public Type visitExpression(PascalParser.ExpressionContext ctx) {
        Type lType = visit(ctx.simpleExpression());
        Type rType = null;
        if (ctx.expression() != null) {
            rType = visit(ctx.expression());
            // looping statement
            if (!lType.equiv(rType)) {
                reportError("Expression types are incompatible! Expected: " +
                        lType + " Actual: " +rType, ctx);
            }
        }
        // if looping statement, return bool otherwise return type of simpleExpression()
        return rType!=null ? Type.BOOLEAN : lType;
    }

    /**
     * SimpleExpression
     * 1. single var - return visit children
     * 2. var1 operator var2 - check on two sides
     *      - operator could be
     *      - additive operator: + -
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
            String operator = ctx.additiveoperator().getText();
            // if left operand is not int nor real
            if (!lType.equiv(Type.INTEGER) && !lType.equiv(Type.REAL)) {
                reportError("Additive Operator " + operator +
                        " cannot be applied on the left operand: " + lType, ctx);
            }
            // if right operand is not int nor real
            if (!rType.equiv(Type.INTEGER) && !rType.equiv(Type.REAL)) {
                reportError("Additive Operator " + operator +
                        " cannot be applied on the right operand: " + rType, ctx);
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
     *      - operator could be:
     *      - *
     *      - /
     *      - div
     *      - mod
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
        if (null != ctx.multiplicativeoperator()) {
            String operator = ctx.multiplicativeoperator().getText();
            // if left operand is not int nor real
            if (!lType.equiv(Type.INTEGER) && !lType.equiv(Type.REAL)) {
                reportError("Multiplicative Operator " + operator +
                        " cannot be applied on the left operand: " + lType, ctx);
                return null;
            }
            // if right operand is not int nor real
            if (!rType.equiv(Type.INTEGER) && !rType.equiv(Type.REAL)) {
                reportError("Multiplicative Operator " + operator +
                        " cannot be applied on the right operand: " + rType, ctx);
                return null;
            }
            // integer division, operands must be integer
            if (operator.equals("div") || operator.equals("DIV")) {
                System.out.println("lType.equiv(Type.INTEGER) = " + lType.equiv(Type.INTEGER));
                System.out.println("rType.equiv(Type.INTEGER) = " + rType.equiv(Type.INTEGER));
                if (!lType.equiv(Type.INTEGER) || !rType.equiv(Type.INTEGER)) {
                    reportError("The operands of integer division must be Integer: "+
                            "with lType: "+lType +
                            " with rType: "+rType, ctx);
                }
                return Type.INTEGER;
            // real division, operands could be int/real
            } else if (operator.equals("/")) {
                return Type.REAL;
            } else if (operator.equals("mod") || operator.equals("MOD")) {
                // modulus reminder division, operands must be integer
                if (!lType.equiv(Type.INTEGER) || !rType.equiv(Type.INTEGER)) {
                    reportError("The operands of modulus must be Integer: "+
                            "with lType: "+lType +
                            " with rType: "+rType, ctx);
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

    /**
     * Children of Term-signedFactor
     * Represents a variable(identifier)
     * @param ctx
     * @return
     */
    @Override
    public Type visitFactorVar(PascalParser.FactorVarContext ctx) {
        return retrieve(ctx.getText(), ctx);
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
