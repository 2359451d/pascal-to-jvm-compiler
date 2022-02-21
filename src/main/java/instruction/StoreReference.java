package instruction;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import tableUtils.Table;

public class StoreReference extends Instruction {

    //@Override
    //public void invoke(MethodVisitor methodVisitor, Table<K, T> localVariableTable) {
    //    localVariableTable.put()
    //    if (localVariableTable instanceof LocalsTable) {
    //        int length = ((LocalsTable<String, LocalVariableInformation>) localVariableTable).length();
        //methodVisitor.visitVarInsn(Opcodes.ASTORE, localVariableTable.sizeOfCurrentScope());
        //
        //}
    //}

    @Override
    public <K, T> void invoke(MethodVisitor methodVisitor, Table<K, T> localVariableTable) {
        methodVisitor.visitVarInsn(Opcodes.ASTORE, localVariableTable.sizeOfCurrentScope());
    }
}
