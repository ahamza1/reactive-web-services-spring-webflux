package ba.klika.focus.reactive.transactions.transactionsdetails.service;

import ba.klika.focus.reactive.transactions.transactionsdetails.model.TransactionDetails;
import reactor.core.publisher.Flux;

public interface ITransactionDetailsService {
    Flux<TransactionDetails> getAllTransactionsWithAccountDetails();
}
