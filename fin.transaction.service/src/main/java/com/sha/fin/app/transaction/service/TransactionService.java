package com.sha.fin.app.transaction.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.sha.fin.app.transaction.entity.TransactionEntity;
import com.sha.fin.app.transaction.model.TransactionType;

public interface TransactionService {

	public TransactionEntity addTransaction(TransactionEntity transaction);

	public Optional<TransactionEntity> updateTransaction(Long id, TransactionEntity transactionDetails);

	public void deleteTransaction(Long id);

	public List<TransactionEntity> getTransactionsByUserId(String userId);

	public List<TransactionEntity> getTransactionsByUserIdAndDateRange(String userId, LocalDate startDate, LocalDate endDate);
	
	public List<TransactionEntity> getTransactionsByUserIdAndType(String userId, TransactionType type); 
	
	public List<TransactionEntity> getTransactionsByUserIdAndCategory(String userId, String category);
	
	public Double calculateCurrentBalance(String userId); 
	
	public Double calculateNewBalance(Double currentBalance, TransactionEntity newTransaction);
	
	public Double calculateNewBalance(TransactionEntity newTransaction);

	
}
