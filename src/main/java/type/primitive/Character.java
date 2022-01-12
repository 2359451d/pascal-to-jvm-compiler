package type.primitive;

import type.StringLiteral;
import type.TypeDescriptor;

public class Character extends Primitive{
    private String value;

    public Character() {
        super("char");
    }

    public Character(String value) {
        super("char");
        this.value = value;
    }

    @Override
    public boolean equiv(TypeDescriptor type) {
        // exception case when left is character, and right is string literal(length must be 1)
        if (!(type instanceof Character) && !(type instanceof StringLiteral)) return false;
        if (type instanceof StringLiteral) {
            int length = ((StringLiteral) type).getValue().replace("'", "").length();
            return length == 1;
        }
        return super.equiv(type);
    }
}
