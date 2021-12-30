package type;

public enum ErrorType implements TypeDescriptor{

    INVALID_COSNTANT_TYPE(3, "invalid constant type");

    private int errorCode;
    private String message;// error message

    private ErrorType(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    //public final static ErrorType INVALID_CONSTANT_TYPE = new ErrorType();

    @Override
    public boolean equiv(TypeDescriptor type) {
        return false;
    }
}
