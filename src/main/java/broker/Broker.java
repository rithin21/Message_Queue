package broker;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class Broker {
    private final Map<String, MessageQueue> queues=new ConcurrentHashMap<>();

    public void createQueue(String queueName){
        queues.putIfAbsent(queueName,new MessageQueue());
        System.out.println("created queue:"+queueName);
    }

    public void publish(String queueName,Message message){
        MessageQueue queue=queues.get(queueName);
        if(queue==null){
            throw new RuntimeException(("queue not found"));
        }
        queue.publish(message);
        System.out.println(Thread.currentThread().getName()+"Published ->" +message); //the only change made bcs we are implementing threads
    }

    public Message consume(String queueName){
        MessageQueue queue= queues.get(queueName);
        if(queue==null)
        {
            throw new RuntimeException("queue not found");
        }
        return queue.consume();
    }

    public void printQueueState(String queueName){
        MessageQueue queue=queues.get(queueName);
        if(queue==null){
            throw new RuntimeException("Queue not found");
        }
        queue.printState();
    }

    public void ack(String queueName,String messageId){
        MessageQueue queue=queues.get(queueName);
        if(queue==null){
            throw new RuntimeException("queue not found");
        }
        queue.ack(messageId);
    }

    public void fail(String queueName,String messageId){
        MessageQueue queue=queues.get(queueName);
        if(queue==null){
            throw new RuntimeException("Queue not found");
        }
        queue.fail(messageId);
    }

}
