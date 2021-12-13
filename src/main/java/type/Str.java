package type;

public class Str extends Type{
    private String value;

    public Str() { }

    public Str(String value) {
        this.value = value;
    }

    @Override
    public boolean equiv(Type type) {
        return (type instanceof Str) ;
    }

    @Override
    public String toString() {
        return "Str{" +
                "value='" + value + '\'' +
                '}';
    }
}
