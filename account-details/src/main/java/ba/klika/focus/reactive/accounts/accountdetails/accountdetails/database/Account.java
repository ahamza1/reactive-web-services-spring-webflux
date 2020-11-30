package ba.klika.focus.reactive.accounts.accountdetails.accountdetails.database;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Account {

    @Id
    private String id;

    private String iban;
    private String ownerFirstName;
    private String ownerLastName;
    private String ownerAddress;

    public Account(String iban, String ownerFirstName, String ownerLastName, String ownerAddress) {
        this.iban = iban;
        this.ownerFirstName = ownerFirstName;
        this.ownerLastName = ownerLastName;
        this.ownerAddress = ownerAddress;
    }

}
