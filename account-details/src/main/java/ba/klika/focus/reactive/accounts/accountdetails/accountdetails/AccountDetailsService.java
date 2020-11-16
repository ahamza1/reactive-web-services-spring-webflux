package ba.klika.focus.reactive.accounts.accountdetails.accountdetails;

import ba.klika.focus.reactive.accounts.accountdetails.accountdetails.database.Account;
import ba.klika.focus.reactive.accounts.accountdetails.accountdetails.service.AccountsService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountDetailsService implements IAccountDetailsService {
    private final AccountsService accountsService;


    @Override
    public Flux<Account> getAllAccount() {
        return accountsService.getAccounts();
    }

    @Override
    public Mono<Account> getAccountByIban(String iban) {
        return accountsService.getAccountInfo(iban);
    }

}
