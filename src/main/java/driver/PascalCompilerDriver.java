package driver;

import exception.PascalCompilerException;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class PascalCompilerDriver {

    private static Set<String> commandMap = Set.of(
            "parse", "check"
    );

    public static Map<String, String> checkArguments(String[] args) throws PascalCompilerException {
        String command = args.length > 0 ? args[0] : null;
        String path = args.length > 1 ? args[1] : null;

        if (StringUtils.isBlank(command) || !commandMap.contains(command)) {
            throw new PascalCompilerException("Invalid command. Available Usage: parse, check");
        }

        if (StringUtils.isBlank(path) || !path.endsWith(".pas")) {
            throw new PascalCompilerException("Invalid file path");
        }

        return Map.of("command", command, "path", path);
    }

    /**
    public static void printInformation(String path) {
        System.out.printf("Source file - %s\n", path);
        System.out.printf("Environment information - OS: %s, Arch: %s, Java version: %s\n",
                System.getProperty("os.name"),
                System.getProperty("os.arch"),
                System.getProperty("java.version"));
    }
     **/

    public static CompilerDriverBuilder constructDriverAndBuild(Map<String, String> argumentsMap) throws IOException, PascalCompilerException {
        // if arguments are valid: command valid, path valid
        String command = argumentsMap.get("command");
        String path = argumentsMap.get("path");

        // default output is standard output
        CompilerDriverBuilder builder = null;

        if (command.equals("parse")) {
            builder = new PascalCompilerDriverBuilder(path).parse();
        }
        if (command.equals("check")) {
            // throw PascalCompilerException, if syntactic analysis not being executed yet
            builder = new PascalCompilerDriverBuilder(path).parse().check();
        }
        return builder;
    }

    public static void main(String[] args) {
        Map<String, String> argumentsMap = null;
        try {
            argumentsMap = checkArguments(args);
            constructDriverAndBuild(argumentsMap);
        } catch (PascalCompilerException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
