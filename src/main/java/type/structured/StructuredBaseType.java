package type.structured;

import type.BaseType;
import type.TypeDescriptor;

public class StructuredBaseType extends BaseType {

    /**
     * All the structured types is unpacked by default
     */
    private boolean isPacked = false;

    /**
     * Setter used to set the structured type to packed or unpacked
     * @param packed: true - is packed, false - is unpacked
     */
    public void setPacked(boolean packed) {
        isPacked = packed;
    }

    public boolean isPacked() {
        return isPacked;
    }

    @Override
    public boolean equiv(TypeDescriptor type) {
        return type instanceof StructuredBaseType;
    }
}
