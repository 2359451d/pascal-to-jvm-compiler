package driver;

import annotation.TestResourcePath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.platform.commons.util.StringUtils;
import utils.test.TestUtils;
import utils.test.extension.UnitTestLoggerExtension;

import java.io.File;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;


/**
 * Syntactic Analysis Regression Unit Test Cases
 */
@TestResourcePath("driver/testPascalCompilerDriver/testParse/")
@Tag("regression")
@DisplayName("Syntactic Regression Unit Test - [PascalCompilerDriverBuilderParseUnitTest.java]")
public class PascalCompilerDriverBuilderParseUnitTest {

    private static String successDir = "testParseWithSuccess";
    //private static String errorDir = "testParseWithError";

    @RegisterExtension
    static UnitTestLoggerExtension extension = new UnitTestLoggerExtension("parse",
            "Syntactic Regression Unit Test - [PascalCompilerDriverBuilderParseUnitTest.java]");

    /**
     * Boxing all the source files that contains no syntactic errors into Arguments
     */
    private static Stream<Arguments> noErrorSourceFileListProvider() {
        StringBuilder newPath = TestUtils.appendNewSubdirectory(extension.getBase(), successDir);
        String fullPath = newPath.toString();
        File[] files = TestUtils.getAllFilesInDir(fullPath);
        return files == null ? Stream.of(Arguments.of(""))
                : Arrays.stream(files).map(each -> Arguments.of(each.getPath()));
    }

    /**
     * Test batch source files without syntactic errors
     */
    @ParameterizedTest(name = "{index} - Source: {0}")
    @MethodSource("noErrorSourceFileListProvider")
    public void testParseWithSuccess(String path) throws Exception {
        if (StringUtils.isBlank(path)) throw new Exception("No test resources found!");
        extension.addNewArgument(path);
        assertDoesNotThrow(() -> {
            DriverArgument driverArgument = new DriverArgument(DriverCommand.PARSE, path);
            new PascalCompilerDriverBuilder(driverArgument).parse();
        });
    }

    /**
     * Boxing all the source files that contains syntactic errors into Arguments
     */
    //private static Stream<Arguments> errorSourceFileListProvider() {
    //    StringBuilder newPath = TestUtils.appendNewSubdirectory(extension.getBase(), errorDir);
    //    String fullPath = newPath.toString();
    //    File[] files = TestUtils.getAllFilesInDir(fullPath);
    //    return files == null ? Stream.of(Arguments.of(""))
    //            : Arrays.stream(files).map(each -> Arguments.of(each.getPath()));
    //}
    //
    ///**
    // * Test batch source files with syntactic errors thrown
    // */
    //@ParameterizedTest(name = "{index} - Source: {0}")
    //@MethodSource("errorSourceFileListProvider")
    //public void testParseWithError(String path) throws Exception {
    //    if (StringUtils.isBlank(path)) throw new Exception("No test resources found!");
    //    extension.addNewArgument(path);
    //    assertThrows(BuiltinException.PARSE_FAILED.getException().getClass(), () -> {
    //        DriverArgument driverArgument = new DriverArgument(DriverCommand.PARSE, path);
    //        new PascalCompilerDriverBuilder(driverArgument).parse();
    //    });
    //}
}
