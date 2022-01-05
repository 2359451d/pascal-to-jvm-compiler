package type.enumerated;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import type.BaseType;
import type.TypeDescriptor;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Enumerated Types
 * <p>
 * This is defined to be the ordinal type
 * - ordinal number of the first element = 0
 * Allows application of relational operators -> evaluated to boolean
 * Allows functionL: succ, pred, ord
 * <p>
 * Remark:
 * An enumeration data type is a finite list of named discrete values. Enumerations virtually give names to individual integer values, however, you cannot (directly) do arithmetic operations on it.
 */
public class EnumeratedType extends BaseType {

    //private String typeName = null;

    // mapping identifier to a ordinal number
    private Map<String, Integer> valueMap = new LinkedHashMap<>();

    public Map<String, Integer> getValueMap() {
        return valueMap;
    }

    public void setValueMap(Map<String, Integer> valueMap) {
        this.valueMap = valueMap;
    }

    //public String getTypeName() {
    //    return typeName;
    //}
    //
    //public void setTypeName(String typeName) {
    //    this.typeName = typeName;
    //}

    @Override
    public boolean equiv(TypeDescriptor type) {
        if (!(type instanceof EnumeratedType)) return false;
        EnumeratedType that = (EnumeratedType) type;
        Map<String, Integer> thatValueMap = that.getValueMap();
        Map<String, Integer> thisValueMap = this.valueMap;
        for (String s : thatValueMap.keySet()) {
            if (!thisValueMap.containsKey(s)) {
                return false;
            }
        }
        return true;
    }

    //@Override
    //public String toString() {
    //    //include isConstant field which is excluded in BaseType class
    //    return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    //}
}
