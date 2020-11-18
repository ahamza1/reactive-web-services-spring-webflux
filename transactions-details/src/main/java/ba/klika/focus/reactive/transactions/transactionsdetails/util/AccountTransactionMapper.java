package ba.klika.focus.reactive.transactions.transactionsdetails.util;

import ba.klika.focus.reactive.transactions.transactionsdetails.model.AccountTransaction;
import ba.klika.focus.reactive.transactions.transactionsdetails.model.Transaction;
import org.springframework.stereotype.Component;

@Component
public class AccountTransactionMapper {

    public AccountTransaction fromTransaction(Transaction transaction) {
        return new AccountTransaction()
                .setDestinationIban(transaction.getDestination())
                .setSourceIban(transaction.getSource())
                .setCreatedAt(transaction.getCreatedAt())
                .setAmount(transaction.getAmount())
                .setId(transaction.getId());
    }

}
