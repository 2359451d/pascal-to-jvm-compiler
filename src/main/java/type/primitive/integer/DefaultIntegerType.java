package type.primitive.integer;

/**
 * Singleton Integer type
 * Might be used with extended compiler behavior (like switch default integer type)
 */
public class DefaultIntegerType extends IntegerBaseType {

    public static final IntegerBaseType instance = new Integer32();

    private DefaultIntegerType(){}

    public static Integer32 of(Long value) {
        return new Integer32(value);
    }

    public static Integer32 of(String value) {
        return new Integer32(value);
    }

    public static Integer32 of(String value, boolean isConstant) {
        return new Integer32(value, isConstant);
    }

    public static Integer32 of(Long value, boolean isConstant) {
        return new Integer32(value, isConstant);
    }
}
