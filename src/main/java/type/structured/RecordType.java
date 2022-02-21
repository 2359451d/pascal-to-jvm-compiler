package type.structured;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import type.TypeDescriptor;

import java.util.LinkedHashMap;
import java.util.Map;

public class RecordType extends StructuredBaseType{

    private Map<String, TypeDescriptor> fieldsMap = new LinkedHashMap<>();

    public RecordType(Map<String, TypeDescriptor> fieldsMap) {
        this.fieldsMap = fieldsMap;
    }


    public Map<String, TypeDescriptor> getFieldsMap() {
        return fieldsMap;
    }

    @Override
    public boolean equiv(TypeDescriptor type) {
        return super.equiv(type);
    }

    //@Override
    //public String toString() {
    //    return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    //}
}
