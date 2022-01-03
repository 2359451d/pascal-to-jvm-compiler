package util;

import type.utils.SymbolTable;
import type.TypeDescriptor;

import java.util.Arrays;

public class BuiltInUtils {

    public static void fillTable(SymbolTable<TypeDescriptor> symbolTable) {
        Arrays.stream(BuiltIn.values()).forEach(each ->
                symbolTable.put(each.getId(), each.getType())
        );
        symbolTable.displayCurrentScope();
    }
}
