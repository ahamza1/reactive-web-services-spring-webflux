package ba.klika.focus.reactive.transactions.transactionsgenerator;

import ba.klika.focus.reactive.transactions.transactionsgenerator.kafkasender.TransactionsKafkaSender;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TransactionsGeneratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransactionsGeneratorApplication.class, args);

        TransactionsKafkaSender transactionsKafkaSender = new TransactionsKafkaSender();
        transactionsKafkaSender.send();
    }
}
