package driver;

import ast.visitor.PascalLexer;
import ast.visitor.PascalParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import ast.visitor.impl.PascalCheckerVisitor;

import java.io.IOException;
import java.io.PrintStream;

public class PascalCheck {
    private static PrintStream out = System.out;

    public static void main(String[] args) throws IOException {
        out.println();
        out.println("Contexual analysis ...");
        System.out.println(args[0]);
        PascalLexer lexer = new PascalLexer(
                CharStreams.fromFileName(args[0]));
        CommonTokenStream tokens =
                new CommonTokenStream(lexer);
        PascalParser parser = new PascalParser(tokens);
        PascalParser.ProgramContext tree = parser.program();

        PascalCheckerVisitor checker = new PascalCheckerVisitor(tokens);
        checker.visit(tree);

        int errors = parser.getNumberOfSyntaxErrors();
        out.println(errors + " contexual errors");
        //out.println(tree.toStringTree(parser));
    }
}
