package broker;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class MessageQueue {
    private final BlockingQueue<Message>waitingQueue=new LinkedBlockingQueue<>();
    private final Map<String,Message> inFlight=new ConcurrentHashMap<>();

    public void publish(Message message){
        waitingQueue.offer(message);
    }

    public Message consume(){
        Message message=waitingQueue.poll();
        if(message!=null){
            message.setStatus(MessageStatus.IN_FLIGHT);
            inFlight.put(message.getId(), message);


        }
        return message;
    }

    public void printState(){
        System.out.println("Waiting Queue");
        System.out.println(waitingQueue);
        System.out.println();
        System.out.println("In Flight");
        System.out.println(inFlight);
    }
}
