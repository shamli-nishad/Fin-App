package com.sha.fin.app.transaction.service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.hibernate.TransactionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sha.fin.app.transaction.entity.TransactionEntity;
import com.sha.fin.app.transaction.error.TransactionCustomException;
import com.sha.fin.app.transaction.model.TransactionType;
import com.sha.fin.app.transaction.repository.TransactionRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class TransactionServiceImpl implements TransactionService {
	
	@Autowired
	TransactionRepository transactionRepository;

	@Override
	public TransactionEntity addTransaction(TransactionEntity transaction) {
		
		transaction.setDate(Instant.now());
		
		validateTransaction(transaction);
		
		return transactionRepository.save(transaction);
	}
	
	@Override
	public Optional<TransactionEntity> updateTransaction(Long id, TransactionEntity transactionDetails) {

		Optional<TransactionEntity> transactionOptional = transactionRepository.findById(id);
		
        transactionOptional.ifPresent(existingTransaction -> {
            copyNonNullProperties(transactionDetails, existingTransaction);
//            validateTransaction(existingTransaction);
            transactionRepository.save(existingTransaction);
        });
        
        log.info("Transaction update {}", id);
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
		log.info("Transaction deleted {}", id);
	}

	@Override
	public List<TransactionEntity> getTransactionsByUserId(String userId) {
		List<TransactionEntity> result =  transactionRepository.findByUserId(userId);
		
		if(result.isEmpty()) {
			throw new TransactionCustomException("No Transactions found for the given criteria", HttpStatus.NOT_FOUND.name(), HttpStatus.NOT_FOUND.value());
		} else {
			return result;
		}
	}

	@Override
	public List<TransactionEntity> getTransactionsByUserIdAndDateRange(String userId, LocalDate startDate,
			LocalDate endDate) {
		List<TransactionEntity> result =  transactionRepository.findByUserIdAndDateBetween(userId, startDate, endDate);
		
		if(result.isEmpty()) {
			throw new TransactionCustomException("No Transactions found for the given criteria", HttpStatus.NOT_FOUND.name(), HttpStatus.NOT_FOUND.value());
		} else {
			return result;
		}
	}

	@Override
	public List<TransactionEntity> getTransactionsByUserIdAndType(String userId, TransactionType type) {
		
		List<TransactionEntity> result =  transactionRepository.findByUserIdAndType(userId, type);
		
		
		if(result.isEmpty()) {
			throw new TransactionCustomException("No Transactions found for the given criteria", HttpStatus.NOT_FOUND.name(), HttpStatus.NOT_FOUND.value());
		} else {
			return result;
		}
	}

	@Override
	public List<TransactionEntity> getTransactionsByUserIdAndCategory(String userId, String category) {
		
		List<TransactionEntity> result = transactionRepository.findByUserIdAndCategory(userId, category);
		
		if(result.isEmpty()) {
			throw new TransactionCustomException("No Transactions found for the given criteria", HttpStatus.NOT_FOUND.name(), HttpStatus.NOT_FOUND.value());
		} else {
			return result;
		}
	}

	@Override
	public Double calculateCurrentBalance(String userId) {
		
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
	
	@Override
	public Double calculateNewBalance(Double currentBalance, TransactionEntity newTransaction) {
		
        double balance = 0.0;
        
            if (newTransaction.getType() == TransactionType.INCOME) {
                balance = currentBalance + newTransaction.getAmount();
            } else {
                balance = currentBalance - newTransaction.getAmount();
            }
            
        return balance;
	
	}
	
	@Override
	public Double calculateNewBalance(TransactionEntity newTransaction) {
		
		Double currentBalance = calculateCurrentBalance(newTransaction.getUserId());
		
        double balance = 0.0;
        
            if (newTransaction.getType() == TransactionType.INCOME) {
                balance = currentBalance + newTransaction.getAmount();
            } else {
                balance = currentBalance - newTransaction.getAmount();
            }
            
        return balance;
	
	}
	
	private void validateTransaction(TransactionEntity transaction) {
		
		Double postTransactionBalance = calculateCurrentBalance(transaction.getUserId());
		
		log.info("Post transaction balance is {}", postTransactionBalance);
		
		transaction.setPostTransactionBalance(calculateNewBalance(postTransactionBalance, transaction));
		
		// call the Budget Service and check if any budget criteria is met. If so, call alert service to send an alert.
	}

}
