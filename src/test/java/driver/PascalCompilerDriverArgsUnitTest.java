package driver;

import annotation.TestResourcePath;
import exception.BuiltinException;
import exception.PascalCompilerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import util.JsonHelper;
import util.ResourceHelper;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@TestResourcePath("testPascalCompilerDriver/testCheckArguments/")
public class PascalCompilerDriverArgsUnitTest {

    private String dir;
    private JsonHelper.Sorter sorter;

    @BeforeEach
    public void setup() {
        TestResourcePath testResourcePath = getClass().getAnnotation(TestResourcePath.class);
        dir = testResourcePath.value();
        sorter = new JsonHelper.Sorter(new String[]{
                "command", "path"
        });
    }

    //private static final PascalCompilerException invalidCommandException = new PascalCompilerException("Invalid command. Available Usage: parse, check");
    //private static final PascalCompilerException invalidPathException = new PascalCompilerException("Invalid file path");

    private static Stream<Arguments> invalidArgumentsListProvider() {
        return Stream.of(
                arguments("invalidCommand.json", BuiltinException.INVALID_COMMAND.getException()),
                arguments("invalidPath.json", BuiltinException.INVALID_PATH.getException())
        );
    }

    private static Stream<Arguments> validArgumentsListProvider() {
        return Stream.of(
                arguments("validParseArguments.json"),
                arguments("validCheckArguments.json")
        );
    }

    @ParameterizedTest
    @MethodSource("invalidArgumentsListProvider")
    public void testCheckArgumentsWithInvalidArguments(String input, PascalCompilerException ex) throws IOException, PascalCompilerException {
        String path = dir + input;
        String resourceToString = ResourceHelper.getResourceToString(getClass(), path);
        String[] arguments = JsonHelper.parseObjectToArray(resourceToString, String[].class,
                sorter);
        Throwable exception = assertThrows(ex.getClass(), () -> {
            PascalCompilerDriver.checkArguments(arguments);
        });
        assertEquals(ex.getMessage(), exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource("validArgumentsListProvider")
    public void testCheckArgumentsWithValidArguments(String input) throws IOException, PascalCompilerException {
        String path = dir + input;
        String resourceToString = ResourceHelper.getResourceToString(getClass(), path);
        String[] arguments = JsonHelper.parseObjectToArray(resourceToString, String[].class,
                sorter);
        Map<String, String> argumentsMap = PascalCompilerDriver.checkArguments(arguments);
        assertAll(
                "check keys of arguments",
                () -> assertTrue(argumentsMap.containsKey("command")),
                () -> assertTrue(argumentsMap.containsKey("path"))
        );

    }

    @ParameterizedTest
    @MethodSource("validArgumentsListProvider")
    public void testMainWithValidArguments(String input) throws IOException, PascalCompilerException {
        String path = dir + input;
        String resourceToString = ResourceHelper.getResourceToString(getClass(), path);
        String[] arguments = JsonHelper.parseObjectToArray(resourceToString, String[].class,
                sorter);
        assertDoesNotThrow(() -> {
            PascalCompilerDriver.main(arguments);
        });
    }


    // test cases
    //@Parameterized.Parameters(name = "{index}: resourceName:{0}")
    //public static Object[] data() {
    //    return new Object[]{
    //            "invalidCommand.json",
    //            "invalidPath.json",
    //            "validParseArguments.json"
    //    };
    //}

    //@Parameterized.Parameter
    //public String input;
    //
    //@Test
    //public void test() throws Exception {
    //    String path = dir + input;
    //    String resourceToString = ResourceHelper.getResourceToString(getClass(), path);
    //    String[] arguments = JsonHelper.parseObjectToArray(resourceToString, String[].class,
    //            sorter);
    //    PascalCompilerDriver.main(arguments);
    //}


    //@Test
    //public void testRunWithInvalidCommand() throws Exception {
    //    String path = dir + "invalidCommand.json";
    //    String resourceToString = ResourceHelper.getResourceToString(getClass(), path);
    //    String[] arguments = JsonHelper.parseObjectToArray(resourceToString, String[].class,
    //            sorter);
    //    PascalCompilerDriver.main(arguments);
    //}
    //
    //@Test
    //public void testRunWithInvalidPath() throws Exception {
    //    String path = dir + "invalidPath.json";
    //    String resourceToString = ResourceHelper.getResourceToString(getClass(), path);
    //    String[] arguments = JsonHelper.parseObjectToArray(resourceToString, String[].class,
    //            sorter);
    //    PascalCompilerDriver.main(arguments);
    //}
    //
    //@Test
    //public void testRunWithValidArguments() throws Exception {
    //    String path = dir + "validarguments.json";
    //    String resourceToString = ResourceHelper.getResourceToString(getClass(), path);
    //    String[] arguments = JsonHelper.parseObjectToArray(resourceToString, String[].class,
    //            sorter);
    //    PascalCompilerDriver.main(arguments);
    //}

}
