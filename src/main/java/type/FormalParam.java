package type;

public class FormalParam extends Param {

    private Type type;
    private String label;
    //private Integer numberLimitation;

    //public Param(Type type, Integer numberLimitation) { this.type = type;
    //    this.numberLimitation = numberLimitation;
    //}

    public FormalParam(Type type, String label) {
        this.type = type;
        this.label = label;
        //this.numberLimitation = -1; // no limitation on args number, ∞
    }

    public Type getType() {
        return type;
    }

    public String getLabel() {
        return label;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "Param{" +
                "type=" + type +
                ", label='" + label + '\'' +
                "}\n";
    }

    //@Override
    //public String toString() {
    //    return "Param{" +
    //            "type=" + type +
    //            ", numberLimitation=" + numberLimitation +
    //            '}';
    //}

    @Override
    public boolean equiv(Type type) {
        if (!(type instanceof FormalParam)) {
            return this.type.equiv(type);
        }
        FormalParam that = (FormalParam) type;
        return this.type.equiv(that.type) && this.label.equals(that.label);
    }
}
