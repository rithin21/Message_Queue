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
                System.out.println("\nBefore ACK");
                broker.printQueueState(queueName);

                System.out.println(name+"consumed :"+message);
                //broker.ack(queueName,message.getId());
                broker.fail(queueName,message.getId());

                System.out.println("\nAFter ACK");
                broker.printQueueState(queueName);
            }
        }
    }

}