package ba.klika.focus.reactive.transactions.transactionsdetails.resource;

import ba.klika.focus.reactive.transactions.transactionsdetails.model.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverRecord;

@RestController
@RequiredArgsConstructor
public class TransactionsResource {
    private final KafkaReceiver<String, Transaction> kafkaReceiver;

    @GetMapping("/transactions")
    public Flux<Transaction> getTransactions() {
        return kafkaReceiver.receive()
            .checkpoint("Message consuming started").log()
            .doOnNext(r -> r.receiverOffset().acknowledge())
            .map(ReceiverRecord::value)
            .checkpoint("Message consuming finished");
    }
}
