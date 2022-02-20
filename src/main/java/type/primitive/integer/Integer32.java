package type.primitive.integer;

import type.TypeDescriptor;

/**
 * Integer (no compiler mode switching implemented)
 * Takes up 32bit (4 bytes)
 * Refer to Free Pascal {$mode iso}, see more: https://wiki.freepascal.org/Mode_iso
 */
public class Integer32 extends IntegerBaseType{

    public static final Long MAX_VALUE = Long.MAX_VALUE;
    public static final Long MIN_VALUE = Long.MIN_VALUE;

    // use Long type in case overflow/underflow
    //private Long value;

    public Integer32() {
        super("int32");
    }

    public Integer32(Long value) {
        super("int32",value);
        //this.value = value;
    }

    public Integer32(String value) {
        super("int32", Long.valueOf(value));
        //this.value = Long.valueOf(value);
        //this.setValue(Long.valueOf(value));
    }

    public Integer32(Long value, boolean isConstant) {
        //this();
        //this.setValue(value);
        //this.setConstant(isConstant);
        this(value);
        this.isConstant = isConstant;
    }

    public Integer32(String value, boolean isConstant) {
        this(Long.valueOf(value), isConstant);

        //super("int32");
        ////this.value = Long.valueOf(value);
        //this.setValue(Long.valueOf(value));
        //this.setConstant(isConstant);
    }

    //public Integer32(boolean isConstant) {
    //    super("int32");
    //    this.setConstant(isConstant);
    //}


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
