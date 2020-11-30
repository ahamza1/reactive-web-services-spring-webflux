package ba.klika.focus.reactive.transactions.transactionsdetails.util;

import ba.klika.focus.reactive.transactions.transactionsdetails.model.Account;
import ba.klika.focus.reactive.transactions.transactionsdetails.model.Transaction;
import ba.klika.focus.reactive.transactions.transactionsdetails.model.TransactionDetails;

public class TransactionDetailsMapper {

    public static TransactionDetails map(Transaction transaction, Account source, Account destination) {
        return new TransactionDetails()
                .setId(transaction.getId())
                .setAmount(transaction.getAmount())
                .setCreatedAt(transaction.getCreatedAt())
                .setSourceIban(transaction.getSource())
                .setDestinationIban(transaction.getDestination())
                .setSourceAccount(source)
                .setDestinationAccount(destination);
    }
}
