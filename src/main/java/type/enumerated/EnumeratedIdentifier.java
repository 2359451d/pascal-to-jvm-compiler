package type.enumerated;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import type.BaseType;
import type.TypeDescriptor;

import java.lang.reflect.Field;

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
        if (!(type instanceof EnumeratedIdentifier)) return false;
        EnumeratedIdentifier that = (EnumeratedIdentifier) type;
        // check whether the enumerated identifier is of the same kind
        return this.belongsTo.equiv(that.getBelongsTo());
    }

    @Override
    public String toString() {
        ReflectionToStringBuilder reflectionToStringBuilder =
                new ReflectionToStringBuilder(this,
                        ToStringStyle.SHORT_PREFIX_STYLE) {
                    @Override
                    protected boolean accept(Field field) {
                        return !field.getName().equals("isConstant");
                    }
                };
        return reflectionToStringBuilder.toString();
    }
}
