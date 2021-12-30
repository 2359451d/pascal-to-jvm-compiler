package type.primitive;

import type.Type;
import type.TypeDescriptor;

/**
 * Integer (no compiler mode switching implemented)
 * Takes up 32bit (4 bytes)
 * Refer to Free Pascal {$mode iso}, see more: https://wiki.freepascal.org/Mode_iso
 */
public class Integer32 extends Primitive{

    private final Integer maxValue = Integer.MAX_VALUE;
    private final Integer minValue = Integer.MIN_VALUE;

    public Integer32() {
        super("int");
    }

    public Integer getMaxValue() {
        return maxValue;
    }

    public Integer getMinValue() {
        return minValue;
    }

    @Override
    public boolean equiv(TypeDescriptor type) {
        return super.equiv(type);
    }
}
