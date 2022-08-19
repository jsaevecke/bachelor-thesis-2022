import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

public class LearnerApplication{
    public static final String SUL_INPUT_ROUTING_KEY = "sul.input";
    public static final String SUL_OUTPUT_ROUTING_KEY = "sul.output";
    public static final String SUL_DIRECT_EXCHANGE = "sul_dx";
    public static final String SUL_INPUT_QUEUE = "sul_input_q";
    public static final String SUL_OUTPUT_QUEUE = "sul_output_q";

    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("guest");
        factory.setPassword("guest");
        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.exchangeDeclare(SUL_DIRECT_EXCHANGE, "direct");
            channel.queueDeclare(SUL_INPUT_QUEUE, true, false, false, null);
            channel.queueDeclare(SUL_OUTPUT_QUEUE, true, false, false, null);
            channel.queueBind(SUL_INPUT_QUEUE, SUL_DIRECT_EXCHANGE, SUL_INPUT_ROUTING_KEY);
            channel.queueBind(SUL_OUTPUT_QUEUE, SUL_DIRECT_EXCHANGE, SUL_OUTPUT_ROUTING_KEY);

            var mq = new MembershipQuery("uuid", "pod", 1, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
                ObjectOutputStream out = null;
                out = new ObjectOutputStream(bos);
                out.writeObject(mq);
                out.flush();
                byte[] yourBytes = bos.toByteArray();
                channel.basicPublish(SUL_DIRECT_EXCHANGE, SUL_INPUT_ROUTING_KEY, null, yourBytes);
            }
            // ignore close exception


        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }
}
