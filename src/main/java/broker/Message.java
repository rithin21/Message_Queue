package broker;

public class Message {
    private final String id;
    private final String payload;
    private MessageStatus status;
    private int retryCount;

    public Message(String id,String payload){
        this.id=id;
        this.payload=payload;
        status=MessageStatus.READY;
        retryCount=0;
    }
    public String getId(){
        return id;
    }

    public String getPayload(){
        return payload;
    }

    public void setStatus(MessageStatus s){
        status=s;
    }

    public MessageStatus getStatus(){
        return status;
    }

    @Override
    public String toString(){
        return "[" +id + "]" + payload;
    }

    public void setRetryCount(int retryCount){
        this.retryCount=retryCount;
    }

    public int getRetryCount(){
        return retryCount;
    }
}
