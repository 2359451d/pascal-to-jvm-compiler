package type;

public class ActualParam extends Param {

    private Type type;
    private String label;

    public ActualParam(Type type) {
        this.type = type;
        this.label = null;
    }

    public ActualParam(Type type, String label) {
        this.type = type;
        this.label = label;
    }

    @Override
    public String toString() {
        return "ActualParam{" +
                "type=" + type +
                ", label='" + label + '\'' +
                '}';
    }

    @Override
    public boolean equiv(Type type) {
        if (!(type instanceof ActualParam)) {
            return this.type.equiv(type);
        }
        ActualParam that = (ActualParam) type;
        return this.type.equiv(that.type) && this.label.equals(that.label);
    }
}
