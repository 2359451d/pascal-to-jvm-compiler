package driver;

import annotations.TestResourcePath;
import ast.visitor.PascalCustomLexer;
import ast.visitor.PascalParser;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Rule;
import org.junit.Test;
import utils.test.rules.TestParse;

import java.io.PrintStream;

import static junit.framework.Assert.assertEquals;

@TestResourcePath("testPascalParse/")
public class PascalParseTest {

    //private static final String RESOURCE_PATH = "testPascalParse/";
    private static PrintStream out = System.out;

    //@Rule
    //public TestName testName = new TestName();

    /**
     * Setup lexer & parser specified for each test method
     * Filename is based on the name of each test method
     * e.g testXXX, then tokens & AST would be generated for the file XXX.pas
     */
    @Rule
    public TestParse testParse = new TestParse();

    //@Before
    //public void setup() {
    //    //
    //}

    @Test
    public void testParseHelloWorldWithSuccess() {
        //System.out.println("testParse.getMethodName() = " + testParse.getMethodName());

        PascalCustomLexer lexer = testParse.getLexer();
        // create a parser, processing the content of the buffer of lexer
        PascalParser parser = testParse.getParser();
        ParseTree tree = parser.program();
        //ANTLRErrorStrategy errorHandler = parser.getErrorHandler();

        int tokenError = lexer.getTokenErrors();
        int syntaxErrors = parser.getNumberOfSyntaxErrors();
        out.println(tokenError + " token recognition errors");
        out.println(syntaxErrors + " syntactic errors");
        assertEquals(0, tokenError);
        assertEquals(0, syntaxErrors);
    }

    @Test
    public void testParseHelloWorldWithError() {
        PascalCustomLexer lexer = testParse.getLexer();
        PascalParser parser = testParse.getParser();
        ParseTree tree = parser.program();

        int tokenError = lexer.getTokenErrors();
        int syntaxErrors = parser.getNumberOfSyntaxErrors();
        assertEquals(1, tokenError);
        assertEquals(1, syntaxErrors);
    }
}
