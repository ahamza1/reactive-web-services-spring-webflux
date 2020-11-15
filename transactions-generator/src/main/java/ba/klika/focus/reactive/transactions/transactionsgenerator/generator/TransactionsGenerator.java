package ba.klika.focus.reactive.transactions.transactionsgenerator.generator;

import ba.klika.focus.reactive.transactions.transactionsgenerator.model.Transaction;
import org.iban4j.Iban;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.Instant;
import java.util.Random;
import java.util.UUID;

public class TransactionsGenerator {
    private static final int FLUX_INTERVAL_MS = 1500;
    private static final long MIN_AMOUNT = 100;
    private static final long MAX_AMOUNT = 5000;

    private static final Random random = new Random();

    public static Flux<Transaction> getTransactions() {
        return Flux.interval(Duration.ofMillis(FLUX_INTERVAL_MS))
            .map(TransactionsGenerator::generateTransaction)
            .share();
    }

    private static Transaction generateTransaction(long i) {
        return new Transaction()
            .setId(UUID.randomUUID())
            .setSource(Iban.random().toString())
            .setDestination(Iban.random().toString())
            .setAmount(getRandomAmount())
            .setCreatedAt(Instant.now());
    }

    private static BigDecimal getRandomAmount() {
        return new BigDecimal(MIN_AMOUNT + random.nextDouble() * (MAX_AMOUNT - MIN_AMOUNT))
            .setScale(2, RoundingMode.HALF_UP);
    }
}
