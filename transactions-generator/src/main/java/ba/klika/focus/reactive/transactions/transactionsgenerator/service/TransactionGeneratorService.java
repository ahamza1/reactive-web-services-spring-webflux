package ba.klika.focus.reactive.transactions.transactionsgenerator.service;

import java.time.LocalDateTime;

import ba.klika.focus.reactive.transactions.transactionsgenerator.model.Transaction;
import ba.klika.focus.reactive.transactions.transactionsgenerator.util.TransactionGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;
import reactor.kafka.sender.SenderResult;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionGeneratorService implements CommandLineRunner {
    private final TransactionGenerator transactionGenerator;
    private final KafkaSender<String, Transaction> kafkaSender;

    @Value("${kafka.topic}")
    private String topic;


    @Override
    public void run(String... args) {
        kafkaSender.send(transactionGenerator.getTransactions().map(this::createSenderRecord))
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
