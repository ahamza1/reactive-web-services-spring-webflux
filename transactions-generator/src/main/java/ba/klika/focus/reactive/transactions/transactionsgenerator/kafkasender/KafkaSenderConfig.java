package ba.klika.focus.reactive.transactions.transactionsgenerator.kafkasender;

import ba.klika.focus.reactive.transactions.transactionsgenerator.model.Transaction;
import ba.klika.focus.reactive.transactions.transactionsgenerator.serializer.TransactionSerializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;
import reactor.kafka.sender.internals.DefaultKafkaSender;
import reactor.kafka.sender.internals.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

public class KafkaSenderConfig {
    private static final String BOOTSTRAP_SERVERS = "localhost:9092";
    private static final String CLIENT_ID_CONFIG = "transactions-generator";

    public static KafkaSender<String, Transaction> kafkaSender() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, CLIENT_ID_CONFIG);
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, TransactionSerializer.class.getName());

        SenderOptions<String, Transaction> senderOptions = SenderOptions.create(properties);
        return new DefaultKafkaSender<>(ProducerFactory.INSTANCE, senderOptions);
    }
}
