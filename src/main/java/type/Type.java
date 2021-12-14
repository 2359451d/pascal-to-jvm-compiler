package type;

import type.error.Invalid;
import type.error.Undefined;

public abstract class Type {

    public static final Primitive INTEGER = new Primitive("int");
    public static final Primitive REAL = new Primitive("real");
    public static final Primitive CHARACTER = new Primitive("char");
    public static final Primitive BOOLEAN = new Primitive("bool");
    public static final Primitive VOID = new Primitive("void");

    public static final Str STR = new Str();
    public static final File FILE = new File();

    // error type
    public static final Invalid INVALID_TYPE = new Invalid();
    public static final Undefined UNDEFINED_TYPE = new Undefined();

    //public static final Str STRING = new Str();

    public abstract boolean equiv(Type type);




}
