package tableUtils;

import ast.visitor.PascalParser;
import org.antlr.v4.runtime.ParserRuleContext;
import type.TypeDescriptor;

/**
 * Helper class for type declaration
 * Each entry represents a defined type
 * @param <T>
 */
public class TypeTable<K, T extends TypeDescriptor> extends Table<K, T>{

    public static Class<? extends ParserRuleContext> context = PascalParser.TypeDefinitionContext.class;

    public TypeTable() {
        super("type table", context);
        //setContext(PascalParser.TypeDefinitionContext.class);
    }

    //// Mapping: typeName -> Type
    //private Map<String, T> table;
    //
    //public TypeTable(Map<String, T> table) {
    //    this.table = table;
    //}
    //
    //public TypeTable() {
    //    this.table = new HashMap<>();
    //}
    //
    //public boolean put(String typeName, T type) {
    //    if (!table.containsKey(typeName)) {
    //        table.put(typeName, type);
    //        return true;
    //    }
    //    return false;
    //}
    //
    //public T get(String typeName) {
    //    return table.get(typeName);
    //}
    //
    ///**
    // * Print out all the existing new types
    // */
    //public void showAllTheTypes() {
    //    System.out.println("Content of the Type Table:");
    //    table.forEach((k,v)->{
    //        System.out.println(String.format("- typeName: [%s] -> type: [%s]",
    //                k,v));
    //    });
    //}
    //
    //@Override
    //public boolean contains(String id) {
    //    return table.containsKey(id.toLowerCase());
    //}
}
