package driver;

import annotation.TestResourcePath;
import exception.BuiltinException;
import exception.PascalCompilerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import utils.JsonHelper;
import utils.ResourceHelper;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * Driver Arguments Unit Test Cases
 *
 */
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
    public void testCheckArgumentsWithInvalidArguments(String input, PascalCompilerException ex) throws IOException, IllegalArgumentException {
        String path = dir + input;
        String resourceToString = ResourceHelper.getResourceToString(getClass(), path);
        String[] arguments = JsonHelper.parseObjectToArray(resourceToString, String[].class,
                sorter);
        Throwable exception = assertThrows(PascalCompilerException.class, () -> {
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
        DriverArgument driverArgument = PascalCompilerDriver.checkArguments(arguments);
        //Map<String, String> argumentsMap = (Map<String, String>) driverArgument;
        assertAll(
                "check keys of arguments",
                () -> assertNotNull(driverArgument.getDriverCommand()),
                () -> assertNotNull(driverArgument.getPath())
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

}
