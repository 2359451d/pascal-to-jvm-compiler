package utils;

public class ErrorMessage {
    /**
     * if set to false, possibly contains error to report
     * otherwise set to true
     */
    //private boolean flag;
    //private String message;
    //private List<String> messageSequence;
    private StringBuilder messageSequence;

    public ErrorMessage() {
    }

    /**
     * No message to report
     *
     * @param flag
     */
    //public ErrorMessage(boolean flag) {
    //    this.flag = flag;
    //    this.messageSequence = null;
    //this.message = null;
    //}

    /**
     * There exist several messages to report, which are encapsulated inside the List
     *
     * @param flag
     * @param messageSequence
     */
    public ErrorMessage(StringBuilder stringBuilder) {
        this.messageSequence = stringBuilder;
    }
    //public ErrorMessage(boolean flag, StringBuilder messageSequence) {
    //    this.flag = flag;
    //    this.messageSequence = messageSequence;
    //}
    //public BooleanMessage(boolean flag, List<String> messageSequence) {
    //    this.flag = flag;
    //    this.messageSequence = messageSequence;
    //}

    /**
     * There exists one message to report, which are encapsulated inside the List
     *
     * @param flag
     * @param message
     */
    //public BooleanMessage(boolean flag, String message) {
    //    this.flag = flag;
    //    this.message = message;
    //}
    //public boolean getFlag() {
    //    return flag;
    //}
    //
    //public void setFlag(boolean flag) {
    //    this.flag = flag;
    //}

    //public List<String> getMessageSequence() {
    //    return messageSequence;
    //}

    //public void setMessageSequence(List<String> messageSequence) {
    //    this.messageSequence = messageSequence;
    //}

    //public String getMessage() { return message; }

    //public void setMessage(String message) {
    //    this.message = message;
    //}

    //@Override
    //public String toString() {
    //    return "BooleanMessage{" +
    //            "flag=" + flag +
    //            ", message='" + message + '\'' +
    //            ", messageSequence=" + messageSequence +
    //            '}';
    //}
    public boolean hasErrors() {
        return messageSequence!=null && messageSequence.length() > 0;
    }

    public String getMessageSequence() {
        return messageSequence.toString();
    }

    public void setMessageSequence(StringBuilder messageSequence) {
        this.messageSequence = messageSequence;
    }

    @Override
    public String toString() {
        return "ErrorMessage{" +
                "messageSequence=" + messageSequence +
                '}';
    }
}
