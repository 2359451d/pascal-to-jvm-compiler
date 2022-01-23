package type.structured;

import org.apache.commons.lang3.builder.ToStringBuilder;
import type.BaseType;
import type.TypeDescriptor;
import util.CustomToStringStyle;

public class StructuredBaseType extends BaseType {

    /**
     * All the structured types is unpacked by default
     */
    protected boolean isPacked = false;

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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, CustomToStringStyle.SHORT_PREFIX_MULTI_LINE_STYLE);
    }
}
