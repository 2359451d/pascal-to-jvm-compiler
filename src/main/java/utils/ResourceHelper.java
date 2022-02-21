package utils;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

public final class ResourceHelper {
    /**
     * Initialisation is not allowed
     */
    private ResourceHelper() {
    }

    public static String concatenateFileNameWithSuffix(String fileName, String suffix) {
        return fileName + "." + suffix;
    }

    public static InputStream getResource(Class<?> clazz, String path) {
        return clazz.getResourceAsStream(path);
    }

    public static String getResourceToString(Class<?> clazz, String path) throws IOException {
        InputStream resource = getResource(clazz, path);
        return IOUtils.toString(resource);
    }

}
