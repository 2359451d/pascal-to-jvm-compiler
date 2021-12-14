package type;

import java.util.ArrayList;
import java.util.List;

public class Proc extends Type{

    private List<Type> formalParams;

    public Proc() {
        this.formalParams = new ArrayList<>();
    }

    /**
     * If formalParams.size()==0, then proc has no formal params
     * @param formalParams
     */
    public Proc(List<Type> formalParams) {
        this.formalParams = formalParams;
    }

    public List<Type> getFormalParams() {
        return formalParams;
    }

    @Override
    public boolean equiv(Type type) {
        return false;
    }

    @Override
    public String toString() {
        return "Proc{\n" +
                "formalParams=" + formalParams +
                '}';
    }
}
