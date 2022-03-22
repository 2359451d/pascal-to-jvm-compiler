package type.primitive.integer;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.objectweb.asm.Type;
import type.TypeDescriptor;
import type.primitive.Primitive;

public class IntegerBaseType extends Primitive<Long> {

    // default size - Integer32 (4B)
    public static final Integer MAX_VALUE = Integer.MAX_VALUE;
    public static final Integer MIN_VALUE = Integer.MIN_VALUE;

    // use Long type in case overflow/underflow
    //private Long value=null;

    public IntegerBaseType() {
        super("int");
        this.value = null;
    }

    public IntegerBaseType(String type) {
        super(type);
        this.value = null;
    }

    public IntegerBaseType(String type, Long value) {
        super(type, value);
    }

    public static IntegerBaseType copy(IntegerBaseType from) {
        IntegerBaseType copy = new IntegerBaseType();
        copy.setValue(from.getValue());
        return copy;
    }

    //public Long getValue() {
    //    return value;
    //}
    //
    //public void setValue(Long value) {
    //    this.value = value;
    //}

    //@Override
    //public String getDescriptor() {
    //    return Type.getDescriptor(int.class);
    //}


    @Override
    public Class<?> getDescriptorClass() {
        return int.class;
    }

    @Override
    public boolean equiv(TypeDescriptor type) {
        //if (! (type instanceof IntegerBaseType)) return false;
        //Long thatValue = ((IntegerBaseType) type).getValue();
        //if (this.value != null && thatValue != null) {
        //    return this.value.equals(thatValue);
        //}
        //return false;
        return type instanceof IntegerBaseType;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
