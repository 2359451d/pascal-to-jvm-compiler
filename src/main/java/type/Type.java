package type;

import type.enumerated.EnumeratedType;
import type.primitive.Boolean;
import type.primitive.Character;
import type.primitive.Primitive;
import type.primitive.floating.DefaultFloatType;
import type.primitive.integer.DefaultIntegerType;

/**
 * Provide existing types (static) for type checking or other usage.
 */
public enum Type implements TypeDescriptor {

    INTEGER(DefaultIntegerType.instance),
    REAL(DefaultFloatType.instance),
    CHARACTER(new Character()),
    BOOLEAN(new Boolean()),
    VOID(new Primitive("void")),
    STRING(new StringLiteral()),
    FILE(new File()),
    // FIXME not necessary? no sense to compare sth with raw enumerated type
    ENUMERATED(new EnumeratedType());;


    private BaseType type;

    Type(BaseType type) {
        this.type = type;
    }

    @Override
    public boolean equiv(TypeDescriptor type) {
        if (!(type instanceof Type)) return false;
        Type that = (Type) type;
        //System.out.println("that = " + that);
        //System.out.println("this = " + this);
        return this.type.equiv(that.type);
    }

    //public static final Primitive INTEGER = new IntegerBaseType();
    //public static final Primitive REAL = new Primitive("real");
    //public static final Primitive CHARACTER = new Primitive("char");
    //public static final Primitive BOOLEAN = new Primitive("bool");
    //public static final Primitive VOID = new Primitive("void");
    //public static final StringLiteral STRING_LITERAL = new StringLiteral();
    //public static final File FILE = new File();
    //
    //// FIXME not necessary? no sense to compare sth with raw enumerated type
    //public static final Enumerated ENUMERATED = new Enumerated();

    //private boolean isConstant = false;
    //
    //public boolean isConstant() {
    //    return isConstant;
    //}
    //
    //public void setConstant(boolean constant) {
    //    isConstant = constant;
    //}

    //@Override
    //public String toString() {
    //    return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    //}
}
