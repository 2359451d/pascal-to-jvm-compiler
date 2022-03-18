package utils.test;

import ast.visitor.PascalBaseVisitor;
import ast.visitor.PascalParser;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.TokenStreamRewriter;
import type.TypeDescriptor;

public class RenameVisitor extends PascalBaseVisitor<TypeDescriptor> {

    TokenStreamRewriter rewriter;
    private String sourceName;

    public RenameVisitor(TokenStream tokens, String sourceName) {
        rewriter = new TokenStreamRewriter(tokens);
        this.sourceName = sourceName;
    }

    @Override
    public TypeDescriptor visitProgramHeading(PascalParser.ProgramHeadingContext ctx) {
        Token start = ctx.identifier().start;
        Token stop = ctx.SEMI().getSymbol();
        rewriter.replace(start,stop,this.sourceName);
        return null;
    }

    public String getReplacedCode() {
        return rewriter.getText();
    }
}
