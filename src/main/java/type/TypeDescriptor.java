package type;

import org.objectweb.asm.Type;

/**
 * Base interface for BaseType, ErrorType, BuiltinType
 * <p>
 *     Usage: to uniform the return type of t visitors
 * </p>
 */
public interface TypeDescriptor {
    boolean equiv(TypeDescriptor type);

    default String getDescriptor() {
        Class<?> descriptorClass = getDescriptorClass();
        return descriptorClass != null ? Type.getDescriptor(descriptorClass) : "";
    }

    default Class<?> getDescriptorClass() {
        return null;
    }
}
