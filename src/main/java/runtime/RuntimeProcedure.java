package runtime;

import annotation.RuntimeAlias;

public class RuntimeProcedure extends RuntimeProcFuncBaseType{
    protected RuntimeProcedure() {
        // automatically set the name in terms of Class name (lower case) or @RuntimeAlias annotation
        RuntimeAlias annotation = this.getClass().getAnnotation(RuntimeAlias.class);
        this.name = annotation!=null ? annotation.value() : this.getClass().getSimpleName().toLowerCase();
    }
}
