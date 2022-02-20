package instruction;

import org.objectweb.asm.MethodVisitor;
import tableUtils.Table;

public abstract class Instruction {

    //public static final Instruction LOAD_REFERENCE = new LoadReference();
    public static final Instruction STORE_REFERENCE = new StoreReference();
    public static final Instruction CREATE_STRING_BUILDER = new CreateStringBuilder();
    public static final Instruction CREATE_SCANNER  = new CreateScanner();

    public abstract <K,T> void invoke(MethodVisitor methodVisitor, Table<K, T> localVariableTable);
}
