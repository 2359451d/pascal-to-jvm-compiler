package ast.visitor.listeners;

import driver.PascalCompilerDriverBuilder;
import org.antlr.v4.runtime.ConsoleErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.log.ErrorReporter;

/**
 * Redirect the console output using Logback rather than standard one
 */
public class CustomConsoleErrorListener extends ConsoleErrorListener {
    protected int _tokenErrors=0;
    public static CustomConsoleErrorListener INSTANCE = new CustomConsoleErrorListener();

    //private static final Logger logger
    //        = LoggerFactory.getLogger(PascalCompilerDriverBuilder.class);

    private CustomConsoleErrorListener() {
    }

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
        //super.syntaxError(recognizer, offendingSymbol, line, charPositionInLine, msg, e);
        //if (logger.isErrorEnabled()) logger.error("line " + line + ":" + charPositionInLine + " " + msg);
        ErrorReporter.error("line {} : {} {}",
                ()->line,
                ()->charPositionInLine,
                ()->msg);
        this._tokenErrors++;
        //System.out.println("_tokenErrors = " + _tokenErrors);
    }

    public int get_tokenErrors() {
        return this._tokenErrors;
    }
}
