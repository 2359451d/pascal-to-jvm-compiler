package type.primitive;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import type.BaseType;
import type.TypeDescriptor;

/**
 * Primitive/Standard types of Pascal
 * Cover: Integer, FloatBaseType, Character, Boolean
 */
public class Primitive<T> extends BaseType {

    protected String type;

    protected T value;

    public Primitive(String type) {
        this.type = type;
    }

    public Primitive(String type, T value) {
        this.type = type;
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public boolean equiv(TypeDescriptor type) {
        if (!(type instanceof Primitive)) return false;
        Primitive<T> otherType = (Primitive<T>) type;

        return (this.type).equals(otherType.type);
    }

    @Override
    public String toString() {
        //include isConstant field which is excluded in BaseType class
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
