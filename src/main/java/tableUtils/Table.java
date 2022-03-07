package tableUtils;

import ch.qos.logback.classic.Logger;
import org.antlr.v4.runtime.ParserRuleContext;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import utils.log.GlobalLogger;

import java.util.*;

/**
 * Generic Table class used for symbol, type tables  .etc
 * Contains operations to process symbols through different scopes
 * Mapping key value <K> to a type <T>
 *
 * @param <K> - represents the key
 * @param <T> - represents class implements TypeDescriptor class
 */
public abstract class Table<K, T> {

    /**
     * Access table manager singleton instance
     */
    protected TableManager<K, T> tableManager = TableManager.getInstance(); // quick reference

    /**
     * Scope stack, support nested scopes
     * Each scope holds entry of current scope
     */
    private Deque<Map<K, T>> scope_stack;

    /**
     * Table name
     */
    private String tableName = "unknown";

    /**
     * Table usage context
     * Default if == null, then select the SymbolTable instance
     */
    //private Class<? extends ParserRuleContext> context = ParserRuleContext.class;
    protected Class<? extends ParserRuleContext> context = null;

    /**
     * Initialise the table
     * ~~Note: Global scope would be initialised in predefine() once perform the contextual analysis~~
     */
    protected Table() {
        scope_stack = new LinkedList<>();
        scope_stack.addLast(new LinkedHashMap<>());

    }

    /**
     * Initialise the table & tableName
     *
     * @param tableName - table name
     */
    protected Table(String tableName) {
        this();
        this.tableName = tableName;
    }

    /**
     * Initialise the table & tableName & usage context
     *
     * @param tableName - table name
     * @param ctx       - usage context
     */
    protected Table(String tableName, Class<? extends ParserRuleContext> ctx) {
        this(tableName);
        this.context = ctx;
        // add a new table instance into the TableContainer
        tableManager.addTable(context, this);
        tableManager.showAllTables();
    }

    /**
     * Add (id,attr) to this symbol table, either to the
     * local part (if enabled, choose the most recent one) or to the global part
     * (Otherwise). Return true if id is unique.
     *
     * @param id
     * @param attr
     * @return
     */
    public boolean put(K id, T attr) {
        Map<K, T> scope = getScope_stack().getLast();
        // if the identifier is not defined yet
        if (scope.get(id) == null) {
            scope.put(id, attr);
            return true;
        } else
            return false;
    }

    /**
     * Get stored content in terms of identifier
     * Exhausted searching till identifier matched, returning corresponding content
     * Otherwise return null
     *
     * @param id
     * @return
     */
    public T get(K id) {
        // try to retrieve the identifier in current (local, most recent) scope
        if (!getScope_stack().isEmpty()) {
            Map<K, T> scope = getScope_stack().getLast();
            if (scope.containsKey(id)) {
                // retrieve the identifier in the most recent scope
                return scope.get(id);
            } else {
                // try to retrieve the id in previous declared scopes from a reverse order
                // (most recent first)
                Iterator<Map<K, T>> scopeStackDescendingIterator = getScope_stack().descendingIterator();
                scopeStackDescendingIterator.next(); // skip current scope
                while (scopeStackDescendingIterator.hasNext()) {
                    //System.out.printf("No declaration in current scope - [depth %d], try to retrieve from former scope\n", getScope_stack().size() - 1);
                    scope = scopeStackDescendingIterator.next();
                    if (scope.containsKey(id)) return scope.get(id);
                }
            }
        }
        GlobalLogger.debug("Searching exhausted, no declaration found!");
        return null;
    }

    /**
     * Check whether the specific key exists in the table
     *
     * @param id
     * @return
     */
    public boolean containsKey(K id) {
        return this.get(id) != null;
    }

    /**
     * Get all the mappings in the current scope
     *
     * @return
     */
    public Map<K, T> getAllVarInCurrentScope() {
        return scope_stack.getLast();
    }

    /**
     * Enter a new cope
     */
    public void enterLocalScope() {
        // One new table entry (a map) corresponds to one new scope
        GlobalLogger.debug("Enter new local scope, last depth = {}"
                , () -> getScope_stack().size() - 1);
        getScope_stack().addLast(new LinkedHashMap<>());
    }

    /**
     * Exit current scope, all the mappings of current scope would be clear up
     */
    public void exitLocalScope() {
        // Discard all entries in current scope of this symbol table.
        // i.e. pop out the topmost stack frame
        // or no effect if no more local scopes defined.
        if (getScope_stack().size() > 1) getScope_stack().removeLast();
    }

    /**
     * Display all the content of current scope
     */
    public void displayCurrentScope() {
        // Display all the symbols of current scope (most recent)
        int size = getScope_stack().size();
        // if size=1, then is at the global scope
        String isGlobal = size == 1 ? "global" : "local";

        GlobalLogger.debug("\n===========");
        GlobalLogger.debug("Table name: [{}] - Symbols of current scope - {} - [depth {}]",
                ()->tableName, ()->isGlobal, ()->size - 1);
        //System.out.printf("Table name: [%s] - Symbols of current scope - %s - [depth %d] \n",
        //        tableName, isGlobal, size - 1);
        Map<K, T> currentScope = getScope_stack().getLast();
        currentScope.forEach((id, type) -> {
            GlobalLogger.debug("id = {}, type = {}", ()->id, ()->type);
            //System.out.println("id = " + id + " ,type = " + type);
        });
        GlobalLogger.debug("===========\n");
    }

    public int sizeOfCurrentScope() {
        return scope_stack.getLast().size();
    }

    public Deque<Map<K, T>> getScope_stack() {
        return scope_stack;
    }

    public void setContext(Class<? extends ParserRuleContext> context) {
        this.context = context;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toStringExclude(this, "scope_stack");
    }
}
