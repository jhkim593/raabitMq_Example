import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Recv {
    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try{
        factory.setPort(5672);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();


            channel.queueDeclare("TEST2",false,false,false,null );
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                try {
                    doWork(message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println(" [x] Done");
                }
                System.out.println(" [x] Received '" + message + "'");

            };
            boolean autoAck = true;
            channel.basicConsume("TEST2", autoAck, deliverCallback, consumerTag -> { });

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    private static void doWork(String task) throws InterruptedException {
        for (char ch: task.toCharArray()) {
            if (ch == 'g') Thread.sleep(1000);
        }
    }
}
