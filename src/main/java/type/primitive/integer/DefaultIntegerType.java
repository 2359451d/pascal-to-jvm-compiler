package type.primitive.integer;

/**
 * Singleton Integer type
 * Might be used with extended compiler behavior (like switch default integer type)
 */
public class DefaultIntegerType extends IntegerBaseType {

    public static final IntegerBaseType instance = new Integer32();

    private DefaultIntegerType(){}
}
