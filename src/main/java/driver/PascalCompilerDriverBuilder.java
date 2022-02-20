package driver;

import ast.visitor.PascalBaseVisitor;
import ast.visitor.PascalCustomLexer;
import ast.visitor.PascalLexer;
import ast.visitor.PascalParser;
import ast.visitor.impl.PascalCheckerVisitor;
import ast.visitor.impl.PascalEncoderVisitor;
import exception.BuiltinException;
import exception.PascalCompilerException;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import type.Type;
import type.TypeDescriptor;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;

public class PascalCompilerDriverBuilder extends CompilerDriverBuilder {

    private PascalLexer lexer;
    private CommonTokenStream tokens; // token buffer
    private PascalParser parser;
    private PascalBaseVisitor<TypeDescriptor> checker;
    private PascalEncoderVisitor runner;
    private ParseTree tree;
    private int syntaxErrors;
    private int tokenErrors;
    private int contextualErrors;

    private String fileName;

    /**
     * Create a new Driver Builder, with default standard output
     * @param fileName - Source file
     */
    public PascalCompilerDriverBuilder(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Create a new Driver Builder with specific output
     * @param outputStream - Specific output
     * @param fileName - Source file
     */
    public PascalCompilerDriverBuilder(OutputStream outputStream, String fileName) {
        this.setOut(outputStream);
        this.fileName = fileName;
    }

    /**
     * Print out information of source file & environment
     * @param path - Source file
     */
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

        if (syntaxErrors > 0 || tokenErrors > 0) {
            //throw new PascalCompilerException("Syntactic analysis failed...");
            throw BuiltinException.PARSE_FAILED.getException();
        }

        return this;
    }

    @Override
    public CompilerDriverBuilder check() throws PascalCompilerException, IOException {
        if (tokens == null || tree == null) {
            //throw new PascalCompilerException("Syntactic analysis not being executed yet...");
            throw BuiltinException.PARSE_NOT_START.getException();
        }

        checker = new PascalCheckerVisitor(tokens);
        checker.visit(tree);
        showContextualErrors();

        //PascalCheckerVisitor _checker = (PascalCheckerVisitor) checker;
        if (contextualErrors > 0) {
            //throw new PascalCompilerException("Contextual analysis failed...");
            throw BuiltinException.CHECK_FAILED.getException();
        }

        return this;
    }

    @Override
    public CompilerDriverBuilder run() throws PascalCompilerException, IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        if (tokens == null || tree == null) {
            //throw new PascalCompilerException("Syntactic analysis not being executed yet...");
            throw BuiltinException.PARSE_NOT_START.getException();
        }

        Path parentDir = Path.of(fileName).getParent();
        System.out.println("parentDir = " + parentDir);

        runner = new PascalEncoderVisitor(parentDir.toString(), tokens);
        runner.visit(tree);
        PascalEncoderVisitor.run();
        return this;
    }



    private void println(String str) throws IOException {
        OutputStream out = getOut();

        // if output is standard
        if (out instanceof PrintStream) {
            PrintStream outStd = new PrintStream(out);
            outStd.println(str);
            return;
        }

        // when output is not PrintStream
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
            // REMARK: not a exception
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

