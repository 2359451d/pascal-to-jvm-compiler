package type.nestedType.param;

import type.TypeDescriptor;

public class ActualParam extends Param {

    //private String label;

    public ActualParam(TypeDescriptor type) {
        setHostType(type);
    }

    public ActualParam(TypeDescriptor type, String label) {
        setHostType(type);
        setLabel(label);
    }

    public ActualParam(TypeDescriptor type, String name, String label) {
        setHostType(type);
        setName(name);
        setLabel(label);
    }

    //public String getLabel() {
    //    return label;
    //}

    @Override
    public boolean equiv(TypeDescriptor type) {
        if (!(type instanceof ActualParam)) {
            return getHostType().equiv(type);
        }
        ActualParam that = (ActualParam) type;
        return getHostType().equiv(that.getHostType()) && this.getLabel().equals(that.getLabel());
    }
}
