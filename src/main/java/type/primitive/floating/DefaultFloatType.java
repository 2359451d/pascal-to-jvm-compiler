package type.primitive.floating;

public class DefaultFloatType extends FloatBaseType {

    public static final FloatBaseType instance = new Real();

    private DefaultFloatType() {

    }

    public static Real of(float value) {
        return new Real(value);
    }

    public static Real of(String value) {
        return new Real(value);
    }

    public static Real of(String value, boolean isConstant) {
        Real real = new Real(value);
        real.setConstant(isConstant);
        return real;
    }

    public static Real of(float value, boolean isConstant) {
        Real real = new Real(value);
        real.setConstant(isConstant);
        return real;
    }

}