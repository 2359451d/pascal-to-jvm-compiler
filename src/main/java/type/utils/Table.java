package type.utils;

public interface Table<A,T> {
    boolean put(A id, T item);
    T get(A id);
    boolean contains(A id);
}
