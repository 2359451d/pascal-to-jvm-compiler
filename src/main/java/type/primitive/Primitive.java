package type.primitive;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import type.BaseType;
import type.Type;
import type.TypeDescriptor;

/**
 * Primitive/Standard types of Pascal
 * Cover: Integer, FloatBaseType, Character, Boolean
 */
public class Primitive extends BaseType {

    private String type;

    public Primitive(String type) {
        this.type = type;
    }

    @Override
    public boolean equiv(TypeDescriptor type) {
        if (!(type instanceof Primitive)) return false;
        Primitive otherType = (Primitive) type;

        return (this.type).equals(otherType.type);
    }

    @Override
    public String toString() {
        //include isConstant field which is excluded in BaseType class
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
