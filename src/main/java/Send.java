import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {
    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        try {
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
            for(int i=0; i<1000;i++){
            String message="ggg"+i;
            channel.queueDeclare("TEST2", false, false, false, null);
            channel.basicPublish("", "TEST2", null, (message).getBytes());
            System.out.println(" [x] Sent '" + message + "'");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}


