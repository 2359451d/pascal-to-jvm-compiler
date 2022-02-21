package type.primitive.floating;

import org.objectweb.asm.Type;
import type.TypeDescriptor;
import type.primitive.Primitive;
import type.primitive.integer.IntegerBaseType;

public class FloatBaseType extends Primitive<Double> {

    public static final Double MAX_VALUE = Double.MAX_VALUE;
    public static final Double MIN_VALUE = Double.MIN_VALUE;

    //protected Float value;

    public FloatBaseType() {
        super("real");
    }

    public FloatBaseType(String type) {
        super(type);
        this.value = 0.0D; // default value
    }

    public FloatBaseType(String type, double value) {
        super(type);
        this.value = value;
    }

    //public Float getValue() {
    //    return value;
    //}
    //
    //public void setValue(Float value) {
    //    this.value = value;
    //}

    //@Override
    //public String getDescriptor() {
    //    return Type.getDescriptor(float.class);
    //}

    @Override
    public Class<?> getDescriptorClass() {
        return double.class;
    }

    @Override
    public boolean equiv(TypeDescriptor type) {
        return type instanceof FloatBaseType || type instanceof IntegerBaseType;
    }
}
