package tableUtils;

import org.apache.commons.lang3.builder.ToStringBuilder;
import utils.CustomToStringStyle;

public class LocalVariableInformation {
    private int slotNum;
    private int length;
    private boolean isStatic; // determine whether is static field

    public LocalVariableInformation(int slotNum, int length, boolean isStatic) {
        this.slotNum = slotNum;
        this.length = length;
        this.isStatic = isStatic;
    }

    public LocalVariableInformation(int slotNum, int length) {
        this(slotNum, length, false);
    }

    public LocalVariableInformation(int length, boolean isStatic) {
        // set a invalid default slot number
        this(-999, length, isStatic);
    }

    public int getSlotNum() {
        return slotNum;
    }

    public int getLength() {
        return length;
    }

    public boolean isStatic() {
        return isStatic;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, CustomToStringStyle.MULTI_LINE_STYLE);
    }
}
