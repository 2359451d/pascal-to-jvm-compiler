package type.nestedType.param;

import type.BaseType;
import type.TypeDescriptor;

import java.util.Set;

public class UnorderedParam extends BaseType {

    private Set<String> acceptableTypes;

    public UnorderedParam(Set<String> acceptableTypes) {
        this.acceptableTypes = acceptableTypes;
    }

    @Override
    public String toString() {
        return "UnorderedParam{" +
                "acceptableTypes=" + acceptableTypes +
                '}';
    }

    @Override
    public boolean equiv(TypeDescriptor type) {
        return false;
    }
}
