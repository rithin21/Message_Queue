package broker;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class Broker {
    private final Map<String, BlockingQueue<Message>> queues=new ConcurrentHashMap<>();

    public void createQueue(String queueName){
        queues.putIfAbsent(queueName,new LinkedBlockingQueue<>());
        System.out.println("created queue:"+queueName);
    }

    public void publish(String queueName,Message message){
        BlockingQueue<Message>queue=queues.get(queueName);
        if(queue==null){
            throw new RuntimeException(("queue not found"));
        }
        queue.offer(message);
        System.out.println("Published -> "+message);
    }

    public Message consume(String queueName){
        BlockingQueue<Message>queue= queues.get(queueName);
        if(queue==null)
        {
            throw new RuntimeException("queue not found");
        }
        return queue.poll();
    }

}
