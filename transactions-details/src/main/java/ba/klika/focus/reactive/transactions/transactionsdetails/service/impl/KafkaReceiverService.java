package ba.klika.focus.reactive.transactions.transactionsdetails.service.impl;

import ba.klika.focus.reactive.transactions.transactionsdetails.model.Transaction;
import ba.klika.focus.reactive.transactions.transactionsdetails.service.IKafkaReceiverService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverRecord;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaReceiverService implements IKafkaReceiverService {
    private final KafkaReceiver<String, Transaction> kafkaReceiver;

    @Override
    public Flux<Transaction> getTransactions() {
        return kafkaReceiver.receive()
                .checkpoint("Message consuming started")
                .doOnNext(r -> r.receiverOffset().acknowledge())
                .map(ReceiverRecord::value)
                .checkpoint("Message consuming finished");
    }
}
