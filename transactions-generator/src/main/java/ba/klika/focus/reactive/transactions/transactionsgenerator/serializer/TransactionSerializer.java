package ba.klika.focus.reactive.transactions.transactionsgenerator.serializer;

import ba.klika.focus.reactive.transactions.transactionsgenerator.model.Transaction;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

public class TransactionSerializer implements Serializer<Transaction> {
    private final ObjectMapper objectMapper;

    public TransactionSerializer() {
        this.objectMapper = new ObjectMapper()
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .registerModule(new JavaTimeModule());
    }

    @Override
    public byte[] serialize(String topic, Transaction data) {
        if (data == null) {
            return null;
        }

        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (JsonProcessingException e) {
            throw new SerializationException(
                "Error occurred while serializing transaction to byte[] due to parsing problem"
            );
        }
    }
}
