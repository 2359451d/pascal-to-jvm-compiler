package type.primitive.floating;

public class Real extends FloatBaseType{

    public static final Double MAX_VALUE = Double.MAX_VALUE;
    public static final Double MIN_VALUE = Double.MIN_VALUE;
    //private float value;

    public Real() {
        super("real64");
    }

    public Real(double value) {
        this.value = value;
    }

    public Real(String value) {
        this.value = Double.parseDouble(value);
    }

}
