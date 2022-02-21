package example;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class BytecodeGenerator {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException, IOException {
        // classwriter to output the example which extends Object class
        ClassWriter cw = new ClassWriter(0);
        /*
         * 0 - JDK
         * 1 - access modifier
         * class name
         * signature of the class, if no extension nor interface it could be empty
         * the superclass name of current class, if superclass is Object then could be empty
         * Interface name which could be empty
         */
        cw.visit(Opcodes.V11, Opcodes.ACC_PUBLIC, "Example", null, "java/lang/Object", null);

        /*
         * MethodWriter used to generate default constructors
         * access modifier
         * method name
         * descriptor symbols
         * method signature (when there is no class property involved in args, return types and exceptions it could be empty)
         * exception name
         */
        MethodVisitor mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
        // push this
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        // execute superclass constructor
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V");
        mv.visitInsn(Opcodes.RETURN);
        // at most use 1 stack, 1 local var
        mv.visitMaxs(1, 1);
        mv.visitEnd();

        // Main method writer
        mv = cw.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
        // get static fields
        mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        // push string literals
        mv.visitLdcInsn("Hello Worldss!");
        // execute PrintStream.println method
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V");
        //mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        //// push string literals
        //mv.visitLdcInsn("Hello World!");
        //// execute PrintStream.println method
        //mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V");


        mv.visitInsn(Opcodes.RETURN);
        // use 2 heaps, 2 locals
        mv.visitMaxs(2, 2);
        mv.visitEnd();
        cw.visitEnd();
        // generate the bytecode
        byte[] code = cw.toByteArray();
        FileOutputStream fos = new FileOutputStream("Example.class");
        fos.write(code);
        fos.close();
        //HelloWorld loader = new HelloWorld();
        //Class<?> exampleClass = loader.defineClass("Example", code, 0, code.length);
        //Method method = exampleClass.getMethods()[0];
        //method.invoke(null, new Object[]{null});
    }
}
