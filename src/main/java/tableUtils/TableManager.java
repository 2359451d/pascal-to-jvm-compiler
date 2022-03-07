package tableUtils;

import ast.visitor.PascalParser;
import ch.qos.logback.classic.Logger;
import org.antlr.v4.runtime.ParserRuleContext;
import type.TypeDescriptor;
import utils.log.GlobalLogger;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Utils class to manage all the initialised tables of the project
 */
public class TableManager<K, T> {

    /**
     * Singleton instance for external access
     */
    private static final TableManager<Object, TypeDescriptor> instance = new TableManager<>();

    /**
     * Container contains all the initialised tables with corresponding usage context
     */
    private Map<Class<? extends ParserRuleContext>, Table<K, T>> container = new LinkedHashMap<>();

    private TableManager() { }

    /**
     * Getter for external access
     *
     * @param <K>
     * @param <T>
     * @return
     */
    public static <K, T> TableManager<K, T> getInstance() {
        return (TableManager<K, T>) instance;
    }

    /**
     * Initialise a table safely,
     * If
     //* @param tableClass
     //* @return
     */
    //public Table<K, T> createTableSafely(String tableName, Class<?> tableClass, K key, T value) {
    //    // if table with specific class does not exist in the container
    //    // create a new one, and return it back
    //    Table<K, T> table = selectTableByClass((Class<? extends Table<K, T>>) tableClass);
    //    if (table ==null) {
    //        try {
    //            Type genericSuperclass = tableClass.getGenericSuperclass();
    //            Type[] actualTypeArguments = ((ParameterizedType) genericSuperclass).getActualTypeArguments();
    //            System.out.println("actualTypeArguments = " + actualTypeArguments);
    //            Table<K,T> _table = (Table<K, T>) tableClass.getDeclaredConstructor(String.class, Class.class).
    //                    newInstance(tableName, tableClass);
    //            return _table;
    //        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
    //            e.printStackTrace();
    //        }
    //    }
    //
    //    return table;
    //}

    public boolean addTable(Class<? extends ParserRuleContext> ctx, Table<K, T> table) {
        // exists duplicate key
        System.out.println("container before addTable = " + container);
        System.out.println("ctx = " + ctx);
        if (container.containsKey(ctx)) return false;

        container.put(ctx, table);
        GlobalLogger.info("New Table is initialised {} ");
        System.out.println("New Table Is Initialised");
        return true;
    }

    public Table<K, T> selectTable(Class<? extends ParserRuleContext> ctx) {
        // select the symbol table by excluded pattern
        if ((ctx != TypeTable.context)) ctx = PascalParser.ProgramContext.class;
        return container.get(ctx);//default return symbol table
    }

    @Deprecated
    public Table<K, T> selectTableByClass(Class<? extends Table<K, T>> tableClass) {
        Table<K, T> result = null;
        for (Map.Entry<Class<? extends ParserRuleContext>, Table<K, T>> entry : container.entrySet()) {
            Table<K, T> table = entry.getValue();
            if (table.getClass() == tableClass) {
                result = table;
                break;
            }
        }
        return result;
    }

    public Map<Class<? extends ParserRuleContext>, Table<K, T>> selectAllTablesExcludedToClass(Class<? extends ParserRuleContext> ctx) {
        if ((ctx != TypeTable.context)) ctx = PascalParser.ProgramContext.class;
        Class<? extends ParserRuleContext> finalCtx = ctx;
        return container.entrySet().stream().filter((e) -> {
            return e.getKey() != finalCtx;
        }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public boolean removeTable(Class<? extends ParserRuleContext> ctx) {
        if (!container.containsKey(ctx)) return false;

        container.remove(ctx);
        return true;
    }

    public boolean containsKey(Class<? extends ParserRuleContext> ctx) {
        return container.containsKey(ctx);
    }

    public void allTablesEnterNewScope() {
        //container.forEach(Table::enterLocalScope);
        container.forEach((k, v) -> v.enterLocalScope());
    }

    public void allTablesExitNewScope() {
        //container.forEach(Table::exitLocalScope);
        container.forEach((k, v) -> v.exitLocalScope());
    }

    public void displayAllTablesCurrentScope() {
        //container.forEach(Table::displayCurrentScope);
        container.forEach((k, v) -> v.displayCurrentScope());
    }

    public void showAllTables() {
        System.out.println(container);
    }

    public void clearAllTables() {
        container.clear();
    }

}
