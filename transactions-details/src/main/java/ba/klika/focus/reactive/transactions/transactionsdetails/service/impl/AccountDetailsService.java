package ba.klika.focus.reactive.transactions.transactionsdetails.service.impl;

import ba.klika.focus.reactive.transactions.transactionsdetails.exception.AccountNotFoundException;
import ba.klika.focus.reactive.transactions.transactionsdetails.model.Account;
import ba.klika.focus.reactive.transactions.transactionsdetails.service.IAccountDetailsService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class AccountDetailsService implements IAccountDetailsService {
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
