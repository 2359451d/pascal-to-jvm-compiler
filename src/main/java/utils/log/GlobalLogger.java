package utils.log;


import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

public class GlobalLogger extends BaseLogger {
    public static final Logger INSTANCE
            = (Logger) LoggerFactory.getLogger(GlobalLogger.class);

    private GlobalLogger() {
    }

    public static void setLevel(Level level) {
        setLevel(INSTANCE,level);
    }

    public static Level getEffectiveLevel() {
        return getEffectiveLevel(INSTANCE);
    }

    /**
     * Lazy logging message with INFO level
     * @param template string template
     * @param messages lambda string arguments which would be evaluated the lazy way
     */
    public static void info(String template, Supplier<?>... messages) {
        info(INSTANCE,template,messages);
    }

    /**
     * Lazy logging message with DEBUG level
     * @param template string template
     * @param messages lambda string arguments which would be evaluated the lazy way
     */
    public static void debug(String template, Supplier<?>... messages) {
       debug(INSTANCE,template,messages);
    }

    /**
     * Lazy logging message with ERROR level
     * @param template string template
     * @param messages lambda string arguments which would be evaluated the lazy way
     */
    public static void error(String template, Supplier<?>... messages) {
        error(INSTANCE,template,messages);
    }
}
