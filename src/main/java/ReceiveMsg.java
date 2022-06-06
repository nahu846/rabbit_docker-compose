import java.io.IOException;

public class ReceiveMsg {

    public static void main(String[] args) throws IOException {
        RabbitComponent rabbitComponent = new RabbitComponent("localhost");

        rabbitComponent.consume("myQueue");
    }

}
