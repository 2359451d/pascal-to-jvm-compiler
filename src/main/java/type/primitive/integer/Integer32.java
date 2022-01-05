package type.primitive.integer;

import type.TypeDescriptor;

/**
 * Integer (no compiler mode switching implemented)
 * Takes up 32bit (4 bytes)
 * Refer to Free Pascal {$mode iso}, see more: https://wiki.freepascal.org/Mode_iso
 */
public class Integer32 extends IntegerBaseType{

    public static final Integer MAX_VALUE = Integer.MAX_VALUE;
    public static final Integer MIN_VALUE = Integer.MIN_VALUE;

    // use Long type in case overflow/underflow
    //private Long value;

    public Integer32() {
        super("int32");
    }

    public Integer32(Long value) {
        super("int32");
        //this.value = value;
        this.setValue(value);
    }

    public Integer32(String value) {
        super("int32");
        //this.value = Long.valueOf(value);
        this.setValue(Long.valueOf(value));
    }

    public Integer32(String value, boolean isConstant) {
        super("int32");
        //this.value = Long.valueOf(value);
        this.setValue(Long.valueOf(value));
        this.setConstant(isConstant);
    }


    public static Integer32 of(Long value) {
        return new Integer32(value);
    }

    public static Integer32 of(String value) {
        return new Integer32(value);
    }

    @Override
    public boolean equiv(TypeDescriptor type) {
        return super.equiv(type);
    }

    //@Override
    //public String toString() {
    //    return "integer32";
    //}
}
