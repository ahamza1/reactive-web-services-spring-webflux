package ba.klika.focus.reactive.transactions.transactionsdetails.controller;

import ba.klika.focus.reactive.transactions.transactionsdetails.model.TransactionDetails;
import ba.klika.focus.reactive.transactions.transactionsdetails.service.ITransactionDetailsService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TransactionDetailsController {
    private final ITransactionDetailsService transactionDetailsService;

    @GetMapping(value = "/transactions", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<TransactionDetails> getAllTransactionsWithAccountDetails() {
        return transactionDetailsService.getAllTransactionsWithAccountDetails();
    }
}
