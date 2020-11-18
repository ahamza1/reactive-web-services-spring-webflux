package ba.klika.focus.reactive.transactions.transactionsdetails.service;

import ba.klika.focus.reactive.transactions.transactionsdetails.model.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IAccountDetailsService {
    Flux<Account> getAllAccounts();

    Mono<Account> getAccount(String iban);
}
