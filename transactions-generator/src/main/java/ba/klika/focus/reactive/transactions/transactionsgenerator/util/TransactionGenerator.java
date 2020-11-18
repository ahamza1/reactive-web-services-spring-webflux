package ba.klika.focus.reactive.transactions.transactionsgenerator.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

import ba.klika.focus.reactive.transactions.transactionsgenerator.model.Transaction;
import reactor.core.publisher.Flux;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class TransactionGenerator {
    private static final String DATA_SOURCE = "data/MOCK_DATA.csv";
    private static final Random random = new Random();
    private static final int FLUX_INTERVAL_MS = 1500;
    private static final long MIN_AMOUNT = 100;
    private static final long MAX_AMOUNT = 5000;

    private final List<String> ibanList;

    public TransactionGenerator() throws IOException {
        this.ibanList = getIbanListFromCsv();
    }


    public Flux<Transaction> getTransactions() {
        return Flux.interval(Duration.ofMillis(FLUX_INTERVAL_MS))
                .map(this::generateTransaction)
                .share();
    }

    private Transaction generateTransaction(long i) {
        return new Transaction()
                .setId(UUID.randomUUID())
                .setSource(getIbanFromList())
                .setDestination(getIbanFromList())
                .setAmount(getRandomAmount())
                .setCreatedAt(Instant.now());
    }

    private String getIbanFromList() {
        Collections.shuffle(ibanList);
        return ibanList.get(random.nextInt(ibanList.size()));
    }

    private BigDecimal getRandomAmount() {
        return BigDecimal.valueOf(MIN_AMOUNT + random.nextDouble() * (MAX_AMOUNT - MIN_AMOUNT))
                .setScale(2, RoundingMode.HALF_UP);
    }

    private List<String> getIbanListFromCsv() throws IOException {
        Resource sourceCsv = new ClassPathResource(DATA_SOURCE);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(sourceCsv.getInputStream()));

        return bufferedReader.lines().skip(1)
                .map(iban -> iban.replace(" ", ""))
                .collect(Collectors.toList());
    }
}
