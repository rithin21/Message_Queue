import broker.Broker;
import consumer.Consumer;
import producer.Producer;

public class Main {

    public static void main(String[] args) {

        Broker broker = new Broker();

        broker.createQueue("orders");

        Producer p1 = new Producer("Cashier-1", broker);
        Producer p2 = new Producer("Cashier-2", broker);
        Producer p3 = new Producer("Cashier-3", broker);

        Consumer c1 = new Consumer("Chef-1", broker);
        Consumer c2 = new Consumer("Chef-2", broker);
        Consumer c3 = new Consumer("Chef-3", broker);

        p1.publish("orders", "101", "Pepperoni Pizza");

        p2.publish("orders", "102", "Veg Pizza");

        p3.publish("orders", "103", "Cheese Burst");

        System.out.println();

        c1.consume("orders");

        c2.consume("orders");

        c3.consume("orders");
    }
}