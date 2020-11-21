package ba.klika.focus.reactive.transactions.transactionsgenerator;

import ba.klika.focus.reactive.transactions.transactionsgenerator.service.ITransactionsGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionsGeneratorRunner implements CommandLineRunner {
    private final ITransactionsGeneratorService transactionsGeneratorService;

    @Override
    public void run(String... args) {
        transactionsGeneratorService.initialize();
    }
}
