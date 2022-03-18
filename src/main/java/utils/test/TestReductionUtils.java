package utils.test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestReductionUtils {

    /**
     * Rename program heading of the Pascal source file
     * Based on the file name
     */
    public static void sourceFileRenaming() {

    }

    public static void main(String[] args) throws IOException {
        //base dir of test resources
        String path = args[0];
        Path subPath = Path.of(path);
        Path resourceDirectory = Paths.get("src", "test", "resources");

        File file = new File(resourceDirectory.toString(), path);
        File[] files = file.listFiles();

        for (File each : files) {
            if (each.isFile()) {
                final String fileNamePrefix = each.getName().split("\\.")[0];
                try (Stream<String> stream = Files.lines(Paths.get(each.getPath()))) {
                    final List<String> collect = stream
                            .map(line -> {
                                if (line.matches("program.*;")) {
                                    return "program " + fileNamePrefix + ";";
                                } else return line;
                            })
                            .collect(Collectors.toList());
                    Files.write(Paths.get(each.getPath()), collect);
                }
            }
        }

        //PascalCustomLexer lexer;
        //CommonTokenStream tokens;
        //
        //
        //for (File each : files) {
        //    System.out.println("each.getName() = " + each.getName());
        //
        //    lexer = new PascalCustomLexer(CharStreams.fromFileName(
        //            each.getPath()));
        //    tokens = new CommonTokenStream(lexer);
        //    PascalParser parser = new PascalParser(tokens);
        //    PascalParser.ProgramContext program = parser.program();
        //
        //    //PascalParser.ProgramHeadingContext programHeadingContext = (PascalParser.ProgramHeadingContext) program;
        //    //PascalParser.ProgramContext tree = (PascalParser.ProgramContext) programHeadingContext;
        //    RenameVisitor renameVisitor = new RenameVisitor(tokens, each.getName());
        //    renameVisitor.visit(program);
        //
        //    System.out.println("program.toStringTree() = " + program.toStringTree());
        //    System.out.println(renameVisitor.getReplacedCode());
        //}


    }
}
