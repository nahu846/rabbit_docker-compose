import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class SendMsg {
    public static void main(String[] args) throws IOException, TimeoutException {
        RabbitComponent rabbitComponent = new RabbitComponent("localhost");

        sendOneMessage(rabbitComponent);
//        sendLotOfMessages(rabbitComponent, 50);
    }

    public static void sendOneMessage(RabbitComponent rabbitComponent) throws IOException, TimeoutException {
        rabbitComponent.produceOne("myQueue", "hello world");
    }

    public static void sendLotOfMessages(RabbitComponent rabbitComponent, int cant) throws IOException, TimeoutException {
        for (int i = 1; i <= cant; i++) {
            rabbitComponent.produceMultiple("myQueue", "hola mundo " + i, i == cant);
        }
    }
}
