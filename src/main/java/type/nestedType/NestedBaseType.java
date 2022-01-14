package type.nestedType;

import type.BaseType;
import type.TypeDescriptor;

/**
 * Represents a nested type
 * Typically contains the host type to be checked later on
 */
public class NestedBaseType extends BaseType {

    private TypeDescriptor hostType;

    public void setHostType(TypeDescriptor hostType) {
        this.hostType = hostType;
    }

    public TypeDescriptor getHostType() {
        return hostType;
    }

    @Override
    public boolean equiv(TypeDescriptor type) {
        return type instanceof NestedBaseType;
    }
}
