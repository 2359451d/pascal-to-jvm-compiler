package type.error;

import type.Type;
import type.TypeDescriptor;

public class InvalidConstantType extends Type {
    @Override
    public boolean equiv(TypeDescriptor that) {
        if (!(that instanceof InvalidConstantType)) return false;
        InvalidConstantType otherType = (InvalidConstantType) that;
        return (this).equals(otherType);
    }

    @Override
    public String toString() {
        return "Invalid constant type";
    }
}
