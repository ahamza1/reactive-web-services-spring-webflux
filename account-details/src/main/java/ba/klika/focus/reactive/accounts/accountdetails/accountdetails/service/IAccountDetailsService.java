package ba.klika.focus.reactive.accounts.accountdetails.accountdetails.service;

import ba.klika.focus.reactive.accounts.accountdetails.accountdetails.database.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IAccountDetailsService {
    Flux<Account> getAllAccounts();

    Mono<Account> getAccountByIban(String iban);
}
