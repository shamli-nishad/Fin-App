package com.sha.fin.app.transaction.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sha.fin.app.transaction.entity.TransactionEntity;
import com.sha.fin.app.transaction.model.TransactionType;
import com.sha.fin.app.transaction.repository.TransactionRepository;

@Service
public class TransactionServiceImpl implements TransactionService {
	
	@Autowired
	TransactionRepository transactionRepository;

	@Override
	public TransactionEntity addTransaction(TransactionEntity transaction) {
		return transactionRepository.save(transaction);
	}

	@Override
	public Optional<TransactionEntity> updateTransaction(Long id, TransactionEntity transactionDetails) {

		Optional<TransactionEntity> transactionOptional = transactionRepository.findById(id);
		
        transactionOptional.ifPresent(existingTransaction -> {
            copyNonNullProperties(transactionDetails, existingTransaction);
            transactionRepository.save(existingTransaction);
        });
        
        return transactionOptional;
	}
	
	private void copyNonNullProperties(TransactionEntity source, TransactionEntity target) {
		Optional.ofNullable(source.getAmount()).ifPresent(target::setAmount);
        Optional.ofNullable(source.getDate()).ifPresent(target::setDate);
        Optional.ofNullable(source.getCategory()).ifPresent(target::setCategory);
        Optional.ofNullable(source.getType()).ifPresent(target::setType);
        Optional.ofNullable(source.getDescription()).ifPresent(target::setDescription);
    }

	@Override
	public void deleteTransaction(Long id) {

		transactionRepository.deleteById(id);
	}

	@Override
	public List<TransactionEntity> getTransactionsByUserId(String userId) {
		return transactionRepository.findByUserId(userId);
	}

	@Override
	public List<TransactionEntity> getTransactionsByUserIdAndDateRange(String userId, LocalDate startDate,
			LocalDate endDate) {
		return transactionRepository.findByUserIdAndDateBetween(userId, startDate, endDate);
	}

	@Override
	public List<TransactionEntity> getTransactionsByUserIdAndType(String userId, TransactionType type) {
		return transactionRepository.findByUserIdAndType(userId, type);
	}

	@Override
	public List<TransactionEntity> getTransactionsByUserIdAndCategory(String userId, String category) {
		return transactionRepository.findByUserIdAndCategory(userId, category);
	}

	@Override
	public Double calculateBalance(String userId) {
		
		List<TransactionEntity> transactions = transactionRepository.findByUserId(userId);
		
        double balance = 0.0;
        
        for (TransactionEntity transaction : transactions) {
            if (transaction.getType() == TransactionType.INCOME) {
                balance += transaction.getAmount();
            } else {
                balance -= transaction.getAmount();
            }
        }
        return balance;
	
	}

}
