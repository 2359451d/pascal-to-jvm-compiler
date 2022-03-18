package driver;

import ast.visitor.PascalBaseVisitor;
import ast.visitor.PascalCustomLexer;
import ast.visitor.PascalLexer;
import ast.visitor.PascalParser;
import ast.visitor.impl.PascalCheckerVisitor;
import ast.visitor.impl.PascalEncoderVisitor;
import ast.visitor.listeners.CustomConsoleErrorListener;
import ch.qos.logback.classic.Level;
import exception.BuiltinException;
import exception.PascalCompilerException;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import type.TypeDescriptor;
import utils.log.GlobalLogger;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.Set;

public class PascalCompilerDriverBuilder extends CompilerDriverBuilder {

    private PascalLexer lexer;
    private CommonTokenStream tokens; // token buffer
    public static PascalParser parser;
    private PascalBaseVisitor<TypeDescriptor> checker;
    private PascalEncoderVisitor runner;
    private ParseTree tree;
    private int syntaxErrors;
    private int tokenErrors;
    private int contextualErrors;

    private String fileName;
    private DriverCommand command;


    /**
     * Create a new Driver Builder, with default standard output
     *
     */
    public PascalCompilerDriverBuilder(DriverArgument driverArgument) {
        //this.fileName = fileName;
        //this.driverInformation = new LinkedHashSet<>();
        //// generate & stack prepared information(environment, etc.)
        //generateEnvInformation(fileName);
        this(null, driverArgument);
    }

    /**
     * Create a new Driver Builder with specific output
     *
     */
    public PascalCompilerDriverBuilder(OutputStream outputStream, DriverArgument driverArgument) {
        this.setOut(outputStream);
        this.fileName = driverArgument.getPath();
        this.command = driverArgument.getDriverCommand();
        // generate & stack prepared information(environment, etc.)
        generateEnvInformation(fileName);
    }

    /**
     * Generate and stack information of source file & environment
     *
     * @param path - Source file
     */
    private void generateEnvInformation(String path) {
        GlobalLogger.info("Source file - {}", () -> path);
        GlobalLogger.info("Environment information - OS: {}, Arch: {}, Java version: {}",
                () -> System.getProperty("os.name"),
                () -> System.getProperty("os.arch"),
                () -> System.getProperty("java.version"));
    }

    @Override
    public CompilerDriverBuilder parse() throws IOException, PascalCompilerException {
        lexer = new PascalCustomLexer(
                CharStreams.fromFileName(fileName));
        lexer.removeErrorListeners();
        lexer.addErrorListener(CustomConsoleErrorListener.INSTANCE);

        tokens = new CommonTokenStream(lexer);
        parser = new PascalParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(CustomConsoleErrorListener.INSTANCE);
        tree = parser.program();
        generateSyntacticInformation();

        if (syntaxErrors > 0 || tokenErrors > 0) {
            //throw new PascalCompilerException("Syntactic analysis failed...");
            GlobalLogger.setLevel(Level.INFO);
            throw BuiltinException.PARSE_FAILED.getException();
        }

        return this;
    }

    @Override
    public CompilerDriverBuilder check() throws PascalCompilerException, IOException {
        if (tokens == null || tree == null) {
            //throw new PascalCompilerException("Syntactic analysis not being executed yet...");
            GlobalLogger.setLevel(Level.INFO);
            throw BuiltinException.PARSE_NOT_START.getException();
        }

        checker = new PascalCheckerVisitor(tokens);
        checker.visit(tree);
        generateContextualInformation();

        //PascalCheckerVisitor _checker = (PascalCheckerVisitor) checker;
        if (contextualErrors > 0) {
            //throw new PascalCompilerException("Contextual analysis failed...");
            GlobalLogger.setLevel(Level.INFO);
            throw BuiltinException.CHECK_FAILED.getException();
        }

        return this;
    }

    @Override
    public CompilerDriverBuilder run() throws PascalCompilerException, IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        if (tokens == null || tree == null) {
            //throw new PascalCompilerException("Syntactic analysis not being executed yet...");
            GlobalLogger.setLevel(Level.INFO);
            throw BuiltinException.PARSE_NOT_START.getException();
        }


        Path parentDir = Path.of(fileName).getParent();
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

    private void generateSyntacticInformation() throws IOException {
        syntaxErrors = parser.getNumberOfSyntaxErrors();
        tokenErrors = ((PascalCustomLexer) lexer).getTokenErrors();
        if (command.equals(DriverCommand.PARSE) || syntaxErrors > 0 || tokenErrors > 0) {
            GlobalLogger.info("Syntactic analysis Results: ");
            GlobalLogger.info("{} token recognition errors", () -> tokenErrors);
            GlobalLogger.info("{} syntactic errors", () -> syntaxErrors);
        }
        //println("Syntactic analysis Results: ");
        //println(tokenErrors + " token recognition errors");
        //println(syntaxErrors + " syntactic errors");
    }

    private void generateContextualInformation() throws IOException {
        if (checker != null) {
            PascalCheckerVisitor _checker = (PascalCheckerVisitor) checker;
            contextualErrors = _checker.getNumberOfContextualErrors();
            //println("Contextual analysis Results: ");
            //println(contextualErrors + " contextual errors");
            if (command.equals(DriverCommand.CHECK) || contextualErrors>0) {
                GlobalLogger.info("Contextual analysis results: ");
                GlobalLogger.info("{} contextual errors", () -> contextualErrors);
            }
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

}

