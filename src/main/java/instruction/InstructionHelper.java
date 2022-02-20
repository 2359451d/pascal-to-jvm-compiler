package instruction;

import jdk.internal.org.objectweb.asm.Type;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import type.TypeDescriptor;
import type.primitive.Boolean;
import type.primitive.Character;
import type.primitive.floating.FloatBaseType;
import type.primitive.integer.IntegerBaseType;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class InstructionHelper extends AbstractHelper {
    //static MethodVisitor defaultMethodVisitor = PascalEncoderVisitor.methodVisitor;

    public static void invokeVirtual(Class<?> owner, String methodName,
                                     Class<?>... arguments) {
        invokeVirtual(defaultMethodVisitor, owner, methodName, false, arguments);
    }

    public static void invokeVirtual(MethodVisitor mv, Class<?> owner, String methodName,
                                     Class<?>... arguments) {
        invokeVirtual(mv, owner, methodName, false, arguments);
    }

    /**
     * Invoke instance method; dispatch based on class
     *
     * @param mv          - MethodVisitor
     * @param owner       - From which class
     * @param methodName  - method name
     * @param isInterface - whether is a interface
     * @param arguments   - Class[]
     */
    public static void invokeVirtual(MethodVisitor mv, Class<?> owner, String methodName,
                                     boolean isInterface, Class<?>... arguments) {
        try {
            Method method = owner.getMethod(methodName, arguments);
            System.out.println("method = " + method);
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                    Type.getInternalName(owner),
                    methodName,
                    Type.getMethodDescriptor(method),
                    isInterface
            );
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public static void invokeVirtual(MethodVisitor mv, Class<?> owner, String methodName,
                                     boolean isInterface, TypeDescriptor type) {
       invokeVirtual(mv,owner,methodName,isInterface, type.getDescriptorClass());
    }

    public static void invokeVirtual(Class<?> owner, String methodName,
                                     boolean isInterface, TypeDescriptor type) {
        invokeVirtual(defaultMethodVisitor,owner,methodName,isInterface,type);
    }

    public static void invokeVirtual(Class<?> owner, String methodName,
                                     TypeDescriptor type) {
        invokeVirtual(owner,methodName,false,type);
    }



    /**
     * Get static field
     *
     * @param mv
     * @param owner
     * @param fieldName
     */
    public static void getStatic(MethodVisitor mv, Class<?> owner, String fieldName
    ) {
        try {
            Field field = owner.getField(fieldName);
            mv.visitFieldInsn(Opcodes.GETSTATIC,
                    Type.getInternalName(owner),
                    fieldName,
                    Type.getDescriptor(field.getType()));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public static void getStatic(Class<?> owner, String fieldName) {
        getStatic(defaultMethodVisitor, owner, fieldName);
    }

    public static void getStatic(MethodVisitor mv, String owner, String fieldName, TypeDescriptor type
    ) {

        mv.visitFieldInsn(Opcodes.GETSTATIC,
                owner,
                fieldName,
                type.getDescriptor());
    }

    public static void getStatic(String owner, String fieldName, TypeDescriptor type) {
        getStatic(defaultMethodVisitor, owner, fieldName, type);
    }

    public static void putStatic(MethodVisitor mv, String owner, String fieldName, TypeDescriptor type) {
        mv.visitFieldInsn(Opcodes.PUTSTATIC, owner, fieldName, type.getDescriptor());
    }

    public static void putStatic(String owner, String fieldName, TypeDescriptor type) {
        putStatic(defaultMethodVisitor, owner, fieldName, type);
    }

    public static void add(MethodVisitor mv, TypeDescriptor type) {
        /**
         *     int IADD = 96;
         *     int LADD = 97; (not implemented in ISO7185)
         *     int FADD = 98;
         *     int DADD = 99;(not implemented in ISO7185)
         */
        // default int add
        int opcode = Opcodes.IADD;
        if (type instanceof FloatBaseType) {
            opcode = Opcodes.FADD;
        }
        mv.visitInsn(opcode);
    }

    public static void add(TypeDescriptor type) {
        add(defaultMethodVisitor, type);
    }

    /**
     * int ISUB = 100;
     * int LSUB = 101;
     * int FSUB = 102;
     * int DSUB = 103;
     *
     * @param type
     */
    public static void sub(MethodVisitor mv, TypeDescriptor type) {
        int opcode = Opcodes.ISUB;
        if (type instanceof FloatBaseType) {
            opcode = Opcodes.FSUB;
        }
        mv.visitInsn(opcode);
    }

    public static void sub(TypeDescriptor typeDescriptor) {
        sub(defaultMethodVisitor, typeDescriptor);
    }

    public static void mul(MethodVisitor mv, TypeDescriptor type) {
        int opcode = Opcodes.IMUL;
        if (type instanceof FloatBaseType) {
            opcode = Opcodes.FMUL;
        }
        mv.visitInsn(opcode);
    }

    public static void mul(TypeDescriptor typeDescriptor) {
        mul(defaultMethodVisitor, typeDescriptor);
    }

    public static void realDiv(MethodVisitor mv) {
        int opcode = Opcodes.FDIV;
        mv.visitInsn(opcode);
    }

    public static void realDiv() {
        realDiv(defaultMethodVisitor);
    }

    public static void intDiv(MethodVisitor mv) {
        int opcode = Opcodes.IDIV;
        mv.visitInsn(opcode);
    }

    public static void intDiv() {
        intDiv(defaultMethodVisitor);
    }

    public static void intMod(MethodVisitor mv) {
        mv.visitInsn(Opcodes.IREM);
    }

    public static void intMod() {
        intMod(defaultMethodVisitor);
    }

    public static void intOrFloatNeg(MethodVisitor mv, TypeDescriptor type) {
        int opcode = Opcodes.INEG;
        if (type instanceof FloatBaseType) {
            opcode = Opcodes.FNEG;
        }
        mv.visitInsn(opcode);
    }

    public static void intOrFloatNeg(TypeDescriptor type) {
        intOrFloatNeg(defaultMethodVisitor,type);
    }

    /**
     * Load constant int, real literal
     * @param mv
     * @param type
     */
    public static void loadIntOrReal(MethodVisitor mv, TypeDescriptor type) {
        if (type instanceof FloatBaseType) {
            float value = ((FloatBaseType) type).getValue();
            if (value == 0.0F) {
                mv.visitInsn(Opcodes.FCONST_0);
            }else mv.visitLdcInsn(value);
            return;
        }
        if (type instanceof Boolean) {
            java.lang.Boolean value = ((Boolean) type).getValue();
            if (value == null || !value) {
                // load false (const 0)
                mv.visitInsn(Opcodes.ICONST_0);
            }else mv.visitInsn(Opcodes.ICONST_1);
            return;
        }

        if (type instanceof Character) {
            java.lang.Character value = ((Character) type).getValue();
            if (value == null) {
                mv.visitInsn(Opcodes.ICONST_0);
            } else mv.visitLdcInsn(Math.toIntExact(value));
            return;
        }

        Long intValue = ((IntegerBaseType) type).getValue();
        if (intValue == 0) {
            System.out.println("loadint mv = " + mv);
            mv.visitInsn(Opcodes.ICONST_0);
        } else mv.visitLdcInsn(Math.toIntExact(intValue));
    }


    public static void loadIntOrReal(TypeDescriptor type) {
        loadIntOrReal(defaultMethodVisitor,type);
    }

    public static void loadTrueOrFalse(MethodVisitor mv, boolean flag) {
        int opcode = Opcodes.ICONST_0;
        if (flag) {
            opcode = Opcodes.ICONST_1;
        }
        mv.visitInsn(opcode);
    }

    public static void loadTrueOrFalse(boolean flag) {
        loadTrueOrFalse(defaultMethodVisitor,flag);
    }

    public static void intLogicalOp(MethodVisitor mv, int opcode) {
        mv.visitInsn(opcode);
    }

    public static void intLogicalOp(int opcode) {
        intLogicalOp(defaultMethodVisitor,opcode);
    }


}
