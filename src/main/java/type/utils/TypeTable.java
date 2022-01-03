package type.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Helper class for type declaration
 * Each entry represents a defined type
 * @param <T>
 */
public class TypeTable<T> implements Table<String, T>{

    // Mapping: typeName -> Type
    private Map<String, T> table;

    public TypeTable(Map<String, T> table) {
        this.table = table;
    }

    public TypeTable() {
        this.table = new HashMap<>();
    }

    public boolean put(String typeName, T type) {
        if (!table.containsKey(typeName)) {
            table.put(typeName, type);
            return true;
        }
        return false;
    }

    public T get(String typeName) {
        return table.get(typeName);
    }

    /**
     * Print out all the existing new types
     */
    public void showAllTheTypes() {
        System.out.println("Content of the Type Table:");
        table.forEach((k,v)->{
            System.out.println(String.format("- typeName: [%s] -> type: [%s]",
                    k,v));
        });
    }

}
