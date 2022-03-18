package ast.visitor;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.LexerNoViableAltException;

public class PascalCustomLexer extends PascalLexer {

    private int _tokenErrors;

    public PascalCustomLexer(CharStream input) {
        super(input);
        this._tokenErrors = 0;
    }

    @Override
    public void notifyListeners(LexerNoViableAltException e) {
        super.notifyListeners(e);
        this._tokenErrors++;
    }

    public int getTokenErrors() {
        return _tokenErrors;
    }



}
