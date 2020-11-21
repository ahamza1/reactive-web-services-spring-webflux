package ba.klika.focus.reactive.transactions.transactionsgenerator.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "generator")
public class GeneratorConfig {
    private int intervalMs = 500;
    private long transactionMinAmount = 1;
    private long transactionMaxAmount = 10000;
    private String dataSourcePath = "data/MOCK_DATA.csv";
}
