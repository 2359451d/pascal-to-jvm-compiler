package type;

/**
 * Primitive/Standard types of Pascal
 * Cover: Integer, Real, Character, Boolean
 */
public class Primitive extends Type{

    public String type;

    public Primitive(String type) {
        this.type = type;
    }

    //@Override
    //public java.lang.String toString() {
    //    return "Primitive{" +
    //            "type='" + type + '\'' +
    //            '}';
    //}

    @Override
    public boolean equiv(Type type) {
        if (!(type instanceof Primitive)) return false;
        Primitive otherType = (Primitive) type;

        //if (!this.type.equals(otherType.type) && this.type.equals("real")) {
        //    // exception case of real type
        //    System.out.println("this type is real");
        //    return otherType.type.equals("int");
        //}else
        return (this.type).equals(otherType.type);
    }
}
