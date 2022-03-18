package tableUtils;

import ast.visitor.PascalParser;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.Deque;
import java.util.LinkedList;

public class LocalsTable<K, T extends LocalVariableInformation> extends Table<K, T> {
    /**
     * Id - startPos(slot), length
     */
    //private Map<K, ? super LocalVariableInformation> table;

    private Deque<Integer> lengthStack;
    public static Class<? extends ParserRuleContext> context = PascalParser.VariableContext.class;

    public LocalsTable() {
        super("local variable table", context);
        this.lengthStack = new LinkedList<>();
        this.lengthStack.push(0);//global scope, initialise 0
        //this.table = new LinkedHashMap<>();
    }

    @Override
    public void enterLocalScope() {
        super.enterLocalScope();
        this.lengthStack.addLast(0);
    }

    @Override
    public void exitLocalScope() {
        super.exitLocalScope();
        this.lengthStack.removeLast();
    }

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
        int start = this.length();
        updateLength(length); //update length as new local put
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
        return this.lengthStack.getLast();
    }

    private void updateLength(int increment) {
        Integer base = this.lengthStack.removeLast();
        this.lengthStack.addLast(base+increment);
    }

    //public Map<K, ? super LocalVariableInformation> getTable() {
    //    return table;
    //}
}
