package ba.klika.focus.reactive.transactions.transactionsdetails.deserializer;

import ba.klika.focus.reactive.transactions.transactionsdetails.model.Transaction;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;

public class TransactionDeserializer implements Deserializer<Transaction> {
    private final ObjectMapper objectMapper;

    public TransactionDeserializer() {
        this.objectMapper = new ObjectMapper()
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .registerModule(new JavaTimeModule());
    }

    @Override
    public Transaction deserialize(String topic, byte[] data) {
        if (data == null) {
            return null;
        }

        try {
            return objectMapper.readValue(data, Transaction.class);
        } catch (IOException e) {
            throw new SerializationException("Error when deserializing byte[] to transaction due to parsing problem");
        }
    }
}
