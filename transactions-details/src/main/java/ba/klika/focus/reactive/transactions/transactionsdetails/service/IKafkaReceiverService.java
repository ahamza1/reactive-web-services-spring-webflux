package ba.klika.focus.reactive.transactions.transactionsdetails.service;

import ba.klika.focus.reactive.transactions.transactionsdetails.model.Transaction;
import reactor.core.publisher.Flux;

public interface IKafkaReceiverService {
    Flux<Transaction> getTransactions();
}
