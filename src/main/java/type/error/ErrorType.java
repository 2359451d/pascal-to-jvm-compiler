package type.error;

import type.TypeDescriptor;

/**
 * Enumerated error type, representing a error when visiting the node
 */
public enum ErrorType implements TypeDescriptor {

    INVALID_TYPE(100, "invalid type"),
    INVALID_CONSTANT_TYPE(101, "invalid constant type"),
    INVALID_FUNCTION_TYPE(102, "invalid function type"),
    INVALID_PROCEDURE_TYPE(103, "invalid procedure type"),
    UNDEFINED_TYPE(200, "undefined type"),
    INTEGER_OVERFLOW(300, "integer overflow"),
    INTEGER_UNDERFLOW(301, "integer underflow"),
    INVALID_MONADIC_OPERATION(400, "invalid monadic operation"),
    INVALID_EXPRESSION(500, "invalid expression")
    ;

    private int errorCode; // error code
    private String message; // error message

    ErrorType(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    //public final static ErrorType INVALID_CONSTANT_TYPE = new ErrorType();


    public int getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equiv(TypeDescriptor type) {
        if (!(type instanceof ErrorType)) return false;
        ErrorType that = (ErrorType) type;
        return this.errorCode == that.getErrorCode() && this.message.equals(that.getMessage());
    }

}
