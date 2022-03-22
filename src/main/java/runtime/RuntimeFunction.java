package runtime;

import annotation.RuntimeAlias;
import type.TypeDescriptor;

public class RuntimeFunction extends RuntimeProcFuncBaseType{

    protected TypeDescriptor resultType;

    protected RuntimeFunction() {
        // automatically set the name in terms of Class name (lower case) or @RuntimeAlias annotation
        RuntimeAlias annotation = this.getClass().getAnnotation(RuntimeAlias.class);
        this.name = annotation!=null ? annotation.value() : this.getClass().getSimpleName().toLowerCase();
    }

    public TypeDescriptor getResultType() {
        return resultType;
    }
}
