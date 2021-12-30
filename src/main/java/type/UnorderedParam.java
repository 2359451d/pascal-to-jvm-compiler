package type;

import java.util.Set;

public class UnorderedParam extends Type {

    private Set<String> acceptableTypes;

    public UnorderedParam(Set<String> acceptableTypes) {
        this.acceptableTypes = acceptableTypes;
    }

    @Override
    public String toString() {
        return "UnorderedParam{" +
                "acceptableTypes=" + acceptableTypes +
                '}';
    }

    @Override
    public boolean equiv(TypeDescriptor type) {
        return false;
    }
}
