package type;

public class PointerType extends BaseType {

    // pointed-type
    TypeDescriptor pointedType;

    public PointerType(TypeDescriptor pointedType) {
        this.pointedType = pointedType;
    }

    public TypeDescriptor getPointedType() {
        return pointedType;
    }

    public void setPointedType(TypeDescriptor pointedType) {
        this.pointedType = pointedType;
    }

    @Override
    public boolean equiv(TypeDescriptor type) {
        if (!(type instanceof PointerType) && !(type instanceof AddressType)
         &&!(type instanceof NilType)) return false;
        if (type instanceof AddressType || type instanceof NilType) return true;
        PointerType that = (PointerType) type;
        return this.pointedType.equiv(that.pointedType);
    }
}
