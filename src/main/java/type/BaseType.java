package type;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.lang.reflect.Field;

public abstract class BaseType implements TypeDescriptor {

    private boolean isConstant = false;

    public boolean isConstant() {
        return isConstant;
    }

    public void setConstant(boolean constant) {
        isConstant = constant;
    }

    /**
     * Using ToStringStyle.SHORT_PREFIX_STYLE as default printed formatter
     * <p>
     *     Default: ignore isConstant field
     * </p>
     * @return type in a formatted string
     */
    @Override
    public String toString() {
        //return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
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
