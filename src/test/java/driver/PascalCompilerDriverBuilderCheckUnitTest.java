package driver;

import annotation.TestResourcePath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.platform.commons.util.StringUtils;
import org.junit.platform.suite.api.SuiteDisplayName;
import utils.test.TestUtils;
import utils.test.extension.UnitTestLoggerExtension;

import java.io.File;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Contextual Analysis Regression Unit Test Cases
 */
@TestResourcePath("driver/testPascalCompilerDriver/testCheck/")
@Tag("regression")
@DisplayName("Contextual Regression Unit Test - [PascalCompilerDriverBuilderCheckUnitTest.java]")
public class PascalCompilerDriverBuilderCheckUnitTest {

    private static String successDir = "testCheckWithSuccess";
    //private static String errorDir = "testCheckWithError";

    @RegisterExtension
    static UnitTestLoggerExtension extension = new UnitTestLoggerExtension("check",
            "Contextual Regression Unit Test - [PascalCompilerDriverBuilderCheckUnitTest.java]");

    /**
     * Boxing all the source files that contains no contextual errors into Arguments
     */
    private static Stream<Arguments> noErrorSourceFileListProvider() {
        StringBuilder newPath = TestUtils.appendNewSubdirectory(extension.getBase(), successDir);
        String fullPath = newPath.toString();
        File[] files = TestUtils.getAllFilesInDir(fullPath);
        return files == null ? Stream.of(Arguments.of(""))
                : Arrays.stream(files).map(each -> Arguments.of(each.getPath()));
    }

    /**
     * Test batch source files without contextual errors
     */
    @ParameterizedTest(name = "{index} - Source: {0}")
    @MethodSource("noErrorSourceFileListProvider")
    public void testCheckWithSuccess(String path) throws Exception {
        if (StringUtils.isBlank(path)) throw new Exception("No test resources found!");
        extension.addNewArgument(path);
        assertDoesNotThrow(() -> {
            DriverArgument driverArgument = new DriverArgument(DriverCommand.CHECK, path);
            new PascalCompilerDriverBuilder(driverArgument).parse().check();
        });
    }

    /**
     * Boxing all the source files that contains contextual errors into Arguments
     */
    //private static Stream<Arguments> errorSourceFileListProvider() {
    //    StringBuilder newPath = TestUtils.appendNewSubdirectory(extension.getBase(), errorDir);
    //    String fullPath = newPath.toString();
    //    File[] files = TestUtils.getAllFilesInDir(fullPath);
    //    return files == null ? Stream.of(Arguments.of(""))
    //            : Arrays.stream(files)
    //            .filter(each->each.getName().endsWith(".pas"))
    //            .map(each -> Arguments.of(each.getPath()));
    //}
    //
    ///**
    // * Test batch source files with contextual errors thrown
    // */
    //@ParameterizedTest(name = "{index} - Source: {0}")
    //@MethodSource("errorSourceFileListProvider")
    //public void testCheckWithError(String path) throws Exception {
    //    if (StringUtils.isBlank(path)) throw new Exception("No test resources found!");
    //    extension.addNewArgument(path);
    //    assertThrows(BuiltinException.CHECK_FAILED.getException().getClass(), () -> {
    //        DriverArgument driverArgument = new DriverArgument(DriverCommand.CHECK, path);
    //        new PascalCompilerDriverBuilder(driverArgument).parse().check();
    //    });
    //}
}
