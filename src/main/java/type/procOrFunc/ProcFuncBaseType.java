package type.procOrFunc;

import org.apache.commons.lang3.builder.ToStringBuilder;
import type.BaseType;
import type.TypeDescriptor;
import util.CustomToStringStyle;

import java.util.List;

/**
 * Base type for Procedure and Function
 */
public class ProcFuncBaseType extends BaseType {

    protected List<TypeDescriptor> formalParams;

    public List<TypeDescriptor> getFormalParams() {
        return formalParams;
    }

    @Override
    public boolean equiv(TypeDescriptor type) {
        if (!(type instanceof ProcFuncBaseType)) return false;
        ProcFuncBaseType that = (ProcFuncBaseType) type;
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
