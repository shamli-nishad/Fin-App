package com.sha.fin.app.transaction.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sha.fin.app.transaction.entity.TransactionEntity;
import com.sha.fin.app.transaction.model.TransactionType;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long>{
	
	 List<TransactionEntity> findByUserId(String userId);
	 
	 List<TransactionEntity> findByUserIdAndDateBetween(String userId, LocalDate startDate, LocalDate endDate);
	 
	 List<TransactionEntity> findByUserIdAndType(String userId, TransactionType type);
	 
	 List<TransactionEntity> findByUserIdAndCategory(String userId, String category);

}
