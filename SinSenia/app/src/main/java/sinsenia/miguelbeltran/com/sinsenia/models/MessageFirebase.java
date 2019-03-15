package sinsenia.miguelbeltran.com.sinsenia.models;

public class MessageFirebase {
    private String keyMessage;
    private MessageSend messageSend;

    public MessageFirebase(String keyMessage, MessageSend messageSend) {
        this.keyMessage = keyMessage;
        this.messageSend = messageSend;
    }

    public String getKeyMessage() {
        return keyMessage;
    }

    public void setKeyMessage(String keyMessage) {
        this.keyMessage = keyMessage;
    }

    public MessageSend getMessageSend() {
        return messageSend;
    }

    public void setMessageSend(MessageSend messageSend) {
        this.messageSend = messageSend;
    }
}
