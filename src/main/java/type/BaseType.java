package type;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.lang.reflect.Field;

public abstract class BaseType implements TypeDescriptor {

    protected boolean isConstant = false;
    //private boolean isPackedElement = false;

    public boolean isConstant() {
        return isConstant;
    }

    public void setConstant(boolean constant) {
        isConstant = constant;
    }

    //public boolean isPackedElement() {
    //    return isPackedElement;
    //}

    //public void setPackedElement(boolean packedElement) {
    //    isPackedElement = packedElement;
    //}

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
                return !field.getName().equals("isConstant") && !field.getName().equals("isPackedElement");
            }
        };
        return reflectionToStringBuilder.toString();
    }

}
