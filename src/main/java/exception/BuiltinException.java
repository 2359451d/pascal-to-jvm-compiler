package exception;

/**
 * Existing built-in exception for reuse
 */
public enum BuiltinException {
    INVALID_COMMAND(new PascalCompilerException("Invalid command. Available Usage: parse, check")),
    INVALID_PATH(new PascalCompilerException("Invalid file path. Please specify valid path")),

    PARSE_NOT_START(new PascalCompilerException("Syntactic analysis not being executed yet...")),
    PARSE_FAILED(new PascalCompilerException("Syntactic analysis failed...")),
    CHECK_FAILED(new PascalCompilerException("Contextual analysis failed..."))

    ;


    private final PascalCompilerException exception;
    BuiltinException(PascalCompilerException exception) {
        this.exception = exception;
    }

    public PascalCompilerException getException() {
        return exception;
    }
}
