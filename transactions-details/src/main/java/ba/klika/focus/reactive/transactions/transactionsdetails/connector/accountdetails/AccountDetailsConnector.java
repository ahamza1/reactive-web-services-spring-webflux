package ba.klika.focus.reactive.transactions.transactionsdetails.connector.accountdetails;

import ba.klika.focus.reactive.transactions.transactionsdetails.connector.accountdetails.model.Account;
import ba.klika.focus.reactive.transactions.transactionsdetails.exception.AccountNotFoundException;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class AccountDetailsConnector {
    private final WebClient webClient;

    @Value("${connector.account-details.url}")
    private String accountDetailsUrl;


    public Flux<Account> getAllAccounts() {
        return webClient.get().uri(accountDetailsUrl + "/accounts")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve().bodyToFlux(Account.class);
    }

    public Mono<Account> getAccount(String iban) {
        return webClient.get().uri(accountDetailsUrl + "/accounts/{iban}", iban)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve().bodyToMono(Account.class)
                .switchIfEmpty(Mono.error(new AccountNotFoundException(iban)));
    }

}
