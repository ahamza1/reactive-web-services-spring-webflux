package ba.klika.focus.reactive.transactions.transactionsdetails.resource;

import ba.klika.focus.reactive.transactions.transactionsdetails.model.AccountTransaction;
import ba.klika.focus.reactive.transactions.transactionsdetails.model.Transaction;
import ba.klika.focus.reactive.transactions.transactionsdetails.service.AccountDetailsService;
import ba.klika.focus.reactive.transactions.transactionsdetails.util.AccountTransactionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverRecord;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AccountTransactionsResource {
    private final KafkaReceiver<String, Transaction> kafkaReceiver;
    private final AccountTransactionMapper accountTransactionMapper;
    private final AccountDetailsService accountDetailsService;

    @GetMapping(value = "/accounts/transactions", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<AccountTransaction> getTransactions() {
        return kafkaReceiver.receive()
                .checkpoint("Message consuming started")
                .doOnNext(r -> r.receiverOffset().acknowledge())
                .map(ReceiverRecord::value)
                .map(accountTransactionMapper::fromTransaction)
                .flatMap(accountDetailsService::getAccountsInTransaction)
                .doOnNext(accountTransaction -> log.info("Received record: {}", accountTransaction))
                .checkpoint("Message consuming finished");
    }
}
