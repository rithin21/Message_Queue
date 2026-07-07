package consumer;

import broker.Broker;
import broker.Message;

public class Consumer implements Runnable {

    private final String name;
    private final Broker broker;
    private final String queueName;

    public Consumer(String name, Broker broker,String queueName) {
        this.name = name;
        this.broker = broker;
        this.queueName=queueName;
    }

    @Override
    public void run(){
        while(true){
            Message message=broker.consume(queueName);
            if(message!=null){
                System.out.println(name+"consumed :"+message);
            }
        }
    }

}