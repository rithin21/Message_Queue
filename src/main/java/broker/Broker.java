package broker;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Broker {

    private final Map<String, MessageQueue> queues =
            new ConcurrentHashMap<>();

    public void createQueue(String queueName) {

        queues.putIfAbsent(
                queueName,
                new MessageQueue()
        );

        System.out.println(
                "Created queue : " + queueName
        );
    }

    public void publish(
            String queueName,
            Message message
    ) {

        MessageQueue queue = queues.get(queueName);

        if (queue == null) {
            throw new RuntimeException(
                    "Queue not found"
            );
        }

        queue.publish(message);

        System.out.println(
                Thread.currentThread().getName()
                        + " published -> "
                        + message
        );
    }

    public Message consume(
            String queueName
    ) throws InterruptedException {

        MessageQueue queue = queues.get(queueName);

        if (queue == null) {
            throw new RuntimeException(
                    "Queue not found"
            );
        }

        return queue.consume();
    }
}
