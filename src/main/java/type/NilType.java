package type;

public class NilType extends BaseType{
    @Override
    public boolean equiv(TypeDescriptor type) {
        return type instanceof NilType;
    }
}
