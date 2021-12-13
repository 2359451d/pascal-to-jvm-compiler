package type.error;

import type.Type;

/**
 * Used when the expression/value cannot be evaluated
 */
public class Invalid extends Type {

    public Invalid() {
    }

    public boolean equiv(Type that) {
        if (!(that instanceof Invalid)) return false;
        Invalid otherType = (Invalid) that;

        //if (!this.type.equals(otherType.type) && this.type.equals("real")) {
        //    // exception case of real type
        //    System.out.println("this type is real");
        //    return otherType.type.equals("int");
        //}else
        return (this).equals(otherType);
    }

    public String toString() {
        return "Invalid type";
    }
}
