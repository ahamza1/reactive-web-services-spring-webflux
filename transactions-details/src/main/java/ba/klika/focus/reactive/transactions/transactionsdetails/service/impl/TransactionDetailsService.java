package ba.klika.focus.reactive.transactions.transactionsdetails.service.impl;

import ba.klika.focus.reactive.transactions.transactionsdetails.model.Transaction;
import ba.klika.focus.reactive.transactions.transactionsdetails.model.TransactionDetails;
import ba.klika.focus.reactive.transactions.transactionsdetails.service.IAccountDetailsService;
import ba.klika.focus.reactive.transactions.transactionsdetails.service.IKafkaReceiverService;
import ba.klika.focus.reactive.transactions.transactionsdetails.service.ITransactionDetailsService;
import ba.klika.focus.reactive.transactions.transactionsdetails.util.TransactionDetailsMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionDetailsService implements ITransactionDetailsService {
    private final IKafkaReceiverService kafkaReceiverService;
    private final IAccountDetailsService accountDetailsService;

    @Override
    public Flux<TransactionDetails> getAllTransactionsWithAccountDetails() {
        return kafkaReceiverService.getTransactions()
                .flatMap(this::getTransactionDetails)
                .doOnNext(this::logTransactionDetails);
    }

    private Mono<TransactionDetails> getTransactionDetails(Transaction transaction) {
        return Mono.zip(
                accountDetailsService.getAccount(transaction.getSource()),
                accountDetailsService.getAccount(transaction.getDestination()),
                (source, destination) -> TransactionDetailsMapper.map(transaction, source, destination)
        );
    }

    private void logTransactionDetails(TransactionDetails transactionsDetails) {
        log.info("Received record: {}", transactionsDetails);
    }
}
