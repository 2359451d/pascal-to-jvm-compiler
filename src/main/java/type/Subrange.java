package type;

import type.enumerated.EnumeratedIdentifier;
import type.enumerated.EnumeratedType;
import type.primitive.Boolean;
import type.primitive.Character;
import type.primitive.integer.IntegerBaseType;

import java.util.Map;

public class Subrange extends BaseType {

    private Class<? extends TypeDescriptor> hostType;
    //private TypeDescriptor lowerBound;
    //private TypeDescriptor upperBound;
    private TypeDescriptor lowerBound;
    private TypeDescriptor upperBound;

    public <T extends TypeDescriptor> Subrange(TypeDescriptor lowerBound, TypeDescriptor upperBound,
                                               Class<T> hostType) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.hostType = hostType;
    }

    public Subrange(TypeDescriptor lowerBound, TypeDescriptor upperBound) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        if (lowerBound instanceof EnumeratedIdentifier) this.hostType = EnumeratedType.class;
        else this.hostType = lowerBound.getClass();
    }

    public Class<? extends TypeDescriptor> getHostType() {
        return hostType;
    }

    public TypeDescriptor getLowerBound() {
        return lowerBound;
    }

    public TypeDescriptor getUpperBound() {
        return upperBound;
    }

    /**
     * !Remark: checking the validity of the subrange is not defined here
     * @param type
     * @return
     */
    @Override
    public boolean equiv(TypeDescriptor type) {
        if (!(type instanceof Subrange)) return false;

        Subrange that = (Subrange) type;
        return this.hostType == that.hostType && this.lowerBound.equiv(that.lowerBound)
                && this.upperBound.equiv(that.upperBound);
    }

    //@Override
    //public String toString() {
    //    return ToStringBuilder.reflectionToString(this, CustomToStringStyle.SHORT_PREFIX_MULTI_LINE_STYLE);
    //}
}
