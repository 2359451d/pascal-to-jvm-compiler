package utils;

import java.util.List;

public class BooleanMessage {
    private boolean flag;
    private List<String> messageSequence;

    public BooleanMessage() { }

    /**
     * No message to report
     * @param flag
     */
    public BooleanMessage(boolean flag) {
        this.flag = flag;
        this.messageSequence = null;
    }

    /**
     * There exists one/several message(s) to report, which are encapsulated inside the List
     * @param flag
     * @param messageSequence
     */
    public BooleanMessage(boolean flag, List<String> messageSequence) {
        this.flag = flag;
        this.messageSequence = messageSequence;
    }

    public boolean getFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public List<String> getMessageSequence() {
        return messageSequence;
    }

    public void setMessageSequence(List<String> messageSequence) {
        this.messageSequence = messageSequence;
    }
}
