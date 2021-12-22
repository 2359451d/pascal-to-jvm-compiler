package util;

import type.SymbolTable;
import type.Type;

import java.util.Arrays;

public class BuiltInUtils {

    public static void fillTable(SymbolTable<Type> symbolTable) {
        Arrays.stream(BuiltIn.values()).forEach(each ->
                symbolTable.put(each.getId(), each.getType())
        );
        symbolTable.displayCurrentScope();
    }
}
