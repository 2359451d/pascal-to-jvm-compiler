package type;

public abstract class Param extends Type {
    @Override
    public boolean equiv(Type type) {
        System.out.println("Param 基类执行");
        return type instanceof Param;
    }
}
