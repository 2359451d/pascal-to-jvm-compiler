package type.primitive.integer;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import type.TypeDescriptor;
import type.primitive.Primitive;

public class IntegerBaseType extends Primitive {

    // default size - Integer32 (4B)
    public static final Integer MAX_VALUE = Integer.MAX_VALUE;
    public static final Integer MIN_VALUE = Integer.MIN_VALUE;

    // use Long type in case overflow/underflow
    private Long value=null;

    public IntegerBaseType() {
        super("int");
    }

    public IntegerBaseType(String type) {
        super(type);
    }

    public static IntegerBaseType copy(IntegerBaseType from) {
        IntegerBaseType copy = new IntegerBaseType();
        copy.setValue(from.getValue());
        return copy;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    @Override
    public boolean equiv(TypeDescriptor type) {
        return type instanceof IntegerBaseType;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
