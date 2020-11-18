package ba.klika.focus.reactive.transactions.transactionsdetails.connector.accountdetails.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    private String id;
    private String iban;
    private String ownerFirstName;
    private String ownerLastName;
    private String ownerAddress;

}
