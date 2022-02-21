package utils;

import tableUtils.SymbolTable;
import type.TypeDescriptor;

import java.util.Arrays;

public class BuiltInUtils {

    public static void fillTable(SymbolTable<Object,TypeDescriptor> symbolTable) {
        Arrays.stream(BuiltIn.values()).forEach(each ->
                symbolTable.put(each.getId(), each.getType())
        );
        symbolTable.displayCurrentScope();
    }
}
