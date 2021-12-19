package driver;

import ast.visitor.PascalBaseVisitor;
import ast.visitor.PascalCustomLexer;
import ast.visitor.PascalLexer;
import ast.visitor.PascalParser;
import ast.visitor.impl.PascalCheckerVisitor;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import type.Type;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class PascalCompilerDriverBuilder extends CompilerDriverBuilder {

    private PascalLexer lexer;
    private CommonTokenStream tokens; // token buffer
    private PascalParser parser;
    private PascalBaseVisitor<Type> checker;
    private ParseTree tree;

    private String fileName;

    public PascalCompilerDriverBuilder(OutputStream outputStream) {
        this.setOut(outputStream);
    }

    public PascalCompilerDriverBuilder(String fileName) {
        this.fileName = fileName;
    }

    public PascalCompilerDriverBuilder(OutputStream outputStream, String fileName) {
        this.setOut(outputStream);
        this.fileName = fileName;
    }

    @Override
    public CompilerDriverBuilder parse() throws IOException {
        lexer = new PascalCustomLexer(
                CharStreams.fromFileName(fileName));
        tokens = new CommonTokenStream(lexer);
        parser = new PascalParser(tokens);
        tree = parser.program();
        return this;
    }

    @Override
    public CompilerDriverBuilder check() throws Exception {
        if (tokens == null || tree == null) {
            throw new Exception("Syntactic analysis not being executed...");
        }
        checker = new PascalCheckerVisitor(tokens);
        checker.visit(tree);
        return this;
    }

    private void println(String str) throws IOException {
        OutputStream out = getOut();
        if (out instanceof PrintStream) {
            PrintStream outStd = new PrintStream(out);
            outStd.println(str);
            return;
        }
        out.write(str.getBytes());
        out.write("\n".getBytes());
    }

    public void showResults() throws IOException {
        if (parser == null && checker == null) {
            println("Compilation not start yet, nothing to be shown...");
            return;
        }

        int syntaxErrors = parser.getNumberOfSyntaxErrors();
        int tokenErrors = ((PascalCustomLexer) lexer).getTokenErrors();
        println("Syntactic analysis Results: ");
        println(tokenErrors + " token recognition errors");
        println(syntaxErrors + " syntactic errors");

        if (checker != null) {
            PascalCheckerVisitor _checker = (PascalCheckerVisitor) checker;
            int contextualErrors = _checker.getNumberOfContextualErrors();
            println("Contextual analysis Results: ");
            println(contextualErrors + " contextual errors");
        }
    }

}

