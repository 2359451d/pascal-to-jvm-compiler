package type;

public abstract class Param extends Type {
    private Type type;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    //@Override
    //public String toString() {
    //    return ToStringBuilder.reflectionToString(this,
    //            CustomToStringStyle.SHORT_PREFIX_MULTI_LINE_STYLE);
    //}

    @Override
    public boolean equiv(Type that) {
        //if (!(that instanceof Param)) return false;
        //if (this.) {
        //    return
        //}
        return (that instanceof Param) && (this.type.equiv(((Param) that).type));
    }
}
