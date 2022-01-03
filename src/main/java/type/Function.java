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

    @Override
    public boolean equiv(TypeDescriptor type) {
        return false;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this,
                CustomToStringStyle.SHORT_PREFIX_MULTI_LINE_STYLE);
    }
}
