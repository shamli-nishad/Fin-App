package com.sha.fin.app.budget.respository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sha.fin.app.budget.entity.BudgetEntity;

@Repository
public interface BudgetRepository extends JpaRepository<BudgetEntity, Long>{
	
	public Optional<List<BudgetEntity>> findByUserId(String userId);

	public Optional<List<BudgetEntity>> findByUserIdAndCategory(String userId, String category);

}
