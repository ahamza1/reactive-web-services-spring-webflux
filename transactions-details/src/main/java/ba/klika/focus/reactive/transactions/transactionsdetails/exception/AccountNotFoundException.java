package ba.klika.focus.reactive.transactions.transactionsdetails.exception;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(String iban) {
        super("Account with IBAN [" + iban + "] not found");
    }
}
