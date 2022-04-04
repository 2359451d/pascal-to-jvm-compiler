package runtime;

import type.BaseType;
import type.TypeDescriptor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RuntimeProcFuncBaseType extends BaseType {

    // procedure/function identifier
    protected String name;

    // overloaded possibility, storing all the signatures in the set
    // ! NOTE: non-args procedures/functions set up empty Set rather than null
    // ! for procedures/functions where the signature cannot be described, set this field to null
    protected Set<List<Class<? extends TypeDescriptor>>> formalParamsMap;

    public Set<List<Class<? extends TypeDescriptor>>> getFormalParamsMap() {
        return formalParamsMap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFormalParamsMap(Set<List<Class<? extends TypeDescriptor>>> formalParamsMap) {
        this.formalParamsMap = formalParamsMap;
    }

    @Override
    public boolean equiv(TypeDescriptor type) {
        if (!(type instanceof RuntimeProcFuncBaseType)) return false;
        RuntimeProcFuncBaseType that = (RuntimeProcFuncBaseType) type;
        return this.formalParamsMap.equals(that.formalParamsMap);
    }

    public boolean checkSignature(List<TypeDescriptor> actualParameters) {
        ArrayList<Class<? extends TypeDescriptor>> _acutalParameters = new ArrayList<>();
        actualParameters.forEach(each->{
            _acutalParameters.add(each.getClass());
        });
        return formalParamsMap.contains(_acutalParameters);
    }

}
