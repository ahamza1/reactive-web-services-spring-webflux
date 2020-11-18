package ba.klika.focus.reactive.accounts.accountdetails.accountdetails.service;

import ba.klika.focus.reactive.accounts.accountdetails.accountdetails.database.Account;
import ba.klika.focus.reactive.accounts.accountdetails.accountdetails.database.IAccountRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountsService {
    private final IAccountRepository accountRepository;


    public Flux<Account> getAccounts() {
        return accountRepository.findAll();
    }

    public Mono<Account> getAccountInfo(String iban) {
        return accountRepository.findByIban(iban);
    }

}
