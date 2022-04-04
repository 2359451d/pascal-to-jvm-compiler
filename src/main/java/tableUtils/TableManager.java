package tableUtils;

import ast.visitor.PascalParser;
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

    public boolean addTable(Class<? extends ParserRuleContext> ctx, Table<K, T> table) {
        // exists duplicate key
        if (container.containsKey(ctx)) return false;
        container.put(ctx, table);
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
        GlobalLogger.debug("{}", ()->container);
    }

    public void resetContainer() {
        container.clear();
    }

    public void resetAllTables() {
        resetAndInitAllTables(false);
    }

    public void resetAndInitAllTables(boolean init) {
        container.forEach((k,v)->{
            v.resetTable();
            if (init) {
                v.initScope();
            }
        });
    }

    public void resetAndInitAllTables() {
        resetAndInitAllTables(true);
    }


}
