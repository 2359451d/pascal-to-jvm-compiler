package ast.visitor.listeners;

import org.antlr.v4.runtime.ConsoleErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

@Deprecated
public class CustomConsoleErrorListener extends ConsoleErrorListener {
    protected int _tokenErrors=0;

    public CustomConsoleErrorListener() {
    }

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
        super.syntaxError(recognizer, offendingSymbol, line, charPositionInLine, msg, e);
        this._tokenErrors++;
        //System.out.println("_tokenErrors = " + _tokenErrors);
    }

    public int get_tokenErrors() {
        return this._tokenErrors;
    }
}
