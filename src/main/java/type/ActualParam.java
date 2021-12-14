package type;

public class ActualParam extends Param {

    private String label;

    public ActualParam(Type type) {
        setType(type);
        this.label = null;
    }

    public ActualParam(Type type, String label) {
        setType(type);
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return "ActualParam{" +
                "type=" + getType() +
                ", label='" + label + '\'' +
                '}';
    }

    @Override
    public boolean equiv(Type type) {
        if (!(type instanceof ActualParam)) {
            return getType().equiv(type);
        }
        ActualParam that = (ActualParam) type;
        return getType().equiv(that.getType()) && this.label.equals(that.label);
    }
}
