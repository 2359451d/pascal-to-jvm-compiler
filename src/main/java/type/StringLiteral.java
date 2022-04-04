package type;

import type.primitive.Character;
import type.primitive.integer.IntegerBaseType;
import type.structured.ArrayType;

public class StringLiteral extends BaseType {

    private String value;

    public StringLiteral() {
    }

    public StringLiteral(String value) {
        this.value = value;
    }

    public StringLiteral(String value, boolean isConstant) {
        this.value = value;
        this.isConstant = isConstant;
    }

    public String getValue() {
        return value;
    }

    //@Override
    //public String getDescriptor() {
    //    return Type.getDescriptor(String.class);
    //}

    @Override
    public Class<?> getDescriptorClass() {
        return String.class;
    }

    @Override
    public boolean equiv(TypeDescriptor type) {
        if (type instanceof StringLiteral) return true;
        if (type instanceof ArrayType) {
            TypeDescriptor componentType = ((ArrayType) type).getComponentType();
            boolean packed = ((ArrayType) type).isPacked();
            if (!(componentType instanceof Character && packed)) return false;

            TypeDescriptor _type = ((ArrayType) type).getIndexList().get(0);
            if (_type instanceof Subrange) {
                TypeDescriptor lowerBound = ((Subrange) _type).getLowerBound();
                TypeDescriptor upperBound = ((Subrange) _type).getUpperBound();
                // calculate the expected string length
                Long expectedLength = null;
                if (lowerBound instanceof IntegerBaseType && upperBound instanceof IntegerBaseType) {
                    Long lowerValue = ((IntegerBaseType) lowerBound).getValue();
                    Long upperValue = ((IntegerBaseType) upperBound).getValue();
                    if (lowerValue != 1) {
                        return false;
                    }
                    expectedLength = upperValue - lowerValue + 1;
                    // check whether right operand is a string type with the same length
                    int actualLength = this.getValue().replace("'", "").length();
                    if (expectedLength!=null && expectedLength.intValue() != actualLength) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

}
