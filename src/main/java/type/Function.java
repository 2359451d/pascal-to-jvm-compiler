package type;

import org.apache.commons.lang3.builder.ToStringBuilder;
import util.CustomToStringStyle;

import java.util.List;

public class Function extends BaseType {

    private List<TypeDescriptor> formalParams;

    private TypeDescriptor resultType;

    /**
     * If formalParams.size()==0, then function has no formal params
     *
     * @param formalParams
     */
    public Function(List<TypeDescriptor> formalParams, TypeDescriptor resultType) {
        this.formalParams = formalParams;
        this.resultType = resultType;
    }

    public List<TypeDescriptor> getFormalParams() {
        return formalParams;
    }

    public TypeDescriptor getResultType() {
        return resultType;
    }

    /**
     * Compare whether two Function types are equal
     * Usage: compare when Function itself as parameters to Procedure/Function
     *
     * @param type
     * @return
     */
    @Override
    public boolean equiv(TypeDescriptor type) {
        if (!(type instanceof Function)) return false;
        Function that = (Function) type;
        if (this.resultType!=that.getResultType()) return false;
        if (this.formalParams.size()!=that.formalParams.size()) return false;

        for (int i = 0; i < this.formalParams.size(); i++) {
            if (!this.formalParams.get(i).equiv(that.formalParams.get(i))) return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this,
                CustomToStringStyle.SHORT_PREFIX_MULTI_LINE_STYLE);
    }
}
