package ba.klika.focus.reactive.transactions.transactionsdetails.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
@Accessors(chain = true)
public class TransactionDetails {
    private UUID id;
    private BigDecimal amount;
    private Instant createdAt;

    private String sourceIban;
    private String destinationIban;
    private Account sourceAccount;
    private Account destinationAccount;
}
