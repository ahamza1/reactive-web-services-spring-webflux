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

import ba.klika.focus.reactive.transactions.transactionsgenerator.config.GeneratorConfig;
import ba.klika.focus.reactive.transactions.transactionsgenerator.exception.ServiceException;
import ba.klika.focus.reactive.transactions.transactionsgenerator.model.Transaction;
import reactor.core.publisher.Flux;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class TransactionGenerator {
    private static final Random random = new Random();

    private final GeneratorConfig generatorConfig;
    private final List<String> ibanList;

    public TransactionGenerator(GeneratorConfig generatorConfig) {
        this.generatorConfig = generatorConfig;
        this.ibanList = getIbanListFromCsv();
    }

    public Flux<Transaction> getTransactions() {
        return Flux.interval(Duration.ofMillis(generatorConfig.getIntervalMs()))
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
        long minAmount = generatorConfig.getTransactionMinAmount();
        long maxAmount = generatorConfig.getTransactionMaxAmount();

        return BigDecimal.valueOf(minAmount + random.nextDouble() * (maxAmount - minAmount))
                .setScale(2, RoundingMode.HALF_UP);
    }

    private List<String> getIbanListFromCsv() {
        try {
            Resource sourceCsv = new ClassPathResource(generatorConfig.getDataSourcePath());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(sourceCsv.getInputStream()));

            return bufferedReader.lines().skip(1)
                .map(iban -> iban.replace(" ", ""))
                .collect(Collectors.toList());
        } catch (IOException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
