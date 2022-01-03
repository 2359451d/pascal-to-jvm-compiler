package type;

public class StringLiteral extends BaseType{

    private String value;

    public StringLiteral() { }

    public StringLiteral(String value) {
        this.value = value;
    }

    @Override
    public boolean equiv(TypeDescriptor type) {
        // if constant, directly compare type
        //if (type instanceof Constant) {
        //    return ((Constant)type).getType() instanceof StringLiteral;
        //}

        return (type instanceof StringLiteral) ;
    }

}
