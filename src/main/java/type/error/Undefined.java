package type.error;

import type.Type;
import type.TypeDescriptor;

public class Undefined extends Type {
    @Override
    public boolean equiv(TypeDescriptor that) {
        if (!(that instanceof Undefined)) return false;
        Undefined otherType = (Undefined) that;

        return (this).equals(otherType);
    }

    @Override
    public String toString() {
        return "Undefined type";
    }
}
