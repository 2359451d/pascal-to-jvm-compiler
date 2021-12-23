package util.test;

import annotation.TestResourcePath;

import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;

public class TestUtils {
    public static final String testResourcesBase = Paths.get("src", "test", "resources").toString();

    /**
     * Concatenate value of @TestResourcePath with test resources path ("src/test/resources")
     * @param base
     */
    public static <T> StringBuilder concatenateTestResourcePath(StringBuilder base, Class<?> clazz) {
        TestResourcePath testResourcePath = clazz.getAnnotation(TestResourcePath.class);
        String[] components = testResourcePath.value().split("[\\/]");
        StringBuilder sb = new StringBuilder(base);
        Arrays.stream(components).forEach(
                each ->{
                    sb.append(File.separator);
                    sb.append(each);
                }
        );
        return sb;
    }

    public static File[] getAllFilesInDir(String path) {
        File file = new File(path);
        return file.listFiles();
    }

    public static StringBuilder appendNewSubdirectory(StringBuilder base, String subdirectory) {
        StringBuilder sb = new StringBuilder(base);
        sb.append(File.separator);
        sb.append(subdirectory);
        return sb;
    }
}
