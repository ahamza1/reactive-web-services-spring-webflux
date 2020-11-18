package ba.klika.focus.reactive.transactions.transactionsgenerator;

import ba.klika.focus.reactive.transactions.transactionsgenerator.model.Transaction;
import ba.klika.focus.reactive.transactions.transactionsgenerator.util.TransactionsGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;
import reactor.kafka.sender.SenderResult;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionsGeneratorService implements CommandLineRunner {
    @Value("${kafka.topic}")
    private String topic;

    private final TransactionsGenerator transactionsGenerator;
    private final KafkaSender<String, Transaction> kafkaSender;

    @Override
    public void run(String... args) {
        kafkaSender.send(transactionsGenerator.getTransactions().map(this::createSenderRecord))
            .doOnNext(this::processResponse)
            .doOnError(this::processError)
            .subscribe();
    }

    private SenderRecord<String, Transaction, String> createSenderRecord(Transaction transaction) {
        return SenderRecord.create(
            new ProducerRecord<>(topic, transaction.getId().toString(), transaction),
            transaction.getId().toString()
        );
    }

    private void processResponse(SenderResult<String> senderResult) {
        RecordMetadata metadata = senderResult.recordMetadata();

        log.info("Record {} sent successfully, topic-partition@offset = {}, timestamp = {}",
            senderResult.correlationMetadata(),
            metadata.toString(),
            LocalDateTime.now()
        );
    }

    private void processError(Throwable throwable) {
        log.error("Record sending failed with exception", throwable);
    }
}
