package driver;

import org.apache.commons.lang3.StringUtils;

public class PascalCompilerDriver {
    public static void main(String[] args) throws Exception {
        // default output is standard output
        CompilerDriverBuilder builder = null;

        String command = args.length>0 ? args[0] : null;
        String path = args.length>1 ? args[1] : null;

        if (StringUtils.isBlank(command)) {
            System.out.println("Please specify valid arguments");
            System.out.println("Available usage: ");
            System.out.println("- parse");
            System.out.println("- check");
            return;
        }

        if (StringUtils.isBlank(path)) {
            System.out.println("Please specify valid file path");
            return;
        }

        System.out.printf("Source file - %s\n", path);
        System.out.printf("Environment information - OS: %s, Arch: %s, Java version: %s\n",
                System.getProperty("os.name"),
                System.getProperty("os.arch"),
                System.getProperty("java.version"));
        //System.out.println(System.getProperty("os.name"));
        //System.out.println(System.getProperty("os.arch"));
        //System.out.println(System.getProperty("java.version"));

        if (command.equals("parse")) {
            builder = new PascalCompilerDriverBuilder(path).parse();
        }
        if (command.equals("check")) {
            builder = new PascalCompilerDriverBuilder(path).parse().check();
        }

        // gather error count if any
        PascalCompilerDriverBuilder _builder = (PascalCompilerDriverBuilder) builder;
        _builder.showResults();
    }
}
