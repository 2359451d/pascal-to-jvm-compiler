package type;

public class AddressType extends BaseType{
    @Override
    public boolean equiv(TypeDescriptor type) {
        return type instanceof AddressType;
    }
}
