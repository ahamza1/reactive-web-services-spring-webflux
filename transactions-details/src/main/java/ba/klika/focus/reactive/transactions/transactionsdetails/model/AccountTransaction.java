package ba.klika.focus.reactive.transactions.transactionsdetails.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AccountTransaction {
    private UUID id;
    private BigDecimal amount;
    private Instant createdAt;

    private String sourceIban;
    private String destinationIban;
    private Account sourceAccount;
    private Account destinationAccount;
}
