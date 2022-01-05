package type.primitive;

public class Boolean extends Primitive{

    private boolean value;

    public boolean getValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public Boolean() {
        super("bool");
    }

    public Boolean(boolean value) {
        super("bool");
        this.value = value;
    }
}
