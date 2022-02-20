package tableUtils;

import ast.visitor.PascalParser;
import org.antlr.v4.runtime.ParserRuleContext;

public class LocalsTable<K, T extends LocalVariableInformation> extends Table<K, T> {
    /**
     * Id - startPos(slot), length
     */
    //private Map<K, ? super LocalVariableInformation> table;

    public static Class<? extends ParserRuleContext> context = PascalParser.VariableContext.class;

    public LocalsTable() {
        super("local variable table", context);
        //this.table = new LinkedHashMap<>();
    }

    //private boolean containsId(K id) {
    //    return table != null && table.containsKey(id);
    //}

    private int getSlot(K id) {
        if (this.get(id) != null) {
            LocalVariableInformation object = (LocalVariableInformation) get(id);
            return object.getSlotNum();
        }
        return -999;
    }

    private boolean isStatic(K id) {
        if (this.get(id) != null) {
            LocalVariableInformation object = (LocalVariableInformation) get(id);
            return this.containsKey(id) && object.isStatic();
        }
        return false;
    }

    public boolean put(K id, int slotNum, int length, boolean isStatic) {
        if (containsKey(id)) return false;
        super.put(id, (T) new LocalVariableInformation(slotNum, length, isStatic));
        return true;
    }

    public void put(K id, int slotNum, int length) {
        put(id, slotNum, length, false);
    }

    /**
     * b
     * Automatically calculate the start position
     * <p>
     * * @param id
     * * @param length
     */
    public boolean put(K id, int length, boolean isStatic) {
        System.out.println("current local table size "+ this.sizeOfCurrentScope());
        int start = this.sizeOfCurrentScope();
        return put(id,start,length,isStatic);
    }

    //private void put(K id, T localVariableInformation) {
    //    put(id,localVariableInformation.getSlotNum(),localVariableInformation.getLength(),
    //            localVariableInformation.isStatic());
    //}

    //private void put(String id, int length) {
    //    put(id,length,false);
    //}

    @Override
    public boolean put(K id, T attr) {
        // capture & preprocess value to be inserted
        if (attr != null) {
            LocalVariableInformation localVariableInformation = (LocalVariableInformation) attr;
            int slotNum = (localVariableInformation).getSlotNum();
            boolean isStatic = localVariableInformation.isStatic();
            int length = localVariableInformation.getLength();
            if (slotNum == -999) {
                // automatically calculate the slotNum
                return put(id, length, isStatic);
            }
        }
        return false;
    }

    //public int length() {
    //    return table.size();
    //}

    //public Map<K, ? super LocalVariableInformation> getTable() {
    //    return table;
    //}
}
