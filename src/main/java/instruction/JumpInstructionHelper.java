package instruction;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;


public class JumpInstructionHelper extends AbstractHelper {

    public static void jumpInstruction(MethodVisitor mv, int opcode, Label label) {
        mv.visitJumpInsn(opcode,label);
    }

    public static void jumpInstruction(int opcode, Label label) {
        jumpInstruction(defaultMethodVisitor,opcode,label);
    }

    public static void gotoLabel(MethodVisitor mv, Label label) {
        mv.visitJumpInsn(Opcodes.GOTO,label);
    }

    public static void gotoLabel(Label label) {
        gotoLabel(defaultMethodVisitor,label);
    }
}
