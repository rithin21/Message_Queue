package broker;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

//InFlighMessage is implementing Delayed because DelayQueue will call getDelay()fn later
public class InFlightMessage implements Delayed {

    private final Message message;
    private final long expireAt;

    public InFlightMessage(Message message,long timeoutMillis){
        this.message=message;
        this.expireAt=System.currentTimeMillis()+timeoutMillis;
    }

    public Message getMessage(){
        return message;
    }

    public long getExpireAt(){
        return expireAt;
    }

    @Override
    public long getDelay(TimeUnit unit){
        long remaining =expireAt-System.currentTimeMillis();

        return unit.convert(remaining,TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed other){
        InFlightMessage o=(InFlightMessage) other;

        return Long.compare(
                this.expireAt,o.expireAt
        );
    }

    @Override
    public String toString(){
        return message.toString();
    }

}
