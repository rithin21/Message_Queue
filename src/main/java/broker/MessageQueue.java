package broker;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MessageQueue {
    public static final int MAX_RETRY=3;
    private static final long VISIBILITY_TIMEOUT=10000;
    private final BlockingQueue<Message>waitingQueue=new LinkedBlockingQueue<>();
    private final Map<String,InFlightMessage> inFlight=new ConcurrentHashMap<>();
    private final BlockingQueue<Message>deadLetterQueue=new LinkedBlockingQueue<>();
    private final DelayQueue<InFlightMessage>timeoutQueue=new DelayQueue<>();

    public void publish(Message message){
        waitingQueue.offer(message);
    }

    public Message consume(){
        Message message=waitingQueue.poll();
        if(message!=null){
            message.setStatus(MessageStatus.IN_FLIGHT);
            InFlightMessage wrapper =new InFlightMessage(message,10000);
            inFlight.put(message.getId(),wrapper);
            timeoutQueue.offer(wrapper);


        }
        return message;
    }

    public void ack(String messageId){
        InFlightMessage wrapper=inFlight.remove(messageId);
        if(wrapper!=null){
            timeoutQueue.remove(wrapper);
            wrapper.getMessage().setStatus(MessageStatus.ACKNOWLEDGED);
            System.out.println("ACK received for"+wrapper.getMessage().getId());
        }
    }

    public void fail(String messageId){
        InFlightMessage wrapper =inFlight.remove(messageId);
        if(wrapper==null){
            return;
            }
        timeoutQueue.remove(wrapper);
        wrapper.getMessage().setRetryCount(wrapper.getMessage().getRetryCount()+1);
        System.out.println("Retry count:"+wrapper.getMessage().getRetryCount());

        if(wrapper.getMessage().getRetryCount()<MAX_RETRY) {
            wrapper.getMessage().setStatus(MessageStatus.READY);
            waitingQueue.offer(wrapper.getMessage());
            System.out.println(wrapper.getMessage().getId() + "returned to waiting queue");
        }
        else
        {

            wrapper.getMessage().setStatus(MessageStatus.FAILED);
            deadLetterQueue.offer(wrapper.getMessage());
            System.out.println(wrapper.getMessage().getId() + " moved to Dead Letter Queue");
        }
    }

    public void printState(){
        System.out.println("Waiting Queue");
        System.out.println(waitingQueue);
        System.out.println();
        System.out.println("In Flight");
        System.out.println(inFlight);
        System.out.println();
        System.out.println("Dead Letter Queue");
        System.out.println(deadLetterQueue);
        System.out.println();
        System.out.println("Timeout Queue");
        System.out.println(timeoutQueue);
    }

    public void printTimeoutQueue(){

        System.out.println("Timeout Queue");

        System.out.println(timeoutQueue);

        System.out.println();

    }
}
