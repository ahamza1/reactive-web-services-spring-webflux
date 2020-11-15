package ba.klika.focus.reactive.transactions.transactionsgenerator.kafkasender;

import ba.klika.focus.reactive.transactions.transactionsgenerator.generator.TransactionsGenerator;
import ba.klika.focus.reactive.transactions.transactionsgenerator.model.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

import java.time.LocalDateTime;

import static ba.klika.focus.reactive.transactions.transactionsgenerator.kafkasender.KafkaSenderConfig.TOPIC;

@Slf4j
public class TransactionsKafkaSender {
    private final KafkaSender<String, Transaction> kafkaSender;

    public TransactionsKafkaSender() {
        this.kafkaSender = KafkaSenderConfig.kafkaSender();
    }

    public void send() {
        kafkaSender.send(
            TransactionsGenerator.getTransactions()
                .map(transaction ->
                    SenderRecord.create(
                        new ProducerRecord<>(TOPIC, transaction.getId().toString(), transaction),
                        transaction.getId().toString()
                    )
                )
        )
            .doOnError(e -> log.error("Record sending failed with exception", e))
            .subscribe(r -> {
                RecordMetadata metadata = r.recordMetadata();
                log.info("Message {} sent successfully, topic-partition={}-{} offset={} timestamp={}",
                    r.correlationMetadata(),
                    metadata.topic(),
                    metadata.partition(),
                    metadata.offset(),
                    LocalDateTime.now()
                );
            });
    }

    public void close() {
        kafkaSender.close();
    }
}
