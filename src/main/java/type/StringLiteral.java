package type;

public class StringLiteral extends Type{
    private String value;

    public StringLiteral() { }

    public StringLiteral(String value) {
        this.value = value;
    }

    @Override
    public boolean equiv(TypeDescriptor type) {
        return (type instanceof StringLiteral) ;
    }

}
