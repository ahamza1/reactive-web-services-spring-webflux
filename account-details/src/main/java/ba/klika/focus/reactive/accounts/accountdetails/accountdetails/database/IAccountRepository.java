package ba.klika.focus.reactive.accounts.accountdetails.accountdetails.database;

import reactor.core.publisher.Mono;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface IAccountRepository extends ReactiveMongoRepository<Account, String> {

    Mono<Account> findByIban(String s);

}
