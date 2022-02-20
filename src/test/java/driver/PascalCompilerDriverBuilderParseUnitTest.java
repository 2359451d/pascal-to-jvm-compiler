package driver;

import annotation.TestResourcePath;
import exception.BuiltinException;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.platform.commons.util.StringUtils;
import utils.test.TestUtils;
import utils.test.extension.TestLoggerExtension;

import java.io.File;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;


// Resource relative path to test resources
//@TestResourcePath("driver\\testPascalCompilerDriver\\testParse\\")
@TestResourcePath("driver/testPascalCompilerDriver/testParse/")
public class PascalCompilerDriverBuilderParseUnitTest {

    //private static StringBuilder base;
    private static String successDir = "testParseWithSuccess";
    private static String errorDir = "testParseWithError";
    //private static String[] argumentsArr;

    @RegisterExtension
    static TestLoggerExtension extension = new TestLoggerExtension("parse");

    //@BeforeAll
    //public static void setup(TestInfo testInfo) {
    //System.out.printf("****** Setup for Test Class [%s] ******\n", testInfo.getDisplayName());
    //// base testing resources path
    //base = new StringBuilder(TestUtils.testResourcesBase);
    //base = TestUtils.concatenateTestResourcePath(base, PascalCompilerDriverBuilderParseUnitTest.class);
    ////ENDs with correct Path in test resources
    //System.out.printf("Base Test Resource Path = %s\n" , base);

    //argumentsArr = new String[2];
    //argumentsArr[0] = "parse";

    //System.out.println("****** Setup Ends ******\n");
    //}

    //@BeforeEach
    //void printStartInfo(TestInfo testInfo) {
    //    System.out.printf("Current Test Method - [%s]\n", testInfo.getTestMethod().get().getName());
    //}
    //
    //@AfterEach
    //void printEndInfo(TestInfo testInfo) {
    //    System.out.println("Current Test Method Ends");
    //    System.out.println();
    //}

    private static Stream<Arguments> noErrorSourceFileListProvider() {
        StringBuilder newPath = TestUtils.appendNewSubdirectory(extension.getBase(), successDir);
        String fullPath = newPath.toString();
        File[] files = TestUtils.getAllFilesInDir(fullPath);
        return files == null ? Stream.of(Arguments.of(""))
                : Arrays.stream(files).map(each -> Arguments.of(each.getPath()));
    }

    /**
     * Test batch source files
     */
    @ParameterizedTest(name = "{index} - Source: {0}")
    @MethodSource("noErrorSourceFileListProvider")
    public void testParseWithSuccess(String path) throws Exception {
        if (StringUtils.isBlank(path)) throw new Exception("No test resources found!");
        extension.addNewArgument(path);
        assertDoesNotThrow(() -> {
            new PascalCompilerDriverBuilder(path).parse();
        });
    }

    private static Stream<Arguments> errorSourceFileListProvider() {
        StringBuilder newPath = TestUtils.appendNewSubdirectory(extension.getBase(), errorDir);
        String fullPath = newPath.toString();
        File[] files = TestUtils.getAllFilesInDir(fullPath);
        return files == null ? Stream.of(Arguments.of(""))
                : Arrays.stream(files).map(each -> Arguments.of(each.getPath()));
    }

    @ParameterizedTest(name = "{index} - Source: {0}")
    @MethodSource("errorSourceFileListProvider")
    public void testParseWithError(String path) throws Exception {
        if (StringUtils.isBlank(path)) throw new Exception("No test resources found!");
        extension.addNewArgument(path);
        assertThrows(BuiltinException.PARSE_FAILED.getException().getClass(), () -> {
            new PascalCompilerDriverBuilder(path).parse();
        });
    }
}
