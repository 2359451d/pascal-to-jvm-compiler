package type.nestedType.param;

import type.TypeDescriptor;
import type.nestedType.NestedBaseType;

public abstract class Param extends NestedBaseType {
    //private TypeDescriptor type;
    //
    //public TypeDescriptor getType() {
    //    return type;
    //}
    //
    //public void setType(TypeDescriptor type) {
    //    this.type = type;
    //}

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
        return (that instanceof Param) && (this.getHostType().equiv(((Param) that).getHostType()));
    }
}
