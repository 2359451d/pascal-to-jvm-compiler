package instruction;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import type.StringLiteral;
import type.TypeDescriptor;
import type.primitive.Boolean;
import type.primitive.Character;
import type.primitive.floating.FloatBaseType;
import type.primitive.integer.IntegerBaseType;

public class LoadStoreHelper extends AbstractHelper {

    public static void loadReference(MethodVisitor mv, int slotNum) {
        mv.visitVarInsn(Opcodes.ALOAD, slotNum);
    }

    public static void loadReference(int slotNum) {
        loadReference(defaultMethodVisitor,slotNum);
    }


    public static void loadIntOrFloatLocal(MethodVisitor mv, TypeDescriptor type, int slotNumber) {
        System.out.println("loadIntOrFloatLocal type = " + type);
        if (type instanceof IntegerBaseType || type instanceof Boolean
                || type instanceof Character) {
            mv.visitVarInsn(Opcodes.ILOAD, slotNumber);
        }
        if (type instanceof FloatBaseType) {
            mv.visitVarInsn(Opcodes.DLOAD, slotNumber);
        }
        if (type instanceof StringLiteral) {
            loadReference(slotNumber);
        }
    }

    public static void loadIntOrFloatLocal(TypeDescriptor type, int slotNumber) {
        loadIntOrFloatLocal(updateDefaultMethodVisitor(),type,slotNumber);
    }

    //TODO: store Address, takes 1B
    public static void storeReference() {

    }

    /**
     * int ISTORE = 54;
     *     int LSTORE = 55;
     *     int FSTORE = 56;
     *     int DSTORE = 57;
     * @param mv
     * @param type
     */
    public static void storePrimitive(MethodVisitor mv, TypeDescriptor type, int slotNum) {
        int opcode = Opcodes.ISTORE; // int, bool, char
        if (type instanceof FloatBaseType) {
            opcode = Opcodes.DSTORE;
        }
        mv.visitVarInsn(opcode, slotNum);
    }

    public static void storePrimitive(TypeDescriptor type, int slotNum) {
        storePrimitive(defaultMethodVisitor,type,slotNum);
    }



}
