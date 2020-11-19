package ba.klika.focus.reactive.accounts.accountdetails.accountdetails.database;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountSeeder implements CommandLineRunner {
    private static final String CSV_DELIMITER = ",";
    private static final int BLOCKING_DURATION = 30;

    private final IAccountRepository accountRepository;

    @Value("${data.source.path}")
    private String dataSource;


    @Override
    public void run(String... args) throws Exception {
        Resource sourceCsv = new ClassPathResource(dataSource);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(sourceCsv.getInputStream()));

        List<Account> accounts = bufferedReader.lines()
                .skip(1)
                .map(row -> row.split(CSV_DELIMITER))
                .map(this::mapRowToAccount)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        accountRepository.insert(accounts)
                .blockLast(Duration.ofSeconds(BLOCKING_DURATION));
    }

    /**
     * CSV contains columns: first_name,last_name,iban,address.
     */
    private Account mapRowToAccount(String[] row) {
        try {
            String iban = row[2].replace(" ", "");
            return new Account(iban, row[0], row[1], row[3]);
        } catch (Exception e) {
            return null;
        }
    }

}
