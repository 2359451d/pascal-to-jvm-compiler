package type;

public class FormalParam extends Param {

    private String label;

    public FormalParam(TypeDescriptor type, String label) {
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
    public boolean equiv(TypeDescriptor type) {
        if (!(type instanceof FormalParam)) {
            return this.getType().equiv(type);
        }
        FormalParam that = (FormalParam) type;
        if (!this.getType().equiv(that.getType())) return false;

        String thisLabel = this.label == null ? "" : this.label;
        String thatLabel = that.label == null ? "" : that.label;
        if (thisLabel.equals("proc") || thisLabel.equals("func") ||
                thatLabel.equals("proc")
                || thatLabel.equals("func")) {
            return thisLabel.equals(thatLabel);
        }
        return true;
        //if (StringUtils.isNotBlank(this.label) && StringUtils.isNotBlank(that.label)) {
        //    if (this.label.equals("var") || that.label.equals("var")) return
        //    return this.label.equals(that.label);
        //}
        //return this.label == null && that.label == null;
    }
}
