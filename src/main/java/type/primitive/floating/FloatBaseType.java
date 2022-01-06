package type.primitive.floating;

import type.TypeDescriptor;
import type.primitive.Primitive;
import type.primitive.integer.IntegerBaseType;

public class FloatBaseType extends Primitive {

    public static final Double MAX_VALUE = Double.MAX_VALUE;
    public static final Double MIN_VALUE = Double.MIN_VALUE;

    public FloatBaseType() {
        super("real");
    }

    public FloatBaseType(String type) {
        super(type);
    }

    @Override
    public boolean equiv(TypeDescriptor type) {
        return type instanceof FloatBaseType || type instanceof IntegerBaseType;
    }
}
