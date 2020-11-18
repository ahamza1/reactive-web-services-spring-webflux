package ba.klika.focus.reactive.accounts.accountdetails.accountdetails.controller;

import ba.klika.focus.reactive.accounts.accountdetails.accountdetails.IAccountDetailsService;
import ba.klika.focus.reactive.accounts.accountdetails.accountdetails.database.Account;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountDetailsController {
    private final IAccountDetailsService accountDetailsService;


    @GetMapping(path = "/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Account> getAllAccount() {
        return accountDetailsService.getAllAccount();
    }

    @GetMapping(path = "/accounts/{iban}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Account> getAccountByIban(@PathVariable String iban) {
        return accountDetailsService.getAccountByIban(iban);
    }

}
