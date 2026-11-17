package io.github.deepakjvm.vaultiq.service;

import java.util.List;

import io.github.deepakjvm.vaultiq.dto.TransactionDTO;

public interface TransactionService {

	List<TransactionDTO> getAllTransactionsByAccountNumber(String accountNumber);
	void sendBankStatementByEmail(String accountNumber);

}
