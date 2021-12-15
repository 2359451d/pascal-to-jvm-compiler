package type;

public class FormalParam extends Param {

    private String label;

    public FormalParam(Type type, String label) {
        this.setType(type);
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    //@Override
    //public String toString() {
    //    return "Param{" +
    //            "type=" + getType() +
    //            ", label='" + label + '\'' +
    //            "}";
    //}

    @Override
    public boolean equiv(Type type) {
        if (!(type instanceof FormalParam)) {
            return getType().equiv(type);
        }
        FormalParam that = (FormalParam) type;
        return getType().equiv(that.getType()) && this.label.equals(that.label);
    }
}
