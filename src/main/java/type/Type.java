package type;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import type.error.Invalid;
import type.error.InvalidConstantType;
import type.error.Undefined;
import type.primitive.Integer32;
import type.primitive.IntegerBaseType;
import type.primitive.Primitive;

public abstract class Type implements TypeDescriptor {

    public static final Primitive INTEGER = new IntegerBaseType();
    public static final Primitive REAL = new Primitive("real");
    public static final Primitive CHARACTER = new Primitive("char");
    public static final Primitive BOOLEAN = new Primitive("bool");
    public static final Primitive VOID = new Primitive("void");

    public static final StringLiteral STRING_LITERAL = new StringLiteral();
    public static final File FILE = new File();

    // error type
    //public static final Invalid INVALID_TYPE = new Invalid();
    //public static final Undefined UNDEFINED_TYPE = new Undefined();
    //public static final InvalidConstantType INVALID_CONSTANT_TYPE = new InvalidConstantType();

    //public static final Str STRING = new Str();

    //public abstract boolean equiv(Type type);

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
