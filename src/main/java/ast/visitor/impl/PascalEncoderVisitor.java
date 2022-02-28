package ast.visitor.impl;

import ast.visitor.PascalBaseVisitor;
import ast.visitor.PascalParser;
import driver.PascalCompilerDriverBuilder;
import instruction.*;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.xpath.XPath;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.objectweb.asm.*;
import tableUtils.SymbolTable;
import tableUtils.*;
import type.BaseType;
import type.StringLiteral;
import type.TypeDescriptor;
import type.error.ErrorType;
import type.nestedType.param.FormalParam;
import type.primitive.Boolean;
import type.primitive.Character;
import type.primitive.Primitive;
import type.primitive.floating.DefaultFloatType;
import type.primitive.floating.FloatBaseType;
import type.primitive.floating.Real;
import type.primitive.integer.DefaultIntegerType;
import type.primitive.integer.Integer32;
import type.primitive.integer.IntegerBaseType;
import type.procOrFunc.Function;
import type.procOrFunc.ProcFuncBaseType;
import type.procOrFunc.Procedure;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.StringConcatFactory;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class PascalEncoderVisitor extends PascalBaseVisitor<TypeDescriptor> {

    // automatically compute the frames, max stack and locals
    public static ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
    public static FieldVisitor fieldVisitor;

    /**
     * (General) Execution chains of creating a new method:
     * ! visitCode()
     * ! visitXxxInsn()
     * ! visitMaxs()
     * ! visitEnd()
     */
    public static MethodVisitor methodVisitor; // main method visitor (default)
    public static AnnotationVisitor annotationVisitor;
    public static String className;
    public static Class<?> classOwner;
    private static String baseDir = "";
    private static String outputPath = "";

    private CommonTokenStream tokens;

    /**
     * Tables
     */
    // store local variables declared(static fields) or intermediate calculation variable
    private TableManager<Object, TypeDescriptor> tableManager = TableManager.getInstance(); // quick reference
    private Table<Object, LocalVariableInformation> localVariableTable;
    private Table<Object, TypeDescriptor> symbolTable;
    private Table<Object, TypeDescriptor> typeTable;

    public PascalEncoderVisitor(CommonTokenStream tokens) {
        this.tokens = tokens;
    }

    public PascalEncoderVisitor(String outputPath, CommonTokenStream tokens) {
        this(tokens);
        this.outputPath = outputPath;
        //tableManager.clearAllTables();
        //symbolTable = tableManager.createTableSafely("symbol table",
        //        (Class<? extends Table<Object, TypeDescriptor>>) symbolTable.getClass());
        //typeTable = new TypeTable<>();
        symbolTable = tableManager.selectTable(SymbolTable.context);
        typeTable = tableManager.selectTable(TypeTable.context);
        localVariableTable = new LocalsTable<>();
    }

    public static MethodVisitor getMethodVisitor() {
        return methodVisitor;
    }

    public static byte[] generateByteCode() {
        return classWriter.toByteArray();
    }

    //public static class LocalVariableInformation{
    //    private int slotNum;
    //    private int length;
    //    private boolean isStatic; // determine whether is static field
    //
    //    public LocalVariableInformation(int slotNum, int length, boolean isStatic) {
    //        this.slotNum = slotNum;
    //        this.length = length;
    //        this.isStatic = isStatic;
    //    }
    //
    //    public int getSlotNum() {
    //        return slotNum;
    //    }
    //
    //    public int getLength() {
    //        return length;
    //    }
    //
    //    public boolean isStatic() {
    //        return isStatic;
    //    }
    //}
    //
    //public static class LocalsTable {
    //    /**
    //     * Id - startPos(slot), length
    //     */
    //    public Map<String, LocalVariableInformation> table;
    //
    //    public LocalsTable() {
    //        this.table = new LinkedHashMap<>();
    //    }
    //
    //    public boolean containsId(String id) {
    //        return this.table.containsKey(id);
    //    }
    //
    //    public int getSlot(String id) {
    //        return containsId(id) ? table.get(id).getSlotNum():-999;
    //    }
    //
    //    public boolean isStatic(String id) {
    //        return containsId(id) && table.get(id).isStatic();
    //    }
    //
    //    public void put(String id, int slotNum, int length, boolean isStatic) {
    //        table.put(id, new LocalVariableInformation(slotNum,length,isStatic));
    //    }
    //
    //    public void put(String id, int slotNum, int length) {
    //        put(id, slotNum, length, false);
    //    }
    //
    //    /**b
    //     * Automatically calculate the start position
    //     *
    //     * @param id
    //     * @param length
    //     */
    //    public void put(String id, int length, boolean isStatic) {
    //        int start = length();
    //        this.put(id, start, length, isStatic);
    //    }
    //
    //    public void put(String id, int length) {
    //        put(id,length,false);
    //    }
    //
    //    public int length() {
    //        return table.size();
    //    }
    //
    //    public Map<String, LocalVariableInformation> getTable() {
    //        return table;
    //    }
    //}


    /**
     * Custom ClassLoader to load class file
     */
    private static class MyClassLoader extends ClassLoader {
        public Class<?> defineClass(String name, byte[] b) {
            // expose defineClass method
            return super.defineClass(name, b, 0, b.length);
        }
    }

    private void define(String id, TypeDescriptor type,
                        ParserRuleContext ctx) {
        //select the specific selectedTable with corresponding usage context
        Table<Object, TypeDescriptor> selectedTable = tableManager
                .selectTable(ctx.getClass());
        System.out.println("id = " + id);
        System.out.println("define selectedTable = " + selectedTable);

        // Add id with its type to the selectedTable
        // Checking whether id is already declared in the same scope.
        // IGNORE CASE
        // Skip LocalVariableTable (Class - LocalsTable)
        boolean isDuplicatedInOtherTable = false;
        Map<Class<? extends ParserRuleContext>, Table<Object, TypeDescriptor>> otherTables = tableManager
                .selectAllTablesExcludedToClass(
                        ctx.getClass()
                );
        for (Table<Object, TypeDescriptor> eachTable : otherTables.values()) {
            if (eachTable instanceof LocalsTable) continue;
            if (eachTable.containsKey(id.toLowerCase())) isDuplicatedInOtherTable = true;
        }
        System.out.println("isDuplicatedInOtherTable = " + isDuplicatedInOtherTable);

        boolean putSuccessfully = false;
        if (!isDuplicatedInOtherTable) putSuccessfully = selectedTable.put(id.toLowerCase(), type);
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
            return ErrorType.UNDEFINED_TYPE;
        }
        return type;
    }

    private TypeDescriptor retrieve(String id, ParserRuleContext occ) {
        // default: do not suppress identifier undeclared errors
        return retrieve(id, true, occ);
    }

    public static void run() throws NoSuchMethodException, IllegalAccessException, IOException {
        byte[] bytes = generateByteCode();
        // generate bytecode file
        String outputfile = className + ".class";
        Path path = Paths.get(outputPath, outputfile);
        //FileOutputStream fos = new FileOutputStream(path.toString());
        // TODO set to line above when finished
        FileOutputStream fos = new FileOutputStream(outputfile);
        fos.write(bytes);
        fos.close();

        MyClassLoader cl = new MyClassLoader();
        Class<?> clazz = cl.defineClass(className, bytes);
        // get main mreethod
        Method main = clazz.getMethod("main", String[].class);
        // call main method
        try {
            main.invoke(null, new Object[]{new String[]{}});
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    void addStandardConstructor() {
        MethodVisitor mv =
                classWriter.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitMethodInsn(
                Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        mv.visitInsn(Opcodes.RETURN);
        mv.visitMaxs(1, 1);
        mv.visitEnd();
    }

    @Override
    public TypeDescriptor visitProgram(PascalParser.ProgramContext ctx) {
        addStandardConstructor();
        prototypeImplTrackingMap = new LinkedHashMap<>();

        putLocals("var0", 1, false);


        visit(ctx.programHeading());
        visit(ctx.block());
        classWriter.visitEnd();
        return null;
    }

    @Override
    public TypeDescriptor visitProgramHeading(PascalParser.ProgramHeadingContext ctx) {
        className = StringUtils.capitalize(ctx.identifier().getText());
        classWriter.visit(Opcodes.V11, Opcodes.ACC_PUBLIC, className,
                null, Type.getInternalName(Object.class), null);
        return null;
    }

    @Override
    public TypeDescriptor visitBlock(PascalParser.BlockContext ctx) {
        //if (ctx.parent instanceof PascalParser.ProgramContext) {
        //} else {
        //List<ParseTree> children = ctx.children;
        //PascalParser.ProcedureAndFunctionDeclarationPartContext decl = null;
        //for (ParseTree each : children) {
        //    if (each instanceof PascalParser.ProcedureAndFunctionDeclarationPartContext) {
        //        decl = (PascalParser.ProcedureAndFunctionDeclarationPartContext) each;
        //    } else {
        //        visit(each);
        //    }
        //}
        //if (decl!=null) visit(decl);
        //if (children instanceof )
        visitChildren(ctx);
        //}
        return null;
    }

    private void updateDefaultMethodVisitor(MethodVisitor mv) {
        AbstractHelper.setDefaultMethodVisitor(mv);
    }

    @Override
    public TypeDescriptor visitCompoundStatement(PascalParser.CompoundStatementContext ctx) {
        if (ctx.parent.parent instanceof PascalParser.ProgramContext) {
            System.out.println("+++++++++MAIN METHOD GENERATED++++++++");
            // create a main method
            methodVisitor = classWriter.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main",
                    "([Ljava/lang/String;)V", null, null);

            updateDefaultMethodVisitor(methodVisitor);

            methodVisitor.visitCode();

            visit(ctx.statements());

            // return
            InstructionHelper.returnFromMethod(null);
            //methodVisitor.visitInsn(Opcodes.RETURN);
            // set stack size & locals
            methodVisitor.visitMaxs(2, 2);
            // method end
            methodVisitor.visitEnd();

            //updateDefaultMethodVisitor(null);

            return null;
        }
        return visit(ctx.statements());
    }

    @Override
    public TypeDescriptor visitConditionalStatement(PascalParser.ConditionalStatementContext ctx) {
        visit(ctx.ifStatement());
        // end if
        setLabel(entireEndIf);
        return null;
    }

    //Label elseBlock;
    //Label endIf;
    Label entireEndIf = makeLabel();

    /**
     * ifStatement
     * : IF expression THEN statement (: ELSE statement)?
     * ;
     *
     * @param ctx
     * @return
     */
    @Override
    public TypeDescriptor visitIfStatement(PascalParser.IfStatementContext ctx) {
        visit(ctx.expression());
        //// 1. generate code to evaluate if expression
        //TypeDescriptor ifExpressionType = visit(ctx.expression());
        //// 2. store into temp local var
        //String tempBoolVar = "__bool" + new Random().nextInt(999);
        //putLocals(tempBoolVar, 1);
        //LoadStoreHelper.storePrimitive(ifExpressionType, getVariableSlotNum(tempBoolVar));
        //
        //// 3. generate bytecode of if true - else structure
        //// prepare labels
        //Label elseBlock = makeLabel();
        //Label endIf = makeLabel();
        //// 3.1 load temp local var onto stack
        //LoadStoreHelper.loadIntOrFloatLocal(ifExpressionType, getVariableSlotNum(tempBoolVar));
        //// jump if temp bool var == 0 (means if expression is false)
        //JumpInstructionHelper.jumpInstruction(Opcodes.IFEQ,
        //        elseBlock);
        //// if T
        //visit(ctx.statement(0));
        //// jump to endif
        //JumpInstructionHelper.gotoLabel(entireEndIf);
        //
        //// else block
        //setLabel(elseBlock);
        //if (ctx.statement().size() > 1) {
        //    // check whether is "else if" block
        //    //Collection<ParseTree> compoundStatementMatch = XPath.findAll(ctx.statement(1), "//compoundStatement", PascalCompilerDriverBuilder.parser);
        //    Collection<ParseTree> ifStatementMatch = XPath.findAll(ctx.statement(1), "//ifStatement", PascalCompilerDriverBuilder.parser);
        //    ifStatementMatch.forEach(each->{
        //        if (each instanceof PascalParser.IfStatementContext) {
        //            System.out.println("((PascalParser.IfStatementContext) each).parent.parent.parent.parent.getClass() = " + ((PascalParser.IfStatementContext) each).parent.parent.parent.parent.parent.getClass());
        //        }
        //        System.out.println("if each.getText() = " + each.getText());
        //    });
        //
        //    visit(ctx.statement(1));
        //}


        return null;
    }


    Label repeatBlockStart=null;
    /**
     * repeatStatement
     * : REPEAT statements UNTIL expression
     * ;
     *
     * @param ctx
     * @return
     */
    @Override
    public TypeDescriptor visitRepeatStatement(PascalParser.RepeatStatementContext ctx) {
        repeatBlockStart = makeLabel();
        setLabel(repeatBlockStart);

        visit(ctx.statements());
        visit(ctx.expression());
        return null;
    }

    Label whileExprStart = null;

    @Override
    public TypeDescriptor visitWhileStatement(PascalParser.WhileStatementContext ctx) {
        whileExprStart = makeLabel();
        setLabel(whileExprStart);
        visit(ctx.expression());
        return null;
    }


    /**
     * forStatement
     * : FOR identifier ASSIGN forList DO statement
     * ;
     *
     * @param ctx
     * @return
     */
    @Override
    public TypeDescriptor visitForStatement(PascalParser.ForStatementContext ctx) {
        Label forExprStart = makeLabel();
        Label endFor = makeLabel();
        //
        //// initialised for counter
        String counterId = ctx.identifier().getText().toLowerCase();
        TypeDescriptor hostType = visit(ctx.identifier());
        TypeDescriptor initValueType = visit(ctx.forList().initialValue());
        if (isStaticField(counterId)) {
            InstructionHelper.putStatic(className, counterId, hostType);
        }

        setLabel(forExprStart);
        // push final value operand
        if (isStaticField(counterId)) {
            InstructionHelper.getStatic(className, counterId, hostType);
        }
        TypeDescriptor finalValueType = visit(ctx.forList().finalValue());
        JumpInstructionHelper.jumpInstruction(relationalOpMappingWithInt.get("<="), endFor);

        visit(ctx.statement());

        // for counter increment
        // 1. load var/field
        if (isStaticField(counterId)) {
            InstructionHelper.getStatic(className, counterId, hostType);
        } else {
            //    TODO, local
        }
        // 2. load const 1
        InstructionHelper.loadTrueOrFalse(true);
        // 3. increment (counter+1)
        InstructionHelper.add(hostType);
        // 4. update counter
        if (isStaticField(counterId)) {
            InstructionHelper.putStatic(className, counterId, hostType);
        } else {
        }
        gotoLabel(forExprStart);

        setLabel(endFor);
        return null;
    }

    /**
     * Assist forward referencing mechanism
     */
    private Map<String, Pair<TypeDescriptor, ParserRuleContext>> prototypeImplTrackingMap;

    String resultVar = null;

    /**
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
                TypeDescriptor argumentType = visit(paramSection);
                if (paramSection instanceof PascalParser.NoLabelParamContext) {
                    PascalParser.IdentifierListContext identifierListContext = ((PascalParser.NoLabelParamContext) paramSection).parameterGroup().identifierList();
                    for (PascalParser.IdentifierContext eachId : identifierListContext.identifier()) {
                        String eachIdText = eachId.getText();
                        define(eachIdText.toLowerCase(), argumentType, ctx);

                        if (!(argumentType instanceof FloatBaseType)) putLocals(eachIdText, 1);
                        else putLocals(eachIdText, 2);
                    }
                }
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

        tableManager.displayAllTablesCurrentScope();

        tableManager.allTablesExitNewScope();
        define(id, function, ctx);
        System.out.println("FUNC Prototype DECL ENDS*************************");

        // insert new entry into the tracking map
        // check whether there exist implementation corresponds to current function prototype declaration
        // Ignore Case
        prototypeImplTrackingMap.put(id.toLowerCase(), Pair.of(function, ctx));

        return null;
    }

    @Override
    public TypeDescriptor visitFunctionDecl(PascalParser.FunctionDeclContext ctx) {
        System.out.println("++++++++Function Decl+++++++++");
        tableManager.displayAllTablesCurrentScope();
        String id = ctx.identifier().getText().toLowerCase();

        Pair<TypeDescriptor, ParserRuleContext> functionPrototypePair = null;
        PascalParser.FunctionPrototypeDeclContext functionPrototypeDeclContext = null;
        if (prototypeImplTrackingMap.containsKey(id)) {
            functionPrototypePair = prototypeImplTrackingMap.get(id);
            if (functionPrototypePair.getRight() instanceof PascalParser.FunctionPrototypeDeclContext) {
                functionPrototypeDeclContext = (PascalParser.FunctionPrototypeDeclContext) functionPrototypePair.getRight();
            }
            // remove the entry in the tracking map, no matter whether the implementation can match the header or not
            prototypeImplTrackingMap.remove(id);
        }

        tableManager.allTablesEnterNewScope();

        ArrayList<Class<?>> arguments = new ArrayList<>();
        List<PascalParser.FormalParameterSectionContext> formalParameterSectionContexts = new ArrayList<>();
        if (ctx.formalParameterList() != null) {
            formalParameterSectionContexts = ctx.formalParameterList().formalParameterSection();
        } else if (functionPrototypeDeclContext != null) {
            // if functionDecl ctx is actual the implementation of predefined function
            // populate necessary information from PrototypeDeclContext
            if (functionPrototypeDeclContext.functionHeading().formalParameterList() != null) {
                formalParameterSectionContexts = functionPrototypeDeclContext.functionHeading().formalParameterList().formalParameterSection();
            }
        }
        for (PascalParser.FormalParameterSectionContext each : formalParameterSectionContexts) {
            if (each instanceof PascalParser.NoLabelParamContext) {
                PascalParser.ParameterGroupContext parameterGroupContext = ((PascalParser.NoLabelParamContext) each).parameterGroup();
                List<PascalParser.IdentifierContext> identifierList = parameterGroupContext.identifierList().identifier();
                TypeDescriptor argumentType = visit(parameterGroupContext.typeIdentifier());
                Class<?> argumentTypeDescriptorClass = argumentType.getDescriptorClass();
                for (PascalParser.IdentifierContext eachId : identifierList) {
                    arguments.add(argumentTypeDescriptorClass);
                    String eachIdText = eachId.getText();
                    define(eachIdText.toLowerCase(), argumentType, ctx);

                    if (!(argumentType instanceof FloatBaseType)) putLocals(eachIdText, 1);
                    else putLocals(eachIdText, 2);
                }
            }
        }

        tableManager.displayAllTablesCurrentScope();

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
        String methodDescriptor = getMethodDescriptor(
                resultType.getDescriptorClass(), arguments.toArray(new Class[]{}));

        methodVisitor = classWriter.visitMethod(Opcodes.ACC_PRIVATE | Opcodes.ACC_STATIC,
                id, methodDescriptor, null, null);

        updateDefaultMethodVisitor(methodVisitor);

        methodVisitor.visitCode();

        // define random local var to be returned as final result
        resultVar = "__result" + new Random().nextInt(999);
        define(resultVar, resultType, ctx);
        if (resultType instanceof Primitive) {
            if (!(resultType instanceof FloatBaseType)) putLocals(resultVar, 1);
            else putLocals(resultVar, 2);
        }
        //initialise result value
        InstructionHelper.loadIntOrReal(resultType);
        LoadStoreHelper.storePrimitive(resultType, getVariableSlotNum(resultVar));

        Label enterScope = makeLabel();
        Label exitScope = makeLabel();

        setLabel(methodVisitor, enterScope);

        visit(ctx.block());


        setLabel(methodVisitor, exitScope);

        TypeDescriptor resultVarType = retrieve(resultVar, ctx);
        int resultVarSlotNum = getVariableSlotNum(resultVar);
        LoadStoreHelper.loadIntOrFloatLocal(resultVarType, resultVarSlotNum);
        InstructionHelper.returnFromMethod(resultType);
        //methodVisitor.visitInsn(Opcodes.RETURN);

        System.out.println("Func locals");
        tableManager.displayAllTablesCurrentScope();
        tableManager.showAllTables();

        // fill in the local variables
        // in terms of symbol table & local variable table (get slot number, in case of underlying order is not right)
        symbolTable.displayCurrentScope();
        Map<Object, TypeDescriptor> symbolTableAllVarInCurrentScope = symbolTable.getAllVarInCurrentScope();
        symbolTableAllVarInCurrentScope.forEach((k, v) -> {
            System.out.println("k = " + k + " v= " + v);
            if (localVariableTable.containsKey(k)) {
                LocalVariableInformation localVariableInformation = localVariableTable.get(k);
                int slotNum = localVariableInformation.getSlotNum();
                methodVisitor.visitLocalVariable(k.toString(), v.getDescriptor(),
                        null,
                        enterScope, exitScope, slotNum);
            }
        });

        methodVisitor.visitMaxs(2, 2); // this would be compute automatically
        methodVisitor.visitEnd();

        tableManager.allTablesExitNewScope();

        // reset result var variable identifier
        resultVar = null;
        return null;
    }

    /**
     * procedureDeclaration
     * : procedureHeading SEMI directive #procedurePrototypeDecl
     *
     * @param ctx
     * @return
     */
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
                TypeDescriptor argumentType = visit(paramSection);
                if (paramSection instanceof PascalParser.NoLabelParamContext) {
                    PascalParser.IdentifierListContext identifierListContext = ((PascalParser.NoLabelParamContext) paramSection).parameterGroup().identifierList();
                    for (PascalParser.IdentifierContext eachId : identifierListContext.identifier()) {
                        String eachIdText = eachId.getText();
                        define(eachIdText.toLowerCase(), argumentType, ctx);

                        if (!(argumentType instanceof FloatBaseType)) putLocals(eachIdText, 1);
                        else putLocals(eachIdText, 2);
                    }
                }
            }
            // all formal params set up
            Map<Object, TypeDescriptor> allParams = symbolTable.getAllVarInCurrentScope();
            System.out.println("allParams = " + allParams);
            allParams.forEach((k, v) -> params.add(v));
        }
        Procedure procedure = new Procedure(params);
        define(id, procedure, ctx);
        System.out.println("Define Func Prototype signature");
        System.out.println("procedure = " + procedure);

        tableManager.displayAllTablesCurrentScope();

        tableManager.allTablesExitNewScope();
        define(id, procedure, ctx);
        System.out.println("Proc Prototype DECL ENDS*************************");

        // insert new entry into the tracking map
        // check whether there exist implementation corresponds to current function prototype declaration
        // Ignore Case
        prototypeImplTrackingMap.put(id.toLowerCase(), Pair.of(procedure, ctx));
        return null;
    }

    @Override
    public TypeDescriptor visitProcedureDecl(PascalParser.ProcedureDeclContext ctx) {
        System.out.println("++++++++Procedure Decl+++++++++");
        tableManager.displayAllTablesCurrentScope();
        String id = ctx.identifier().getText().toLowerCase();

        PascalParser.ProcedurePrototypeDeclContext procedurePrototypeDeclContext = null;
        if (prototypeImplTrackingMap.containsKey(id)) {
            Pair<TypeDescriptor, ParserRuleContext> procedurePrototypePair = prototypeImplTrackingMap.get(id);
            if (procedurePrototypePair.getRight() instanceof PascalParser.ProcedurePrototypeDeclContext) {
                procedurePrototypeDeclContext = (PascalParser.ProcedurePrototypeDeclContext) procedurePrototypePair.getRight();
            }
            // remove the entry in the tracking map, no matter whether the implementation can match the header or not
            prototypeImplTrackingMap.remove(id);
        }

        tableManager.allTablesEnterNewScope();

        ArrayList<Type> arguments = new ArrayList<>();
        List<PascalParser.FormalParameterSectionContext> formalParameterSectionContexts = new ArrayList<>();
        if (ctx.formalParameterList() != null) {
            formalParameterSectionContexts = ctx.formalParameterList().formalParameterSection();
        } else if (procedurePrototypeDeclContext != null) {
            // if ProcDecl ctx is actual the implementation of predefined procedure
            // populate necessary information from PrototypeDeclContext
            if (procedurePrototypeDeclContext.procedureHeading().formalParameterList() != null) {
                formalParameterSectionContexts = procedurePrototypeDeclContext.procedureHeading().formalParameterList().formalParameterSection();
            }
        }
        for (PascalParser.FormalParameterSectionContext each : formalParameterSectionContexts) {
            if (each instanceof PascalParser.NoLabelParamContext) {
                PascalParser.ParameterGroupContext parameterGroupContext = ((PascalParser.NoLabelParamContext) each).parameterGroup();
                List<PascalParser.IdentifierContext> identifierList = parameterGroupContext.identifierList().identifier();
                TypeDescriptor argumentType = visit(parameterGroupContext.typeIdentifier());
                Class<?> argumentTypeDescriptorClass = argumentType.getDescriptorClass();
                for (PascalParser.IdentifierContext eachId : identifierList) {
                    //arguments[i++] = Type.getType(argumentTypeDescriptorClass);
                    arguments.add(Type.getType(argumentTypeDescriptorClass));
                    String eachIdText = eachId.getText();
                    define(eachIdText.toLowerCase(), argumentType, ctx);

                    if (!(argumentType instanceof FloatBaseType)) putLocals(eachIdText, 1);
                    else putLocals(eachIdText, 2);
                }
            }
        }

        tableManager.displayAllTablesCurrentScope();

        String methodDescriptor = Type.getMethodDescriptor(
                Type.VOID_TYPE, arguments.toArray(new Type[]{})
        );

        methodVisitor = classWriter.visitMethod(Opcodes.ACC_PRIVATE | Opcodes.ACC_STATIC,
                id, methodDescriptor, null, null);

        updateDefaultMethodVisitor(methodVisitor);

        methodVisitor.visitCode();

        Label enterScope = makeLabel();
        Label exitScope = makeLabel();

        setLabel(methodVisitor, enterScope);

        visit(ctx.block());

        setLabel(methodVisitor, exitScope);

        InstructionHelper.returnFromMethod(null);
        //methodVisitor.visitInsn(Opcodes.RETURN);

        System.out.println("Proc locals");
        tableManager.displayAllTablesCurrentScope();
        tableManager.showAllTables();

        // fill in the local variables
        // in terms of symbol table & local variable table (get slot number, in case of underlying order is not right)
        symbolTable.displayCurrentScope();
        Map<Object, TypeDescriptor> symbolTableAllVarInCurrentScope = symbolTable.getAllVarInCurrentScope();
        symbolTableAllVarInCurrentScope.forEach((k, v) -> {
            LocalVariableInformation localVariableInformation = localVariableTable.get(k);
            int slotNum = localVariableInformation.getSlotNum();
            methodVisitor.visitLocalVariable(k.toString(), v.getDescriptor(),
                    null,
                    enterScope, exitScope, slotNum);
        });

        methodVisitor.visitMaxs(2, 2); // this would be compute automatically
        methodVisitor.visitEnd();

        tableManager.allTablesExitNewScope();
        return null;
    }

    /**
     * Define global variables
     *
     * @param ctx
     * @return
     */
    @Override
    public TypeDescriptor visitVariableDeclarationPart(PascalParser.VariableDeclarationPartContext ctx) {
        List<PascalParser.VariableDeclarationContext> variableDeclarationContexts = ctx.variableDeclaration();
        variableDeclarationContexts.forEach(this::visit);
        return null;
    }

    /**
     * Define field with specific type
     * <p>
     * ! if Gloabl var decl
     * - All variable declared in this part would be treated as static fields
     * ! if Proc/Func var decl
     * - all vars are treated as local variables (must initialised with default value)
     * <p>
     * variableDeclaration
     * : identifierList COLON type_
     * ;
     *
     * @param ctx
     * @return
     */
    @Override
    public TypeDescriptor visitVariableDeclaration(PascalParser.VariableDeclarationContext ctx) {
        System.out.println("++++++++Var decl start++++++++");
        System.out.println("var decl ctx.getText() = " + ctx.getText());
        String typeDescriptor = null;
        TypeDescriptor type = visit(ctx.type_());
        System.out.println("var decl type = " + type);
        typeDescriptor = type.getDescriptor();

        List<PascalParser.IdentifierContext> identifiers = ctx.identifierList().identifier();
        for (PascalParser.IdentifierContext each : identifiers) {
            // case insensitive
            String id = each.getText().toLowerCase();
            System.out.println("ctx.parent.parent = " + ctx.parent.parent.getClass());
            if (ctx.parent.parent.parent instanceof PascalParser.ProgramContext) {
                fieldVisitor = classWriter.visitField(Opcodes.ACC_PRIVATE | Opcodes.ACC_STATIC,
                        id, typeDescriptor, null, null);
                fieldVisitor.visitEnd();
            } else {
                if (!(type instanceof FloatBaseType)) putLocals(id, 1, false);
                else putLocals(id, 2);

                if (type instanceof Primitive) {
                    InstructionHelper.loadIntOrReal(type);
                }
                // store the local var based on the type descriptor
                // use corresponding store instruction
                int slotNum = getVariableSlotNum(id);
                LoadStoreHelper.storePrimitive(type, slotNum);
                //invoke(Instruction.STORE_REFERENCE);

            }
            // insert into symbol table
            define(id, type, ctx);

        }

        System.out.println("Finished variable initialisation");
        tableManager.displayAllTablesCurrentScope();

        return null;
    }

    @Override
    public TypeDescriptor visitAssignmentStatement(PascalParser.AssignmentStatementContext ctx) {
        TypeDescriptor expressionType = visit(ctx.expression());
        System.out.println("expressionType = " + expressionType);
        String id = ctx.variable().getText().toLowerCase();
        TypeDescriptor lType = retrieve(id, ctx);
        System.out.println("assignment lType = " + lType);

        // function return statement(assignment)
        if (lType instanceof Function) {
            // store the content to local result variable
            System.out.println("Function return assignment");
            tableManager.displayAllTablesCurrentScope();
            TypeDescriptor resultType = retrieve(resultVar, ctx);
            int slotNum = getVariableSlotNum(resultVar);
            if (resultType instanceof Primitive) LoadStoreHelper.storePrimitive(resultType, slotNum);
            return null;
        }

        if (!expressionType.equiv(lType)) {
            if (expressionType instanceof IntegerBaseType && lType instanceof FloatBaseType) {
                // int convert to float before putStatic
                TypeConverterHelper.I2D();
            }
        }

        // store value to static field
        if (isStaticField(id)) {
            System.out.println("lType.getDescriptorClass() = " + lType.getDescriptorClass());
            InstructionHelper.putStatic(className, id, lType);
            //methodVisitor.visitFieldInsn(Opcodes.PUTSTATIC, className, id, lType.getDescriptor());
        } else {
            // store value to local varaible
            int variableSlotNum = getVariableSlotNum(id);
            LoadStoreHelper.storePrimitive(lType, variableSlotNum);
        }
        return null;
    }

    private Label makeLabel() {
        return new Label();
    }

    private void gotoLabel(Label label) {
        JumpInstructionHelper.gotoLabel(label);
    }

    private void setLabel(MethodVisitor mv, Label label) {
        mv.visitLabel(label);
    }

    private void setLabel(Label label) {
        setLabel(methodVisitor, label);
    }

    /**
     * ifeq	153	Jump if int comparison with zero succeeds
     * ifge	156	Jump if int comparison with zero succeeds
     * ifgt	157	Jump if int comparison with zero succeeds
     * ifle	158	Jump if int comparison with zero succeeds
     * iflt	155	Jump if int comparison with zero succeeds
     * ifne	154	Jump if int comparison with zero succeeds
     */
    Map<String, Integer> relationalOpMapping = Map.of(
            "<", Opcodes.IFGE,
            ">", Opcodes.IFLE,
            "=", Opcodes.IFNE,
            "<>", Opcodes.IFEQ,
            "<=", Opcodes.IFGT,
            ">=", Opcodes.IFLT);

    Map<String, Integer> relationalOpMappingWithDouble = Map.of(
            "<", Opcodes.DCMPG,
            ">", Opcodes.DCMPL,
            "=", Opcodes.DCMPL,
            "<>", Opcodes.DCMPL,
            "<=", Opcodes.DCMPG,
            ">=", Opcodes.DCMPL);

    Map<String, Integer> relationalOpMappingWithInt = Map.of(
            "<", Opcodes.IF_ICMPGE,
            ">", Opcodes.IF_ICMPLE,
            "=", Opcodes.IF_ICMPNE,
            "<>", Opcodes.IF_ICMPEQ,
            "<=", Opcodes.IF_ICMPGT,
            ">=", Opcodes.IF_ICMPLT);

    /**
     * TODO OTHER OP OTHER TYPES
     * TODO Refactor for For,while statement(too coupling)
     *
     * @param operator
     */
    private void invokeRelationalInstruction(String operator, TypeDescriptor lType, TypeDescriptor rType, PascalParser.IfStatementContext ifStatementContext, PascalParser.WhileStatementContext whileStatementContext, boolean involveConstantIf) {
        boolean involveReal = lType instanceof FloatBaseType || rType instanceof FloatBaseType;
        boolean involveStr = lType instanceof StringLiteral || rType instanceof StringLiteral;
        // prepare labels
        Label evaluateToFalse = makeLabel();
        Label endLabel = makeLabel();

        if (involveReal || involveStr) {
            if (involveReal) methodVisitor.visitInsn(relationalOpMappingWithDouble.get(operator));
            if (involveStr) {
                InstructionHelper.invokeStatic(StringUtils.class, "compare", false,
                        String.class, String.class);
            }
            JumpInstructionHelper.jumpInstruction(relationalOpMapping.get(operator), evaluateToFalse);
        } else {
            if (involveConstantIf) {
                System.out.println("int compare " + operator);
                InstructionHelper.loadTrueOrFalse(true);
            }
            JumpInstructionHelper.jumpInstruction(
                    relationalOpMappingWithInt.get(operator),
                    evaluateToFalse);
        }

        if (ifStatementContext != null) {
            System.out.println("if block");
            visit(ifStatementContext.statement(0));
        } else if (whileStatementContext != null) {
            System.out.println("while block");
            visit(whileStatementContext.statement());
        } else {
            InstructionHelper.loadTrueOrFalse(true);
        }

        System.out.println("whileExprStart = " + whileExprStart);
        if (whileStatementContext != null) {
            System.out.println("goto whileExprStart");
            gotoLabel(whileExprStart);
        } else gotoLabel(endLabel);
        //gotoLabel(endLabel);

        setLabel(evaluateToFalse);

        if (ifStatementContext != null && ifStatementContext.statement().size() > 1) {
            System.out.println("else block");
            visit(ifStatementContext.statement(1));
        } else if (whileStatementContext == null && ifStatementContext == null) {
            InstructionHelper.loadTrueOrFalse(false);
        }
        setLabel(endLabel);
    }

    private void invokeRelationalInstruction(String operator, TypeDescriptor lType, TypeDescriptor rType, PascalParser.IfStatementContext ifStatementContext, PascalParser.WhileStatementContext whileStatementContext) {
        invokeRelationalInstruction(operator, lType, rType, ifStatementContext, whileStatementContext, false);
    }

    private void invokeRelationalInstructionFromRepeat(String operator, TypeDescriptor lType, TypeDescriptor rType) {
        boolean involveReal = lType instanceof FloatBaseType || rType instanceof FloatBaseType;
        boolean involveStr = lType instanceof StringLiteral || rType instanceof StringLiteral;

        if (involveReal || involveStr) {
            if (involveReal) methodVisitor.visitInsn(relationalOpMappingWithDouble.get(operator));
            if (involveStr) {
                InstructionHelper.invokeStatic(StringUtils.class, "compare", false,
                        String.class, String.class);
            }
            JumpInstructionHelper.jumpInstruction(relationalOpMapping.get(operator), repeatBlockStart);
        } else {
            JumpInstructionHelper.jumpInstruction(
                    relationalOpMappingWithInt.get(operator),
                    repeatBlockStart);
        }
    }


    /**
     * expression
     * : simpleExpression (relationalOperator=(EQUAL| NOT_EQUAL| LT| LE| GE| GT| IN)
     * e2=expression)?
     * ;
     * <p>
     * Relational op between int and real is allowed
     * ! but need to convert int to double before compare
     * ! and use DXXX instruction if real involved
     *
     * @param ctx
     * @return
     */
    @Override
    public TypeDescriptor visitExpression(PascalParser.ExpressionContext ctx) {
        //whileExprStart = makeLabel();
        //if (ctx.parent instanceof PascalParser.WhileStatementContext) setLabel(whileExprStart);

        System.out.println("ctx.getText() = " + ctx.getText());
        System.out.println("expr ctx.parent.getClass() = " + ctx.parent.getClass());

        boolean fromIfStatement = ctx.parent.parent.parent.parent.parent.parent.getClass() == PascalParser.IfStatementContext.class ||
                ctx.parent.getClass() == PascalParser.IfStatementContext.class;
        PascalParser.IfStatementContext ifStatementContext = null;
        if (fromIfStatement) {
            if (ctx.parent.getClass() == PascalParser.IfStatementContext.class) {
                ifStatementContext = (PascalParser.IfStatementContext) ctx.parent;
            } else ifStatementContext = (PascalParser.IfStatementContext) ctx.parent.parent.parent.parent.parent.parent;
        }
        if (ifStatementContext != null)
            System.out.println("ifStatementContext.getText() = " + ifStatementContext.getText());

        boolean fromWhileStatement = ctx.parent.getClass() == PascalParser.WhileStatementContext.class;
        PascalParser.WhileStatementContext whileStatementContext = null;
        if (fromWhileStatement) {
            whileStatementContext = (PascalParser.WhileStatementContext) ctx.parent;
        }

        boolean fromRepeatStatement = ctx.parent.getClass() == PascalParser.RepeatStatementContext.class;

        TypeDescriptor lType = visit(ctx.simpleExpression());

        if (ctx.expression() != null) {
            // if relational op exists,
            // check first whether rType involve real, before push onto stack
            Collection<ParseTree> matches;
            matches = XPath.findAll(ctx, "//NUM_REAL", PascalCompilerDriverBuilder.parser);
            boolean rTypeIsReal = !matches.isEmpty();

            // check whether rType involve String/Char
            matches = XPath.findAll(ctx, "//STRING_LITERAL", PascalCompilerDriverBuilder.parser);
            boolean rTypeIsStr = !matches.isEmpty();
            // check further whether rType is char
            boolean rTypeIsChar = false;
            if (rTypeIsStr) {
                String rawText = ctx.expression().getText().replace("'", "");
                rTypeIsChar = rawText.length() == 1;
            }

            // if rType involve real, lType is int
            // convert left operand first
            if (rTypeIsReal && lType instanceof IntegerBaseType) {
                TypeConverterHelper.I2D();
            }

            //if rType is String, lType is Char
            //convert left operand
            if (rTypeIsStr && !rTypeIsChar && lType instanceof Character) {
                InstructionHelper.invokeStatic(String.class, "valueOf", false, char.class);
            }

            // push right operand onto stack
            TypeDescriptor rType = visit(ctx.expression());

            // if left is real, right is int, convert right operand
            if (rType instanceof IntegerBaseType && lType instanceof FloatBaseType) {
                TypeConverterHelper.I2D();
            }
            // if left is String, right is char, convert right operand
            if (lType instanceof StringLiteral && rType instanceof Character) {
                InstructionHelper.invokeStatic(String.class, "valueOf", false, char.class);
            }

            String relationalOperator = ctx.relationalOperator.getText().toLowerCase();

            if (lType instanceof Primitive && rType instanceof Primitive) {
                //if (fromIfStatement) invokeRelationalInstruction(relationalOperator, lType, rType, ifStatementContext);
                if (fromRepeatStatement) invokeRelationalInstructionFromRepeat(relationalOperator,lType,rType);
                else invokeRelationalInstruction(relationalOperator, lType, rType, ifStatementContext, whileStatementContext);
            }
            if (lType instanceof StringLiteral || rType instanceof StringLiteral) {
                if (fromRepeatStatement) invokeRelationalInstructionFromRepeat(relationalOperator,lType,rType);
                else invokeRelationalInstruction(relationalOperator, lType, rType, ifStatementContext, whileStatementContext);
            }

            return new Boolean();
        }
        // if only involve 1 operand(left) and from if statement
        if (fromIfStatement && (ctx.getText().equalsIgnoreCase("true") ||
                ctx.getText().equalsIgnoreCase("false"))) {
            System.out.println("Constant expr found");
            invokeRelationalInstruction("=", null, null, ifStatementContext,
                    null, true);
        }

        return lType;
    }

    private Stack<TypeDescriptor> operandStack = new Stack<>();
    /**
     * additiveOperator=(PLUS| MINUS| OR)
     * TODO: OR
     *
     * @param operator
     */
    private void invokeAdditiveInstruction(String operator, TypeDescriptor lType, TypeDescriptor rType) {
        TypeDescriptor resultType = DefaultIntegerType.instance;
        if (lType instanceof FloatBaseType || rType instanceof FloatBaseType) {
            resultType = DefaultFloatType.instance;
        }
        switch (operator) {
            case "+":
                InstructionHelper.add(resultType);
                break;
            case "-":
                InstructionHelper.sub(resultType);
                break;
            case "or":
                InstructionHelper.intLogicalOp(Opcodes.IOR);
                break;
        }
    }


    /**
     * simpleExpression
     * : term (additiveOperator=(PLUS| MINUS| OR) simpleExpression)?
     * ;
     *
     * @param ctx
     * @return
     */
    @Override
    public TypeDescriptor visitSimpleExpression(PascalParser.SimpleExpressionContext ctx) {
        System.out.println("simple expr ctx.parent.parent.getClass() = " + ctx.parent.parent.getClass());
        TypeDescriptor lType = visit(ctx.term());
        System.out.println("simple expr ctx.getText() = " + ctx.getText());
        System.out.println("simple expression lType = " + lType);

        if (ctx.simpleExpression() != null) {
            String operator = ctx.additiveOperator.getText().toLowerCase();
            //if (!operator.equals("or")) {
            // determine whether do left type conversion first
            // before access right field

            String rId = "";
            if (ctx.simpleExpression().term() != null) rId = ctx.simpleExpression().getText().toLowerCase();
            TypeDescriptor rType = retrieve(rId, ctx);
            if (!rType.equiv(ErrorType.UNDEFINED_TYPE) || rId.contains(".")) {
                // if one of the operands is real type
                if (lType instanceof FloatBaseType || (rType instanceof FloatBaseType || rId.contains("."))) {
                    // convert if left type is integer
                    if (lType instanceof IntegerBaseType) {
                        TypeConverterHelper.I2D();
                    }
                }
            }

            TypeDescriptor _rType = visit(ctx.simpleExpression().term());
            // check whether right type need to do type conversion
            if (lType instanceof FloatBaseType || _rType instanceof FloatBaseType) {
                if (_rType instanceof IntegerBaseType) {
                    TypeConverterHelper.I2D();
                }
            }

            invokeAdditiveInstruction(operator, lType, _rType);
            if (ctx.simpleExpression().simpleExpression() != null) {
                TypeDescriptor type = visit(ctx.simpleExpression().simpleExpression());
                String _operator = ctx.simpleExpression().additiveOperator.getText();

                // type conversion before arithmetic operation
                if (_rType instanceof FloatBaseType || type instanceof FloatBaseType) {
                    if (type instanceof IntegerBaseType) {
                        TypeConverterHelper.I2D();
                    }
                }
                invokeAdditiveInstruction(_operator, _rType, type);
            }
            // or - logical operation
            if (lType instanceof Boolean && _rType instanceof Boolean) {
                return new Boolean();
            }

            if (lType instanceof FloatBaseType || _rType instanceof FloatBaseType) {
                Real real = new Real();
                real.setConstant(true);
                return real;
            }
            Integer32 integer32 = new Integer32();
            integer32.setConstant(true);
            return integer32;

        }
        return lType;
    }

    /**
     * multiplicativeOperator=(STAR| SLASH| DIV| MOD| AND)
     * TODO: AND
     *
     * @param operator
     */
    private void invokeMultiplicativeInstruction(String operator, TypeDescriptor lType, TypeDescriptor rType) {
        TypeDescriptor resultType = DefaultIntegerType.instance;
        if (lType instanceof FloatBaseType || rType instanceof FloatBaseType) {
            resultType = DefaultFloatType.instance;
        }
        switch (operator) {
            case "*":
                InstructionHelper.mul(resultType);
                break;
            case "/":
                // real division
                InstructionHelper.realDiv();
                break;
            case "div":
                // integer division, operands must be integer
                InstructionHelper.intDiv();
                break;
            case "mod":
                InstructionHelper.intMod();
                break;
            case "and":
                InstructionHelper.intLogicalOp(Opcodes.IAND);
                break;
        }
    }

    /**
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
        System.out.println("term ctx.signedFactor().getText() = " + ctx.signedFactor().getText());
        System.out.println("lType = " + lType);
        if (ctx.term() != null) {
            String operator = ctx.multiplicativeOperator.getText().toLowerCase();

            if (operator.contains("and")) {
                visit(ctx.term());
                invokeMultiplicativeInstruction(operator, lType, null);
                return new Boolean();
            }

            if (operator.equals("*")) {
                String rId = ctx.term().getText().toLowerCase();
                TypeDescriptor rType = retrieve(rId, ctx);

                System.out.println("rId = " + rId);
                System.out.println("retrieve type before visit rType = " + rType);
                // if rId is variable identifier not constant literal
                // check whether need type conversion
                boolean convertLType = false;
                if (!rType.equiv(ErrorType.UNDEFINED_TYPE)) {
                    if (lType instanceof FloatBaseType || rType instanceof FloatBaseType) {
                        if (lType instanceof IntegerBaseType) {
                            convertLType = true;
                        }
                    }
                } else {
                    // if right operand is literal,
                    // if contains '.' then must involve real type
                    if (lType instanceof FloatBaseType || rId.contains(".")) {
                        if (lType instanceof IntegerBaseType) {
                            convertLType = true;
                        }
                    }
                }
                // convert Ltype before visit right field
                if (convertLType) {
                    TypeConverterHelper.I2D();
                }

                // get right field first then check whether conversion needed
                TypeDescriptor _rType = visit(ctx.term());

                if (lType instanceof FloatBaseType && _rType instanceof IntegerBaseType) {
                    TypeConverterHelper.I2D();
                }
                System.out.println("left ctx.signedFactor().getText() = " + ctx.signedFactor().getText());
                System.out.println("lType = " + lType);
                System.out.println("right ctx.term().getText() = " + ctx.term().getText());
                System.out.println("_rType = " + _rType);
                System.out.println();
                invokeMultiplicativeInstruction(operator, lType, _rType);

                //if any real operands involved, then return real
                if (lType instanceof FloatBaseType || _rType instanceof FloatBaseType) {
                    //Real real = new Real();
                    //real.setConstant(true);
                    //return real;
                    return DefaultFloatType.instance;
                }
                // otherwise return integer
                return DefaultIntegerType.instance;
                //Integer32 integer32 = new Integer32();
                //integer32.setConstant(true);
                //return integer32;
            }

            // real division, must return real type
            if (operator.equals("/")) {

                // if operands involved integer, convert to float first
                if (lType instanceof IntegerBaseType) {
                    TypeConverterHelper.I2D();
                }
                TypeDescriptor _rType = visit(ctx.term());
                if (_rType instanceof IntegerBaseType) {
                    TypeConverterHelper.I2D();
                }

                invokeMultiplicativeInstruction(operator, lType, _rType);
                return DefaultFloatType.instance;
            }

            // integer division, must return integer
            if (operator.equals("div")) {
                invokeMultiplicativeInstruction(operator, lType, visit(ctx.term()));
            }

            // modulo operation, return integer
            if (operator.equals("mod")) {
                invokeMultiplicativeInstruction(operator, lType, visit(ctx.term()));
            }

            return DefaultIntegerType.instance;

        }
        return lType;
    }

    /**
     * signedFactor
     * : monadicOperator=(PLUS | MINUS)? factor
     * ;
     *
     * @param ctx
     * @return
     */
    @Override
    public TypeDescriptor visitSignedFactor(PascalParser.SignedFactorContext ctx) {
        // capture maxint
        System.out.println();
        System.out.println("signed factor ctx.getText() = " + ctx.getText());
        String factorId = ctx.factor().getText().toLowerCase();
        int monadicOperatorType = -999;
        if (ctx.monadicOperator != null) {
            monadicOperatorType = ctx.monadicOperator.getType();
        }
        if (ctx.factor() instanceof PascalParser.FactorVarContext) {
            if (factorId.equals("maxint")) {
                Integer32 maxint = DefaultIntegerType.of(Long.valueOf(Integer.MAX_VALUE));
                if (loadConst) InstructionHelper.loadIntOrReal(maxint);
                loadConst = true;
                maxint.setConstant(true);
                if (monadicOperatorType == PascalParser.MINUS) {
                    InstructionHelper.intOrFloatNeg(maxint);
                }
                return maxint;
            }
            //    other Variable, load Reference
        }
        TypeDescriptor type = visit(ctx.factor());
        if (monadicOperatorType == PascalParser.MINUS) {
            InstructionHelper.intOrFloatNeg(type);
        }
        System.out.println("factor type = " + type);
        return type;
    }


    /**
     * NOT factor #notFactor
     *
     * @param ctx
     * @return
     */
    @Override
    public TypeDescriptor visitNotFactor(PascalParser.NotFactorContext ctx) {
        // visit boolean first
        // load boolean static field or constants onto stack
        visit(ctx.factor());

        Label changeToFalse = makeLabel();
        Label endLabel = makeLabel();

        // if boolean operand != 0 (false)
        // then jump to changeToFalse
        methodVisitor.visitJumpInsn(Opcodes.IFNE, changeToFalse);
        // if boolean operand ==0 (false)
        // load true (0)
        if (loadConst) InstructionHelper.loadTrueOrFalse(true);
        loadConst = true;
        // directly jump to the end
        methodVisitor.visitJumpInsn(Opcodes.GOTO, endLabel);

        methodVisitor.visitLabel(changeToFalse);
        if (loadConst) InstructionHelper.loadTrueOrFalse(false);
        loadConst = true;

        methodVisitor.visitLabel(endLabel);
        return new Boolean();
    }

    /**
     * Variable
     *
     * @param ctx
     * @return
     */
    @Override
    public TypeDescriptor visitFactorVar(PascalParser.FactorVarContext ctx) {
        return super.visitFactorVar(ctx);
    }

    @Override
    public TypeDescriptor visitFactorExpr(PascalParser.FactorExprContext ctx) {
        //System.out.println("factorExpr ctx.getText() = " + ctx.getText());
        TypeDescriptor type = visit(ctx.expression());
        //System.out.println("factor expr type = " + type);
        return type;
    }

    private boolean isStaticField(String id) {
        return !localVariableTable.containsKey(id);
    }

    private TypeDescriptor functionDesignator(String functionId, Function function) {
        System.out.println("Call Function Designator Manually");
        TypeDescriptor resultType = function.getResultType();
        Class<?>[] funcArgumentsClass = new Class<?>[0];

        // function must not return void though
        Class<?> resultTypeDescriptorClass = resultType != null ? resultType.getDescriptorClass() : void.class;
        String funcDescriptor = getMethodDescriptor(resultTypeDescriptorClass, funcArgumentsClass);
        System.out.println("funcDescriptor = " + funcDescriptor);
        InstructionHelper.invokeStatic(className, functionId, funcDescriptor, false);
        return resultType;
    }

    /**
     * Variable declared in variable declaration part
     * Find in LocalVariableTable, if static then visit
     * <p>
     * variable
     * : variableHead
     * (
     * arrayScripting
     * | fieldDesignator
     * | POINTER // only for pointer variable
     * )*
     *
     * @param ctx
     * @return
     */
    @Override
    public TypeDescriptor visitVariable(PascalParser.VariableContext ctx) {
        System.out.println("visitVariable ctx.getText() = " + ctx.getText());
        symbolTable.displayCurrentScope();

        TypeDescriptor type = visit(ctx.variableHead());
        //TypeDescriptor type = visit(ctx);
        System.out.println("type = " + type);

        // if should actually match [non-args] function
        // call function designator logics manually from this point
        if (type instanceof Function) {
            String functionId = ctx.variableHead().getText().toLowerCase();
            return functionDesignator(functionId, (Function) type);
        }

        symbolTable.displayCurrentScope();
        localVariableTable.displayCurrentScope();

        String id = ctx.getText().toLowerCase();
        System.out.println("id = " + id);

        // if variable from readStatement, skip static field access
        if (ctx.parent instanceof PascalParser.InputValueContext) return type;

        // static field access
        System.out.println("visitVariable ctx.getText() = " + ctx.getText());
        System.out.println("visitVariable type = " + type);
        System.out.println("isStaticField(id) = " + isStaticField(id));
        if (isStaticField(id)) {
            tableManager.displayAllTablesCurrentScope();
            InstructionHelper.getStatic(className, id, type);
        } else {
            LocalVariableInformation localVariableInformation = localVariableTable.get(id);
            System.out.println("localVariableInformation = " + localVariableInformation);
            LoadStoreHelper.loadIntOrFloatLocal(type, localVariableInformation.getSlotNum());
            //methodVisitor.visitVarInsn(Opcodes.ILOAD,localVariableInformation.getSlotNum());
        }
        return type;
    }

    @Override
    public TypeDescriptor visitIdentifier(PascalParser.IdentifierContext ctx) {
        System.out.println("ctx.IDENT().getText() = " + ctx.IDENT().getText());
        TypeDescriptor type = retrieve(ctx.getText().toLowerCase(), ctx);
        //methodVisitor.visitFieldInsn(Opcodes.GETSTATIC, className, "sum", "Ljava/lang/Integer;");

        System.out.println("type.fgetDescriptor() = " + type.getDescriptor());
        //methodVisitor.visitFieldInsn(Opcodes.GETSTATIC, className, ctx.getText().toLowerCase(), type.getDescriptor());
        return type;
    }

    private void putLocals(String id, int length, boolean isStatic) {
        System.out.println("put locals - id = " + id);
        System.out.println("length = " + length);
        localVariableTable.put(id, new LocalVariableInformation(length, isStatic));
        System.out.println("slot num " + localVariableTable.get(id).getSlotNum());
        localVariableTable.displayCurrentScope();
    }

    private void putLocals(String id, int length) {
        putLocals(id, length, false);
    }

    private void putReferenceTypeIntoLocals(String id, boolean isStatic) {
        putLocals(id, 1, isStatic);
    }

    private void putReferenceTypeIntoLocals(String id) {
        putReferenceTypeIntoLocals(id, false);
    }

    private int getVariableSlotNum(String id) {
        return localVariableTable.get(id).getSlotNum();
    }

    // default invoke, pass default methodVisitor and localVariablesTable
    // no id usage
    private void invoke(Instruction instruction) {
        instruction.invoke(methodVisitor, localVariableTable);
    }

    // invoke with id information
    // related instructions:
    // - LoadReference
    //private void invoke(Instruction instruction, String id) {
    //    invoke(instruction, methodVisitor, localVariableTable, id);
    //}

    //private void invokeVirtual(MethodVisitor mv, Class<?> owner, String methodName, Class<?>...arguments) {
    //    InstructionHelper.invokeVirtual(mv,owner,methodName,arguments);
    //}prede
    //
    //// invokeVirtual with default methodVisitor
    //private void invokeVirtual(Class<?> owner, String methodName, Class<?>...arguments) {
    //    invokeVirtual(methodVisitor,owner,methodName,arguments);
    //}
    //
    //private void getStatic(MethodVisitor mv, Class<?> owner, String fieldName) {
    //    InstructionHelper.getStatic(mv,owner , fieldName);
    //}
    //
    //private void getStatic(Class<?> owner, String fieldName) {
    //    getStatic(methodVisitor,owner,fieldName);
    //}

    private MethodType getMethodType(Class<?> returnType, Class<?>... args) {
        return MethodType.methodType(returnType, args);
    }

    private String getMethodDescriptor(Class<?> returnType, Class<?>... args) {
        MethodType methodType = getMethodType(returnType, args);
        return methodType.toMethodDescriptorString();
    }


    // FIXME: this can be removed, if current write is left
    boolean loadConst = true;

    @Override
    public TypeDescriptor visitWriteProcedureStatement(PascalParser.WriteProcedureStatementContext ctx) {
        //invoke(Instruction.CREATE_STRING_BUILDER);
        //invoke(Instruction.STORE_REFERENCE);
        //
        //String stringBuilder = "stringBuilder" + UUID.randomUUID().toString();
        //System.out.println("stringBuilder = " + stringBuilder);
        //putReferenceTypeIntoLocals(stringBuilder);
        //localVariableTable.displayCurrentScope();

        //MethodType.

        // get static std output
        InstructionHelper.getStatic(System.class, "out");

        // load string builder
        //LoadStoreHelper.loadReference(getVariableSlot(stringBuilder));

        boolean involvesConstant = false;
        //prepare strings
        if (ctx.writeParameters() != null) {
            List<PascalParser.OutputValueContext> outputValueContexts = ctx.writeParameters().outputValue();
            if (outputValueContexts.isEmpty()) {
                String printMethodName = ctx.WRITELN() != null ? "println" : "print";
                InstructionHelper.invokeVirtual(PrintStream.class, printMethodName);
                return null;
            }
            //System.out.println("StringUtils.join(\"StringTest\", \"\\u0001\") = " +
            //        StringUtils.join("StringTest", "\u0001", "hhhhh"));
            // single param, do no concat
            if (outputValueContexts.size() == 1) {
                System.out.println("outputValueContexts = " + outputValueContexts.get(0).getText());
                TypeDescriptor type = visit(outputValueContexts.get(0));
                String methodName = "println";
                if (ctx.WRITE() != null) {
                    methodName = "print";
                }
                InstructionHelper.invokeVirtual(PrintStream.class, methodName, type.getDescriptorClass());
                return null;
            }

            StringBuilder preprocessedStr = new StringBuilder();
            // multiple params, perform concat and string processing
            List<Class<?>> argumentClass = new ArrayList<>();
            for (PascalParser.OutputValueContext each : outputValueContexts) {

                //loadConst = false;

                TypeDescriptor type = visit(each);
                System.out.println("outputvalue.getText() = " + each.getText());
                System.out.println("outputvalue type = " + type);

                if (type instanceof Primitive || type instanceof StringLiteral) {
                    boolean isConst = ((BaseType) type).isConstant();
                    Object constantValue = null;

                    if (type instanceof Primitive) {
                        Primitive<?> _type = (Primitive<?>) type;
                        constantValue = _type.getValue();
                    }
                    if (type instanceof StringLiteral) {
                        constantValue = ((StringLiteral) type).getValue();
                    }

                    preprocessedStr.append("\u0001");
                    argumentClass.add(type.getDescriptorClass());

                }
            }

            System.out.println("preprocessedStr = " + preprocessedStr);
            String methodDescriptor = getMethodDescriptor(
                    CallSite.class,
                    MethodHandles.Lookup.class,
                    String.class,
                    MethodType.class,
                    String.class,
                    Object[].class
            );
            String methodName = "makeConcatWithConstants";
            //bootstrap link to callSite factory
            Handle bootstrap = new Handle(
                    Opcodes.H_INVOKESTATIC,
                    Type.getInternalName(StringConcatFactory.class),
                    methodName,
                    methodDescriptor,
                    false
            );

            // takes preprocessed string and consume operands on the stack
            // return the result string
            String concatStrMethodDescriptor = getMethodDescriptor(
                    String.class,
                    argumentClass.toArray(new Class[]{}));

            Object[] arguments = {preprocessedStr.toString()};
            methodVisitor.visitInvokeDynamicInsn(
                    methodName,
                    concatStrMethodDescriptor, // take arguments, then return result
                    bootstrap,
                    arguments // arguments push into bootstrap
            );
            //if (type instanceof BaseType) {
            //    System.out.println("ctx.getText() = " + ctx.getText());
            //    System.out.println("each.getText() = " + each.getText());
            //    System.out.println("((BaseType) type).isConstant() = " + ((BaseType) type).isConstant());
            //    System.out.println("type = " + type);
            //    if (((BaseType) type).isConstant()) involvesConstant = true;
            //}
            //
            //System.out.println("ctx.getText() = " + ctx.getText());
            //System.out.println("write type = " + type);
            //
            //System.out.println("write type.getDescriptorClass() = " + type.getDescriptorClass());
            //InstructionHelper.invokeVirtual(StringBuilder.class,"append",type);
        }

        // finally call the print function
        //Class<?> printArg = ctx.writeParameters() == null ? Void.class : String.class;
        String printMethodName = ctx.WRITELN() != null ? "println" : "print";
        if (ctx.writeParameters() != null) {
            InstructionHelper.invokeVirtual(PrintStream.class, printMethodName, String.class);
        } else {
            InstructionHelper.invokeVirtual(PrintStream.class, printMethodName);
        }
        return null;
    }

    @Override
    public TypeDescriptor visitReadProcedureStatement(PascalParser.ReadProcedureStatementContext ctx) {
        List<PascalParser.InputValueContext> inputValueContexts = ctx.readParameters().inputValue();
        invoke(Instruction.CREATE_SCANNER);
        invoke(Instruction.STORE_REFERENCE);
        String scanner = "__scanner" + new Random().nextInt(999);
        putReferenceTypeIntoLocals(scanner);

        System.out.println("ctx.getText() = " + ctx.getText());
        System.out.println("ctx.readParameters().getText() = " + ctx.readParameters().getText());

        System.out.println("ctx.readParameters().inputValue().size() = " + inputValueContexts.size());

        // read scanner content into variable
        inputValueContexts.forEach(each -> {
            //load scanner (LoadReference - ALOAD)
            int variableSlot = getVariableSlotNum(scanner);
            System.out.println("scanner variableSlot = " + variableSlot);
            LoadStoreHelper.loadReference(variableSlot);

            String fieldName = each.getText();
            TypeDescriptor inputType = visit(each);
            System.out.println("fieldName = " + fieldName);
            System.out.println("inputType = " + inputType);
            //if (inputType instanceof IntegerBaseType) {
            //    InstructionHelper.invokeVirtual(Scanner.class, "nextInt");
            // else scanner.nextLine() or next()
            String methodName = ctx.READ() != null ? "next" : "nextLine";
            InstructionHelper.invokeVirtual(Scanner.class, methodName);
            if (inputType instanceof FloatBaseType) {
                // parse string to double
                InstructionHelper.invokeStatic(Double.class,
                        "parseDouble", false, String.class);
            }
            if (inputType instanceof IntegerBaseType) {
                InstructionHelper.invokeStatic(Integer.class, "parseInt", false, String.class);
            }
            if (inputType instanceof Character) {
                // get the char at pos 0, ignoring others
                methodVisitor.visitInsn(Opcodes.ICONST_0);
                InstructionHelper.invokeVirtual(String.class, "charAt", int.class);
                //methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "charAt", "(I)C", false);
            }
            InstructionHelper.putStatic(className, fieldName, inputType);
        });
        return null;
    }

    @Override
    public TypeDescriptor visitInputValue(PascalParser.InputValueContext ctx) {
        System.out.println("intput value ctx.variable().getText() = " + ctx.variable().getText());
        return super.visitInputValue(ctx);
    }

    /**
     * functionDesignator
     * : identifier LPAREN parameterList RPAREN
     * ;
     *
     * @param ctx
     * @return
     */
    @Override
    public TypeDescriptor visitFunctionDesignator(PascalParser.FunctionDesignatorContext ctx) {
        String functionId = ctx.identifier().getText().toLowerCase();
        System.out.println("FunctionDesignator ctx.getText() = " + ctx.getText());
        System.out.println("functionId ctx.getText() = " + functionId);

        // call function
        // push operands to be consumed onto stack
        // then call defined static function
        List<PascalParser.ActualParameterContext> actualParameterContexts = ctx.parameterList().actualParameter();
        // generate corresponding bytecode
        // literal - ldc
        // static fields - getstatic
        actualParameterContexts.forEach(this::visit);

        TypeDescriptor func = retrieve(functionId, ctx);
        List<TypeDescriptor> formalParams = null;
        TypeDescriptor resultType = null;
        if (func instanceof Function) {
            formalParams = ((Function) func).getFormalParams();
            resultType = ((Function) func).getResultType();
        }
        Class<?>[] funcArgumentsClass = new Class<?>[formalParams.size()];
        int i = 0;
        for (TypeDescriptor each : formalParams) {
            if (each instanceof FormalParam) {
                TypeDescriptor hostType = ((FormalParam) each).getHostType();
                Class<?> descriptorClass = hostType.getDescriptorClass();
                funcArgumentsClass[i++] = descriptorClass;
            }
        }
        // function must not return void though
        Class<?> resultTypeDescriptorClass = resultType != null ? resultType.getDescriptorClass() : void.class;
        String funcDescriptor = getMethodDescriptor(resultTypeDescriptorClass, funcArgumentsClass);
        System.out.println("funcDescriptor = " + funcDescriptor);

        InstructionHelper.invokeStatic(className, functionId, funcDescriptor, false);

        tableManager.displayAllTablesCurrentScope();
        return resultType;
    }

    @Override
    public TypeDescriptor visitProcedureStatement(PascalParser.ProcedureStatementContext ctx) {
        System.out.println("procedure statement ctx.getText() = " + ctx.getText());
        // if trivial procedure statement
        if (ctx.identifier() != null) {
            String procedureId = ctx.identifier().getText().toLowerCase();
            System.out.println("procedureId ctx.getText() = " + ctx.getText());

            // call procedure
            // push operands to be consumed onto stack
            // then call defined static function
            if (ctx.parameterList() != null) {
                List<PascalParser.ActualParameterContext> actualParameterContexts = ctx.parameterList().actualParameter();
                // generate corresponding bytecode
                // literal - ldc
                // static fields - getstatic
                actualParameterContexts.forEach(this::visit);
            }

            TypeDescriptor proc = retrieve(procedureId, ctx);
            List<TypeDescriptor> formalParams = null;
            if (proc instanceof Procedure) {
                formalParams = ((Procedure) proc).getFormalParams();
            }
            Class<?>[] procArgumentsClass = new Class<?>[formalParams.size()];
            int i = 0;
            for (TypeDescriptor each : formalParams) {
                if (each instanceof FormalParam) {
                    TypeDescriptor hostType = ((FormalParam) each).getHostType();
                    Class<?> descriptorClass = hostType.getDescriptorClass();
                    procArgumentsClass[i++] = descriptorClass;
                }
            }
            String procDescriptor = getMethodDescriptor(void.class, procArgumentsClass);
            System.out.println("procDescriptor = " + procDescriptor);
            InstructionHelper.invokeStatic(className, procedureId, procDescriptor, false);
            //methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC,
            //        className,
            //        procedureId, procDescriptor,
            //        false);
            tableManager.displayAllTablesCurrentScope();
        } else {
            if (ctx.readProcedureStatement() != null) {
                visit(ctx.readProcedureStatement());
            } else {
                visit(ctx.writeProcedureStatement());
            }
        }
        return null;
    }

    /**
     * ! Return constant real/int literal
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
                //methodVisitor.visitLdcInsn(Integer.valueOf(String.valueOf(ctx.NUM_INT())));
                Integer32 integer32 = DefaultIntegerType.of(String.valueOf(ctx.NUM_INT()), true);
                InstructionHelper.loadIntOrReal(integer32);
                return integer32;
            case PascalParser.NUM_REAL:
                Real real = DefaultFloatType.of(String.valueOf(ctx.NUM_REAL()), true);
                InstructionHelper.loadIntOrReal(real);
                //methodVisitor.visitLdcInsn(Float.valueOf(String.valueOf(ctx.NUM_INT())));
                return real;
        }
        return null;
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
        return null;
    }

    @Override
    public TypeDescriptor visitString(PascalParser.StringContext ctx) {
        System.out.println("Load String " + ctx.getText());
        String text = ctx.getText().replace("'", "");
        // if char
        if (text.length() == 1) {
            char[] chars = text.toCharArray();
            int charValue = (int) chars[0];
            methodVisitor.visitLdcInsn(Integer.valueOf(charValue));
            return new Character(chars[0], true);
        }

        if (loadConst) methodVisitor.visitLdcInsn(text);
        loadConst = true; //reset
        return new StringLiteral(text, true);
    }

    @Override
    public TypeDescriptor visitFactorBool(PascalParser.FactorBoolContext ctx) {
        String flag = ctx.bool_().getText();
        if (StringUtils.containsIgnoreCase(flag, "f")) {
            // false
            if (loadConst) InstructionHelper.loadTrueOrFalse(false);
            loadConst = true;
            return new Boolean(false, true);
        }

        if (loadConst) InstructionHelper.loadTrueOrFalse(true);
        loadConst = true;
        return new Boolean(true, true);
    }
}
