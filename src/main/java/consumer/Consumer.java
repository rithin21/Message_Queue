package consumer;

import broker.Broker;
import broker.Message;

public class Consumer {

    private final String name;
    private final Broker broker;

    public Consumer(String name, Broker broker) {
        this.name = name;
        this.broker = broker;
    }

    public void consume(String queueName) {

        Message message = broker.consume(queueName);

        if (message == null) {

            System.out.println(name + " : Queue Empty");
            return;
        }

        System.out.println(name + " consumed : " + message);
    }
}