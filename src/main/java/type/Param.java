package type;

public class Param extends Type{

    private Type type;
    //private Integer numberLimitation;

    //public Param(Type type, Integer numberLimitation) { this.type = type;
    //    this.numberLimitation = numberLimitation;
    //}

    public Param(Type type) {
        this.type = type;
        //this.numberLimitation = -1; // no limitation on args number, ∞
    }

    @Override
    public String toString() {
        return "Param{" +
                "type=" + type +
                '}';
    }

    //@Override
    //public String toString() {
    //    return "Param{" +
    //            "type=" + type +
    //            ", numberLimitation=" + numberLimitation +
    //            '}';
    //}

    @Override
    public boolean equiv(Type type) {
        return false;
    }
}
