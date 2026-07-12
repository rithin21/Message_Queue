package broker;

public class InFlightMessage {

    private final Message message;
    private final long recievedTime;

    public InFlightMessage(Message message){
        this.message=message;
        this.recievedTime=System.currentTimeMillis();
    }

    public Message getMessage(){
        return message;
    }

    public long getRecievedTime(){
        return recievedTime;
    }

}
