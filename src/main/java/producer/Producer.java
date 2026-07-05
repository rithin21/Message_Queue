package producer;

import broker.Broker;
import broker.Message;

public class Producer {
    private final String name;
    private final Broker broker;

    public Producer(String name, Broker broker){
        this.name=name;
        this.broker=broker;
    }

    public void publish(String queueName,String id,String payload){
        Message message=new Message(id,payload);
        System.out.println(name+"producing:"+message);
        broker.publish(queueName,message);
    }
}
