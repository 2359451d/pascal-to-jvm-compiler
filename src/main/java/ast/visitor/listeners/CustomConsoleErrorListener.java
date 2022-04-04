package ast.visitor.listeners;

import org.antlr.v4.runtime.ConsoleErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import utils.log.ErrorReporter;
import utils.log.RegressionTestErrorReporter;

/**
 * Redirect the console output using Logback rather than standard one
 */
public class CustomConsoleErrorListener extends ConsoleErrorListener {
    protected int _tokenErrors;
    public static CustomConsoleErrorListener INSTANCE = new CustomConsoleErrorListener();
    protected boolean redirectToRegressionLog;

    //private static final Logger logger
    //        = LoggerFactory.getLogger(PascalCompilerDriverBuilder.class);

    private CustomConsoleErrorListener() {
        this._tokenErrors = 0;
        this.redirectToRegressionLog = false;
    }

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
        //super.syntaxError(recognizer, offendingSymbol, line, charPositionInLine, msg, e);
        //if (logger.isErrorEnabled()) logger.error("line " + line + ":" + charPositionInLine + " " + msg);

        ErrorReporter.error("line {} : {} {}",
                ()->line,
                ()->charPositionInLine,
                ()->msg);
        if (redirectToRegressionLog) {
            RegressionTestErrorReporter.error(
                    "line {} : {} {}",
                    () -> line,
                    () -> charPositionInLine,
                    () -> msg
            );
        }
        this._tokenErrors++;
        //System.out.println("_tokenErrors = " + _tokenErrors);
    }

    public int get_tokenErrors() {
        return this._tokenErrors;
    }

    public void setRedirectToRegressionLog(boolean redirectToRegressionLog) {
        this.redirectToRegressionLog = redirectToRegressionLog;
    }
}
