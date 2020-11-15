package ba.klika.focus.reactive.accounts.accountdetails.accountdetails;

import ba.klika.focus.reactive.accounts.accountdetails.accountdetails.database.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IAccountDetailsService {

    Flux<Account> getAllAccount();

    Mono<Account> getAccountByIban(String iban);

}
