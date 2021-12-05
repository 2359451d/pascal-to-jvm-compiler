package type;

public class Mapping extends Type{

    public Type domain, result;

    public Mapping (Type d, Type r) {
        domain = d;
        result = r;
    }

    @Override
    public boolean equiv (Type that) {
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
