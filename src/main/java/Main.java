import broker.Broker;
import consumer.Consumer;
import producer.Producer;

public class Main {

    public static void main(String[] args) {

        Broker broker = new Broker();

        broker.createQueue("orders");

        Producer p1 = new Producer("Cashier-1", broker, "orders");
        Producer p2 = new Producer("Cashier-2", broker, "orders");
        Producer p3 = new Producer("Cashier-3", broker, "orders");

        Consumer c1 = new Consumer("Chef-1", broker, "orders");
        Consumer c2 = new Consumer("Chef-2", broker, "orders");
        Consumer c3 = new Consumer("Chef-3", broker, "orders");

        new Thread(p1, "Cashier-1").start();
        new Thread(p2, "Cashier-2").start();
        new Thread(p3, "Cashier-3").start();

        new Thread(c1, "Chef-1").start();
        new Thread(c2, "Chef-2").start();
        new Thread(c3, "Chef-3").start();
    }
}