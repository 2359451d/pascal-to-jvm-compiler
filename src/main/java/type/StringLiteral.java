package type;

import type.primitive.Character;

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
        return type instanceof StringLiteral || type instanceof Character;
    }

}
