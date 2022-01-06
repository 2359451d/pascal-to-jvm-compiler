package type;

import type.primitive.Character;

public class StringLiteral extends BaseType{

    private String value;

    public StringLiteral() { }

    public StringLiteral(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equiv(TypeDescriptor type) {
        return type instanceof StringLiteral || type instanceof Character;
    }

}
