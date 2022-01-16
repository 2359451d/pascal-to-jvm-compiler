package type.nestedType.param;

import type.TypeDescriptor;

public class FormalParam extends Param {

    //private String label;

    public FormalParam(TypeDescriptor type, String label) {
        setHostType(type);
        setLabel(label);
    }

    public FormalParam(TypeDescriptor type, String name, String label) {
        setHostType(type);
        setName(name);
        setLabel(label);
    }

    //public String getLabel() {
    //    return label;
    //}

    //public void setLabel(String label) {
    //    this.label = label;
    //}

    @Override
    public boolean equiv(TypeDescriptor type) {
        if (!(type instanceof FormalParam)) {
            return this.getHostType().equiv(type);
        }
        FormalParam that = (FormalParam) type;
        if (!this.getHostType().equiv(that.getHostType())) return false;

        String thisLabel = this.getLabel() == null ? "" : this.getLabel();
        String thatLabel = that.getLabel() == null ? "" : that.getLabel();
        if (thisLabel.equals("proc") || thisLabel.equals("func") ||
                thatLabel.equals("proc")
                || thatLabel.equals("func")) {
            return thisLabel.equals(thatLabel);
        }
        return true;
    }
}
