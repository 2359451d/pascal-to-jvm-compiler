package type;

public abstract class Type {

    public static final Primitive INTEGER = new Primitive("int");
    public static final Primitive REAL = new Primitive("real");
    public static final Primitive CHARACTER = new Primitive("char");
    public static final Primitive BOOLEAN = new Primitive("bool");
    public static final Primitive VOID = new Primitive("void");

    public static final Str STR = new Str();
    public static final File FILE = new File();

    public static final Error ERROR = new Error();

    //public static final Str STRING = new Str();

    public abstract boolean equiv(Type type);

}
