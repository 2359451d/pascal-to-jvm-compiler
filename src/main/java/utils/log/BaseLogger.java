package utils.log;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

import java.util.Arrays;
import java.util.function.Supplier;

public abstract class BaseLogger {

    public static void setLevel(Logger INSTANCE, Level level) {
        INSTANCE.setLevel(level);
    }

    public static Level getEffectiveLevel(Logger INSTANCE) {
        return INSTANCE.getEffectiveLevel();
    }

    /**
     * Lazy logging message with INFO level
     * @param template string template
     * @param messages lambda string arguments which would be evaluated the lazy way
     */
    public static void info(Logger INSTANCE, String template, Supplier<?>... messages) {
        if (INSTANCE.isInfoEnabled()) {
            INSTANCE.info(template, Arrays.stream(messages).map(Supplier::get).toArray());
        }
    }

    /**
     * Lazy logging message with DEBUG level
     * @param template string template
     * @param messages lambda string arguments which would be evaluated the lazy way
     */
    public static void debug(Logger INSTANCE, String template, Supplier<?>... messages) {
        if (INSTANCE.isDebugEnabled()) {
            INSTANCE.debug(template, Arrays.stream(messages).map(Supplier::get).toArray());
        }
    }

    /**
     * Lazy logging message with ERROR level
     * @param template string template
     * @param messages lambda string arguments which would be evaluated the lazy way
     */
    public static void error(Logger INSTANCE, String template, Supplier<?>... messages) {
        if (INSTANCE.isErrorEnabled()) {
            INSTANCE.error(template, Arrays.stream(messages).map(Supplier::get).toArray());
        }
    }
}
