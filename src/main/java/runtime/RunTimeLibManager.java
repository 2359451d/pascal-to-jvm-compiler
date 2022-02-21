package runtime;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import type.TypeDescriptor;
import tableUtils.Table;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

public class RunTimeLibManager {

    public static RunTimeLibManager instance = new RunTimeLibManager();

    //public static Map<String, TypeDescriptor> lib;

    //private RunTimeLibManager() {
    //    lib = new HashMap<>();
    //}

    public static RunTimeLibManager getInstance() {
        return instance;
    }

    /**
     * Exploit reflection mechanism to set up the runtime procedures/functions
     * @param table
     */
    public static void fillTable(Table<Object,TypeDescriptor> table) {
        Reflections reflections = new Reflections("runtime", new SubTypesScanner(false));
        Set<Class<? extends RuntimeProcFuncBaseType>> allClasses =
                reflections.getSubTypesOf(RuntimeProcFuncBaseType.class);

        System.out.println("allClasses = " + allClasses);
        allClasses.forEach(each->{
            try {
                Class<?> superclass = each.getSuperclass();
                // only put runtime procedures/functions class which extends RuntimeProcedure/RuntimeFunction class
                if (superclass == RuntimeProcedure.class) {
                    RuntimeProcFuncBaseType runtimeProcFuncBaseType = each.getDeclaredConstructor().newInstance();
                    table.put(runtimeProcFuncBaseType.getName(), runtimeProcFuncBaseType);
                }
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        });

    }

    //public static void fillTable(SymbolTable<Object, TypeDescriptor> symbolTable) {
    //    init();
    //    lib.forEach(symbolTable::put);
    //    symbolTable.displayCurrentScope();
    //}

}
