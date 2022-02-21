package instruction;

import ast.visitor.impl.PascalEncoderVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import tableUtils.LocalVariableInformation;
import tableUtils.LocalsTable;
import tableUtils.Table;

@Deprecated
public class LoadReference {
    // default load "this" reference
    //@Override
    //public <K, T> void invoke(MethodVisitor methodVisitor, Table<K, T> localVariableTable) {
    //    methodVisitor.visitVarInsn(Opcodes.ALOAD, localVariableTable.get("id").getSlotNum());
    //}
    //
    //public void invoke(MethodVisitor methodVisitor, Table<String, LocalVariableInformation> localVariableTable, String id) {
    //    methodVisitor.visitVarInsn(Opcodes.ALOAD, localVariableTable.get(id).getSlotNum());
    //}
}
