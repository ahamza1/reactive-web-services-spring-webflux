package ba.klika.focus.reactive.transactions.transactionsgenerator.config;

import ba.klika.focus.reactive.transactions.transactionsgenerator.model.Transaction;
import ba.klika.focus.reactive.transactions.transactionsgenerator.util.TransactionSerializer;
import lombok.Getter;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;
import reactor.kafka.sender.internals.DefaultKafkaSender;
import reactor.kafka.sender.internals.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaSenderConfig {
    @Value("${kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${kafka.client-id}")
    private String clientId;

    @Bean
    public KafkaSender<String, Transaction> kafkaSender() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, TransactionSerializer.class.getName());
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, clientId);

        SenderOptions<String, Transaction> senderOptions = SenderOptions.create(properties);
        return new DefaultKafkaSender<>(ProducerFactory.INSTANCE, senderOptions);
    }
}
