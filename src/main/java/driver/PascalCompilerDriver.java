package driver;

import ch.qos.logback.classic.Level;
import exception.BuiltinException;
import exception.PascalCompilerException;
import org.apache.commons.lang3.StringUtils;
import utils.log.GlobalLogger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;

/**
 * Main Driver Entrance of the compiler
 */
public class PascalCompilerDriver {

    private static Set<String> commandMap = Set.of(
            "parse", "check", "run"
    );

    public static DriverArgument checkArguments(String[] args) throws PascalCompilerException {
        String command = args.length > 0 ? args[0] : null;
        String path = args.length > 1 ? args[1] : null;

        //if (StringUtils.isBlank(command) || !commandMap.contains(command)) {
        //    //throw new PascalCompilerException("Invalid command. Available Usage: parse, check");
        //    throw BuiltinException.INVALID_COMMAND.getException();
        //}
        if (StringUtils.isBlank(command)) {
            //throw new PascalCompilerException("Invalid command. Available Usage: parse, check");
            throw BuiltinException.INVALID_COMMAND.getException();
        }

        DriverCommand driverCommand = null;
        try {
            driverCommand = DriverCommand.valueOf(command.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw BuiltinException.INVALID_COMMAND.getException();
        }

        if (StringUtils.isBlank(path) || !path.endsWith(".pas")) {
            //throw new PascalCompilerException("Invalid file path. Please specify valid path");
            throw BuiltinException.INVALID_PATH.getException();
        }
        final boolean exists = new File(path).exists();
        if (!exists) throw new PascalCompilerException(new FileNotFoundException("File not found: "+path).getMessage());

        return new DriverArgument(driverCommand,path);
    }

    static CompilerDriverBuilder builder = null;

    /**
     * public static void printInformation(String path) {
     * System.out.printf("Source file - %s\n", path);
     * System.out.printf("Environment information - OS: %s, Arch: %s, Java version: %s\n",
     * System.getProperty("os.name"),
     * System.getProperty("os.arch"),
     * System.getProperty("java.version"));
     * }
     **/

    public static CompilerDriverBuilder constructDriverAndBuild(DriverArgument driverArgument) throws IOException, PascalCompilerException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        // if arguments are valid: command valid, path valid
        String commandName = driverArgument.getCommandName();

        // default output is standard output
        if (commandName.equals("parse")) {
            builder = new PascalCompilerDriverBuilder(driverArgument).parse();
        }
        if (commandName.equals("check")) {
            // throw PascalCompilerException, if syntactic analysis not being executed yet
            builder = new PascalCompilerDriverBuilder(driverArgument).parse().check();
        }
        if (commandName.equals("run")) {
            builder = new PascalCompilerDriverBuilder(driverArgument).parse().check().run();
        }
        return builder;
    }

    public static void main(String[] args) {
        try {
            DriverArgument driverArgument = checkArguments(args);
            constructDriverAndBuild(driverArgument);
        } catch (PascalCompilerException e) {
            GlobalLogger.info("{}", e::getMessage);
        } catch (IOException | InvocationTargetException | NoSuchMethodException | IllegalAccessException
                e) {
            //GlobalLogger.error("{}", e::getStackTrace);
            e.printStackTrace();
        }
    }
}
