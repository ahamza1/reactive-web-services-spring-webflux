package ba.klika.focus.reactive.transactions.transactionsdetails.config;

import ba.klika.focus.reactive.transactions.transactionsdetails.deserializer.TransactionDeserializer;
import ba.klika.focus.reactive.transactions.transactionsdetails.model.Transaction;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.receiver.internals.ConsumerFactory;
import reactor.kafka.receiver.internals.DefaultKafkaReceiver;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {
    private static final String TOPIC = "transactions-generator-demo";
    private static final String BOOTSTRAP_SERVERS = "localhost:9092";
    private static final String GROUP_ID_CONFIG = "transactions-details";
    private static final String CLIENT_ID_CONFIG = "transactions-details";
    private static final String AUTO_OFFSET_RESET_CONFIG = "earliest";
    private static final boolean ENABLE_AUTO_COMMIT_CONFIG = true;

    @Bean
    public KafkaReceiver<String, Transaction> kafkaReceiver() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, TransactionDeserializer.class.getName());
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID_CONFIG);
        properties.put(ConsumerConfig.CLIENT_ID_CONFIG, CLIENT_ID_CONFIG);
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, AUTO_OFFSET_RESET_CONFIG);
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, ENABLE_AUTO_COMMIT_CONFIG);

        ReceiverOptions<String, Transaction> receiverOptions = ReceiverOptions.create(properties);

        return new DefaultKafkaReceiver<>(
            ConsumerFactory.INSTANCE,
            receiverOptions.subscription(Collections.singleton(TOPIC))
        );
    }
}
