package producer;

import broker.Broker;
import broker.Message;

import java.util.Random;

public class Producer implements Runnable {

    private final String name;
    private final Broker broker;
    private final String queueName;

    private final Random random = new Random();

    public Producer(String name, Broker broker, String queueName) {
        this.name = name;
        this.broker = broker;
        this.queueName = queueName;
    }

    @Override
    public void run() {
        int count = 1;

        while (!Thread.currentThread().isInterrupted()) {

            Message message = new Message(
                    name + "-" + count,
                    "Pizza " + count
            );

            broker.publish(queueName, message);

            count++;

            try {
                Thread.sleep(random.nextInt(1000, 3001));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
