package runtime;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import type.TypeDescriptor;
import tableUtils.Table;
import utils.log.GlobalLogger;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

public class RuntimeLibManager {

    public static RuntimeLibManager instance = new RuntimeLibManager();

    public static RuntimeLibManager getInstance() {
        return instance;
    }

    /**
     * Exploit reflection mechanism to set up the runtime procedures/functions
     * @param table
     */
    public static void fillTable(Table<Object, TypeDescriptor> table) {
        Reflections reflections = new Reflections("runtime", new SubTypesScanner(false));
        Set<Class<? extends RuntimeProcFuncBaseType>> allClasses =
                reflections.getSubTypesOf(RuntimeProcFuncBaseType.class);

        GlobalLogger.debug("All the builtin classes to be scanned:\n{}",
                ()->allClasses);

        allClasses.forEach(each->{
            try {
                Class<?> superclass = each.getSuperclass();
                // only put runtime procedures/functions class which extends RuntimeProcedure/RuntimeFunction class
                if (superclass == RuntimeProcedure.class || superclass == RuntimeFunction.class) {
                    RuntimeProcFuncBaseType runtimeProcFuncBaseType = each.getDeclaredConstructor().newInstance();
                    table.put(runtimeProcFuncBaseType.getName(), runtimeProcFuncBaseType);
                }


            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        });

    }
}
