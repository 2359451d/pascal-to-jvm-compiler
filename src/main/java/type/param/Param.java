package type.param;

import type.BaseType;
import type.TypeDescriptor;

public abstract class Param extends BaseType {
    private TypeDescriptor type;

    public TypeDescriptor getType() {
        return type;
    }

    public void setType(TypeDescriptor type) {
        this.type = type;
    }

    //@Override
    //public String toString() {
    //    return ToStringBuilder.reflectionToString(this,
    //            CustomToStringStyle.SHORT_PREFIX_MULTI_LINE_STYLE);
    //}


    @Override
    public boolean equiv(TypeDescriptor that) {
        //if (!(that instanceof Param)) return false;
        //if (this.) {
        //    return
        //}
        return (that instanceof Param) && (this.type.equiv(((Param) that).type));
    }
}
