package utils;

public final class ResourceHelper {
    /**
     * Initialisation is not allowed
     */
    private ResourceHelper() {
    }

    public static String concatenateFileNameWithSuffix(String fileName, String suffix) {
        return fileName + "." + suffix;
    }
}
