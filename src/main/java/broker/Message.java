package broker;

public class Message {
    private final String id;
    private final String payload;

    public Message(String id,String payload){
        this.id=id;
        this.payload=payload;
    }
    public String getId(){
        return id;
    }

    public String getPayload(){
        return payload;
    }

    @Override
    public String toString(){
        return "[" +id + "]" + payload;
    }
}
