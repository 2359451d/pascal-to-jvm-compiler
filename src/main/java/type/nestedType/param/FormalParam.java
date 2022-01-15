package type.nestedType.param;

import type.TypeDescriptor;

public class FormalParam extends Param {

    private String label;

    public FormalParam(TypeDescriptor type, String label) {
        this.setHostType(type);
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public boolean equiv(TypeDescriptor type) {
        if (!(type instanceof FormalParam)) {
            return this.getHostType().equiv(type);
        }
        FormalParam that = (FormalParam) type;
        if (!this.getHostType().equiv(that.getHostType())) return false;

        String thisLabel = this.label == null ? "" : this.label;
        String thatLabel = that.label == null ? "" : that.label;
        if (thisLabel.equals("proc") || thisLabel.equals("func") ||
                thatLabel.equals("proc")
                || thatLabel.equals("func")) {
            return thisLabel.equals(thatLabel);
        }
        return true;
    }
}
