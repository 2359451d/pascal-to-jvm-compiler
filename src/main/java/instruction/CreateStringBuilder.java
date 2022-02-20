package instruction;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import tableUtils.Table;

public class CreateStringBuilder extends Instruction {

    //@Override
    //public void invoke(MethodVisitor methodVisitor, Table<String, LocalVariableInformation> localVariableTable) {
    //}

    @Override
    public <K, T> void invoke(MethodVisitor methodVisitor, Table<K, T> localVariableTable) {
        methodVisitor.visitTypeInsn(Opcodes.NEW, "java/lang/StringBuilder");
        methodVisitor.visitInsn(Opcodes.DUP);
        methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
    }
}
