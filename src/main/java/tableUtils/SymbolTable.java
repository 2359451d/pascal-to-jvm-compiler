package tableUtils;

import ast.visitor.PascalParser;
import org.antlr.v4.runtime.ParserRuleContext;
import type.TypeDescriptor;

public class SymbolTable<K, T extends TypeDescriptor> extends Table<K, T> {

    //private Deque<HashMap<String, A>> getScope_stack();
    public static Class<? extends ParserRuleContext> context = PascalParser.ProgramContext.class;

    public SymbolTable() {
        super("symbol table", context);
        //this.context = PascalParser.ProgramContext.class;
        //getScope_stack() = new LinkedList<>();
        //getScope_stack().addLast(new HashMap<>());
    }

    //public boolean put(String id, A attr) {
    //    // Add (id,attr) to this symbol table, either to the
    //    // local part (if enabled, choose the most recent one) or to the global part
    //    // (otherwise). Return true if id is unique.
    //    //HashMap<String, A> scope =
    //    //(current_scope != null ? current_scope : globals);
    //    // get the most recent stack frame
    //    HashMap<String, A> scope = getScope_stack().getLast();
    //    // if the identifier is not defined yet
    //    if (scope.get(id) == null) {
    //        scope.put(id, attr);
    //        return true;
    //    } else
    //        return false;
    //}
    //
    //public A get(String id) {
    //    // try to retrieve the identifier in current (local, most recent) scope
    //    if (!getScope_stack().isEmpty()) {
    //        HashMap<String, A> scope = getScope_stack().getLast();
    //        if (scope.containsKey(id)) {
    //            // retrieve the identifier in the most recent scope
    //            return scope.get(id);
    //        } else {
    //            // try to retrieve the id in previous declared scopes from a reverse order
    //            // (most recent first)
    //            Iterator<HashMap<String, A>> scopeStackDescendingIterator = getScope_stack().descendingIterator();
    //            scopeStackDescendingIterator.next(); // skip current scope
    //            while (scopeStackDescendingIterator.hasNext()) {
    //                System.out.printf("No declaration in current scope - [depth %d], try to retrieve from former scope\n", getScope_stack().size() - 1);
    //                scope = scopeStackDescendingIterator.next();
    //                if (scope.containsKey(id)) return scope.get(id);
    //            }
    //        }
    //    }
    //    System.out.println("Searching exhausted, no declaration found!");
    //    return null;
    //}
    //
    ///**
    // * ! Case sensitive
    // * @param id
    // * @return
    // */
    //@Override
    //public boolean contains(String id) {
    //    return this.get(id) != null;
    //}

    //public Map<String, T> getAllVarInCurrentScope() {
    //    return getScope_stack().getLast();
    //}

    //public void enterLocalScope() {
    //    // Enable the local part of this symbol table.
    //    // One new symbol table corresponds to one new scope
    //    System.out.println("Enter new local scope, last depth = " + (getScope_stack().size() - 1));
    //    getScope_stack().addLast(new HashMap<>());
    //}
    //
    //public void exitLocalScope() {
    //    // Discard all entries in current scope of this symbol table.
    //    // i.e. pop out the topmost stack frame
    //    // or no effect if no more local scopes defined.
    //    if (getScope_stack().size() > 1) getScope_stack().removeLast();
    //}

    //public void displayCurrentScope() {
    //    // Display all the symbols of current scope (most recent)
    //    int size = getScope_stack().size();
    //    String isGlobal = size == 1 ? "global" : "local";
    //    System.out.println("\n===========");
    //    System.out.printf("Symbols of current scope - %s - [depth %d] \n", isGlobal, size - 1);
    //    HashMap<String, T> currentScope = getScope_stack().getLast();
    //    currentScope.forEach((id, type) -> {
    //        System.out.println("id = " + id + " ,type = " + type);
    //    });
    //    System.out.println("===========\n");
    //}

    //public String displayScope(int depth) {
    //    // Display all the symbols of the specific scope (given depth starting from 0)
    //    Iterator<HashMap<String, T>> scopeStackIterator = getScope_stack().iterator();
    //    int count = 0;
    //    HashMap<String, T> scope = null;
    //
    //    while (scopeStackIterator.hasNext()) {
    //        scope = scopeStackIterator.next();
    //        if (count == depth) break;
    //        count++;
    //    }
    //    return "Symbols of the scope with depth " + depth + ": " + scope + "\n";
    //}
}
