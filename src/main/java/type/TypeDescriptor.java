package type;

/**
 * Base interface for BaseType, ErrorType, BuiltinType
 * <p>
 *     Usage: to uniform the return type of visitors
 * </p>
 */
public interface TypeDescriptor {
    boolean equiv(TypeDescriptor type);
}
