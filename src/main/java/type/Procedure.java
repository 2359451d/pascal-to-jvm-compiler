package type;

import org.apache.commons.lang3.builder.ToStringBuilder;
import util.CustomToStringStyle;

import java.util.ArrayList;
import java.util.List;

public class Procedure extends Type {

    private List<TypeDescriptor> formalParams;

    public Procedure() {
        this.formalParams = new ArrayList<>();
    }

    /**
     * If formalParams.size()==0, then proc has no formal params
     *
     * @param formalParams
     */
    public Procedure(List<TypeDescriptor> formalParams) {
        this.formalParams = formalParams;
    }

    public List<TypeDescriptor> getFormalParams() {
        return formalParams;
    }

    public int getFormalParamsSize() {
        return formalParams.size();
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
