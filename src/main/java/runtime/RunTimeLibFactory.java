package runtime;

// proc, func

import runtime.proc.Write;
import type.Type;
import type.SymbolTable;
import type.TypeDescriptor;

import java.util.HashMap;
import java.util.Map;

public class RunTimeLibFactory {

    public static RunTimeLibFactory instance = new RunTimeLibFactory();

    public static Map<String, Type> lib;

    private RunTimeLibFactory() {
        lib = new HashMap<>();
    }

    public static RunTimeLibFactory getInstance() {
        return instance;
    }

    private static void init() {
        new Write().addToFactory();
    }

    public static void fillTable(SymbolTable<TypeDescriptor> symbolTable) {
        init();
        lib.forEach(symbolTable::put);
        symbolTable.displayCurrentScope();
    }

}
