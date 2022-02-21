package type.primitive;

import type.StringLiteral;
import type.TypeDescriptor;

public class Character extends Primitive<java.lang.Character>{
    //private String value;

    public Character() {
        super("char");
    }

    public Character(char value) {
        super("char");
        this.value = value;
    }

    public Character(char value, boolean isConstant) {
        this(value);
        this.isConstant = isConstant;
    }


    //@Override
    //public String getDescriptor() {
    //    return Type.getDescriptor(char.class);
    //}


    @Override
    public Class<?> getDescriptorClass() {
        return char.class;
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
