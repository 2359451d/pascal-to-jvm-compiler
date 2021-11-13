package driver;

import ast_visitor.PascalLexer;
import ast_visitor.PascalParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.IOException;
import java.io.PrintStream;

public class PascalParse {
    private static PrintStream out = System.out;

    public static void main(String[] args) throws IOException {
        out.println();
        out.println("Syntactic analysis ...");
        System.out.println(args[0]);
        PascalLexer lexer = new PascalLexer(
                CharStreams.fromFileName(args[0]));
        CommonTokenStream tokens =
                new CommonTokenStream(lexer);
        PascalParser parser = new PascalParser(tokens);
        ParseTree tree = parser.program();
        int errors = parser.getNumberOfSyntaxErrors();
        out.println(errors + " syntactic errors");
        out.println(tree.toStringTree(parser));
    }
}
