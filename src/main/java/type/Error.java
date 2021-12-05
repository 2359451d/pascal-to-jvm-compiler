package type;

public class Error extends Type {

    public Error() {
    }

    public boolean equiv(Type that) {
        return true;
    }

    public String toString() {
        return "error";
    }
}
