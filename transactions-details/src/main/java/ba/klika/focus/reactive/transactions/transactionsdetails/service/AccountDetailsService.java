package ba.klika.focus.reactive.transactions.transactionsdetails.service;

import ba.klika.focus.reactive.transactions.transactionsdetails.connector.accountdetails.AccountDetailsConnector;
import ba.klika.focus.reactive.transactions.transactionsdetails.model.AccountTransaction;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountDetailsService {
    private final AccountDetailsConnector accountDetailsConnector;

    public Mono<AccountTransaction> getAccountsInTransaction(AccountTransaction accountTransaction) {
        return Mono.zip(
                accountDetailsConnector.getAccount(accountTransaction.getSourceIban()),
                accountDetailsConnector.getAccount(accountTransaction.getDestinationIban()),
                (source, destination) -> accountTransaction
                        .setSourceAccount(source).setDestinationAccount(destination)
        );
    }

}
