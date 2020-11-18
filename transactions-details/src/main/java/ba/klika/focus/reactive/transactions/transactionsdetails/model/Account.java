package ba.klika.focus.reactive.transactions.transactionsdetails.model;

import lombok.Data;

@Data
public class Account {
    private String id;
    private String iban;
    private String ownerFirstName;
    private String ownerLastName;
    private String ownerAddress;
}
