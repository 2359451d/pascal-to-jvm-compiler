package type.primitive.floating;

public class Real extends FloatBaseType{

    public static final Float MAX_VALUE = Float.MAX_VALUE;
    public static final Float MIN_VALUE = Float.MIN_VALUE;
    //private float value;

    public Real() {
        super("real64");
    }

    public Real(float value) {
        this.value = value;
    }

    public Real(String value) {
        this.value = Float.parseFloat(value);
    }

    //public float getValue() {
    //    return value;
    //}
    //
    //public void setValue(float value) {
    //    this.value = value;
    //}
}
