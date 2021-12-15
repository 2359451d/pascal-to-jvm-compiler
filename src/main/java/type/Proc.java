package type;

import org.apache.commons.lang3.builder.ToStringBuilder;
import utils.CustomToStringStyle;

import java.util.ArrayList;
import java.util.List;

public class Proc extends Type {

    private List<Type> formalParams;

    public Proc() {
        this.formalParams = new ArrayList<>();
    }

    /**
     * If formalParams.size()==0, then proc has no formal params
     *
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
        return ToStringBuilder.reflectionToString(this,
                CustomToStringStyle.SHORT_PREFIX_MULTI_LINE_STYLE);
    }
}
