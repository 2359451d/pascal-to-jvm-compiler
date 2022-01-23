package type.procOrFunc;

import type.TypeDescriptor;
import type.procOrFunc.ProcFuncBaseType;

import java.util.ArrayList;
import java.util.List;

public class Procedure extends ProcFuncBaseType {

    public Procedure() {
        this.formalParams = new ArrayList<>();
    }

    /**
     * If formalParams.size()==0, then proc has no formal params
     *
     * @param formalParams
     */
    public Procedure(List<TypeDescriptor> formalParams) {
        this.formalParams = formalParams;
    }

    /**
     * Compare whether two Procedure types are equal
     * Usage: compare when Procedure itself as parameters to Procedure/Function
     *
     * @param type
     * @return
     */
    @Override
    public boolean equiv(TypeDescriptor type) {
        return super.equiv(type);
    }


}
