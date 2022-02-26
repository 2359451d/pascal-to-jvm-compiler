package tableUtils;

import ast.visitor.PascalParser;
import org.antlr.v4.runtime.ParserRuleContext;

public class LocalsTable<K, T extends LocalVariableInformation> extends Table<K, T> {
    /**
     * Id - startPos(slot), length
     */
    //private Map<K, ? super LocalVariableInformation> table;

    private int length;
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

        int start = this.length();
        updateLength(length); //update length as new local put
        System.out.println("length after update = " + this.length);
        return put(id,start,length,isStatic);
    }

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

    /**
     * Total length of all the locals in the table
     * @return
     */
    public int length() {
        return length;
    }

    private void updateLength(int increment) {
        this.length += increment;
    }

    //public Map<K, ? super LocalVariableInformation> getTable() {
    //    return table;
    //}
}
