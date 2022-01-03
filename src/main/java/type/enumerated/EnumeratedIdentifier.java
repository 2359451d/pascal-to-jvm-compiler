package type.enumerated;

import type.BaseType;
import type.TypeDescriptor;

import java.util.prefs.BackingStoreException;

/**
 * Represents a single value of an existing enumerated type
 */
public class EnumeratedIdentifier extends BaseType {

    private String value;

    public EnumeratedIdentifier(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equiv(TypeDescriptor type) {
        if (!(type instanceof EnumeratedIdentifier)) return false;
        EnumeratedIdentifier that = (EnumeratedIdentifier) type;
        return this.value.equals(that.getValue());
    }
}
