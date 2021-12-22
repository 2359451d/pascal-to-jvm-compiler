package util;


import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.builder.ToStringStyle;

public class CustomToStringStyle extends ToStringStyle {
    public static final ToStringStyle SHORT_PREFIX_MULTI_LINE_STYLE = new CustomToStringStyle.ShortPrefixMultiLineToStringStyle();

    private CustomToStringStyle() { }

    private static final class ShortPrefixMultiLineToStringStyle extends ToStringStyle {
        private static final long serialVersionUID = 1L;

        public ShortPrefixMultiLineToStringStyle() {
            this.setContentStart("[");
            this.setFieldSeparator(SystemUtils.LINE_SEPARATOR + "  ");
            this.setFieldSeparatorAtStart(true);
            this.setContentEnd(SystemUtils.LINE_SEPARATOR + "]");
            this.setUseShortClassName(true);
            this.setUseIdentityHashCode(false);
        }

        private Object readResolve() {
            return CustomToStringStyle.SHORT_PREFIX_MULTI_LINE_STYLE;
        }
    }

}
