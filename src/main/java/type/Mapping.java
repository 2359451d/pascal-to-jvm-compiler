package type;

public class Mapping extends Type{

    public TypeDescriptor domain, result;

    public Mapping (TypeDescriptor d, TypeDescriptor r) {
        domain = d;
        result = r;
    }

    @Override
    public boolean equiv (TypeDescriptor that) {
        if (that instanceof Mapping) {
            Mapping thatMapping =
                    (Mapping)that;
            return this.domain.equiv(
                    thatMapping.domain)
                    && this.result.equiv(
                    thatMapping.result);
        } else
            return false;
    }

    public String toString () {
        return domain + " -> " + result;
    }
}
