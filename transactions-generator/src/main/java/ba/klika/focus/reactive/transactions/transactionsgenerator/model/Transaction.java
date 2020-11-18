package ba.klika.focus.reactive.transactions.transactionsgenerator.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Transaction {
    private UUID id;
    private String source;
    private String destination;
    private BigDecimal amount;
    private Instant createdAt;
}
