package io.github.deepakjvm.vaultiq.mapper;

import org.springframework.stereotype.Component;

import io.github.deepakjvm.vaultiq.dto.TransactionDTO;
import io.github.deepakjvm.vaultiq.entity.Transaction;

@Component
public class TransactionMapper {

    public TransactionDTO toDto(Transaction transaction) {
        return new TransactionDTO(transaction);
    }

}
