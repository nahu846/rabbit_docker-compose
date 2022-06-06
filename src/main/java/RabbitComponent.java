
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitComponent {

    private String host;
    private int port;
    private String user;
    private String password;
    private Connection connection;
    private Channel channel;

    public RabbitComponent(String host, int port, String user, String password) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        factory.setPort(port);
        factory.setUsername(user);
        factory.setPassword(password);
        try {
            connection = factory.newConnection();
            channel = connection.createChannel();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    public RabbitComponent(String host) {
        ConnectionFactory factory = new ConnectionFactory();
        this.host = host;
        try {
            connection = factory.newConnection();
            channel = connection.createChannel();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    public void produceOne(String queueName, String message) throws IOException, TimeoutException {

        channel.queueDeclare(queueName, false, false, false, null);

        channel.basicPublish("", queueName, null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");

        channel.close();
        connection.close();

    }

    public void produceMultiple(String queueName, String message, boolean finalMessage) throws IOException, TimeoutException {

        channel.queueDeclare(queueName, false, false, false, null);

        channel.basicPublish("", queueName, null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");

        if (finalMessage) {
            channel.close();
            connection.close();
        }
    }

    public void consume(String queueName) throws IOException {

        channel.queueDeclare(queueName, false, false, false, null);

        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag,
                                       Envelope envelope, AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
            }
        };
        channel.basicConsume(queueName, true, consumer);
    }
}
