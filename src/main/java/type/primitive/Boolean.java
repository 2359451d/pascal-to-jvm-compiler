package type.primitive;

public class Boolean extends Primitive<java.lang.Boolean>{

    //private boolean value;

    //public boolean getValue() {
    //    return value;
    //}
    //
    //public void setValue(boolean value) {
    //    this.value = value;
    //}

    public Boolean() {
        super("bool");
    }

    public Boolean(boolean value) {
        super("bool");
        this.value = value;
    }

    public Boolean(boolean value, boolean isConstant) {
        this(value);
        this.isConstant = isConstant;
    }



    @Override
    public Class<?> getDescriptorClass() {
        return boolean.class;
    }
}
