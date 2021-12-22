package driver;

import ast.visitor.PascalBaseVisitor;
import ast.visitor.PascalCustomLexer;
import ast.visitor.PascalLexer;
import ast.visitor.PascalParser;
import ast.visitor.impl.PascalCheckerVisitor;
import exception.PascalCompilerException;
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
    private int syntaxErrors;
    private int tokenErrors;
    private int contextualErrors;

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

    private void printInformation(String path) {
        System.out.printf("Source file - %s\n", path);
        System.out.printf("Environment information - OS: %s, Arch: %s, Java version: %s\n",
                System.getProperty("os.name"),
                System.getProperty("os.arch"),
                System.getProperty("java.version"));
    }

    @Override
    public CompilerDriverBuilder parse() throws IOException, PascalCompilerException {
        lexer = new PascalCustomLexer(
                CharStreams.fromFileName(fileName));

        // print prepared information(environment, etc.)
        printInformation(fileName);

        tokens = new CommonTokenStream(lexer);
        parser = new PascalParser(tokens);
        tree = parser.program();
        showSyntacticErrors();

        if (parser.getNumberOfSyntaxErrors() > 0) {
            throw new PascalCompilerException("Syntactic analysis failed...");
        }

        return this;
    }

    @Override
    public CompilerDriverBuilder check() throws PascalCompilerException, IOException {
        if (tokens == null || tree == null) {
            throw new PascalCompilerException("Syntactic analysis not being executed yet...");
        }

        checker = new PascalCheckerVisitor(tokens);
        checker.visit(tree);
        showContextualErrors();

        PascalCheckerVisitor _checker = (PascalCheckerVisitor) checker;
        if (_checker.getNumberOfContextualErrors() > 0) {
            throw new PascalCompilerException("Contextual analysis failed...");
        }

        return this;
    }

    private void println(String str) throws IOException {
        OutputStream out = getOut();
        if (out instanceof PrintStream) {
            PrintStream outStd = new PrintStream(out);
            outStd.println(str);
            return;
        }
        try {
            out.write(str.getBytes());
            out.write("\n".getBytes());
        } catch (IOException e) {
            throw e;
        } finally {
            out.close();
        }
    }

    private void showSyntacticErrors() throws IOException {
        if (parser == null && checker == null) {
            println("Compilation not start yet, nothing to be shown...");
            return;
        }
        syntaxErrors = parser.getNumberOfSyntaxErrors();
        tokenErrors = ((PascalCustomLexer) lexer).getTokenErrors();
        println("Syntactic analysis Results: ");
        println(tokenErrors + " token recognition errors");
        println(syntaxErrors + " syntactic errors");
    }

    private void showContextualErrors() throws IOException {
        if (checker != null) {
            PascalCheckerVisitor _checker = (PascalCheckerVisitor) checker;
            contextualErrors = _checker.getNumberOfContextualErrors();
            println("Contextual analysis Results: ");
            println(contextualErrors + " contextual errors");
        }
    }

    public int getSyntaxErrors() {
        return syntaxErrors;
    }

    public int getTokenErrors() {
        return tokenErrors;
    }

    public int getContextualErrors() {
        return contextualErrors;
    }

    /**
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
 **/

}

