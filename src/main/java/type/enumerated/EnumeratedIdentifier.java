package type.enumerated;

import org.apache.commons.lang3.builder.ToStringBuilder;
import type.BaseType;
import type.TypeDescriptor;
import utils.CustomToStringStyle;

/**
 * Represents a single value of an existing enumerated type
 */
public class EnumeratedIdentifier extends BaseType {

    private EnumeratedType belongsTo;

    private String value;

    public EnumeratedIdentifier(String value) {
        this.value = value;
    }

    public EnumeratedIdentifier(EnumeratedType belongsTo, String value) {
        this.belongsTo = belongsTo;
        this.value = value;
    }

    public EnumeratedType getBelongsTo() {
        return belongsTo;
    }

    public void setBelongsTo(EnumeratedType belongsTo) {
        this.belongsTo = belongsTo;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equiv(TypeDescriptor type) {
        if (!(type instanceof EnumeratedType) && !(type instanceof EnumeratedIdentifier)) return false;
        if (type instanceof EnumeratedType) {
            EnumeratedType that = (EnumeratedType) type;
            return that.getValueMap().containsKey(this.value.toLowerCase());
        }
        EnumeratedIdentifier that = (EnumeratedIdentifier) type;
        // check whether the enumerated identifier is of the same kind
        return this.belongsTo.equiv(that.getBelongsTo()) ;
    }

    @Override
    public String toString() {
        //include isConstant field which is excluded in BaseType class
        return ToStringBuilder.reflectionToString(this, CustomToStringStyle.SHORT_PREFIX_MULTI_LINE_STYLE);
    }
}
