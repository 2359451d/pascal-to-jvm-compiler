package instruction;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import tableUtils.LocalVariableInformation;
import tableUtils.LocalsTable;
import tableUtils.Table;

public class CreateScanner extends Instruction {

    //@Override
    //public void invoke(MethodVisitor methodVisitor, Table<String, LocalVariableInformation> localVariableTable) {
    //}

    @Override
    public <K, T> void invoke(MethodVisitor methodVisitor, Table<K, T> localVariableTable) {
        methodVisitor.visitTypeInsn(Opcodes.NEW, "java/util/Scanner");
        methodVisitor.visitInsn(Opcodes.DUP);
        methodVisitor.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "in", "Ljava/io/InputStream;");
        methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/util/Scanner", "<init>", "(Ljava/io/InputStream;)V", false);
    }
}
