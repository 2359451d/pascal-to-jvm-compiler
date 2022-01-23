package type.procOrFunc;

import type.TypeDescriptor;

import java.util.List;

public class Function extends ProcFuncBaseType {

    private TypeDescriptor resultType;

    /**
     * If formalParams.size()==0, then function has no formal params
     *
     * @param formalParams
     */
    public Function(List<TypeDescriptor> formalParams, TypeDescriptor resultType) {
        this.formalParams = formalParams;
        this.resultType = resultType;
    }

    public List<TypeDescriptor> getFormalParams() {
        return formalParams;
    }

    public TypeDescriptor getResultType() {
        return resultType;
    }

    /**
     * Compare whether two Function types are equal
     * Usage: compare when Function itself as parameters to Procedure/Function
     *
     * @param type
     * @return
     */
    @Override
    public boolean equiv(TypeDescriptor type) {
        super.equiv(type);
        if (!(type instanceof Function)) return false;
        Function that = (Function) type;
        return this.resultType == that.getResultType();
    }

}
