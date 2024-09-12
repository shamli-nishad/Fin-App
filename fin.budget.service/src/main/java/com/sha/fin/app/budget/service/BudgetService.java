package com.sha.fin.app.budget.service;

import java.util.List;
import java.util.Optional;

import com.sha.fin.app.budget.entity.BudgetEntity;
import com.sha.fin.app.budget.model.BudgetCriteriaResponse;

public interface BudgetService {
	
	List<BudgetEntity> getAllBudgets();
	
	Optional<BudgetEntity> getBudgetById(Long id);
	
	BudgetEntity createBudget(BudgetEntity budget);
	
	Optional<BudgetEntity> updateBudget(Long id, BudgetEntity budget);
	
	void deleteBudget(Long id);

	Optional<List<BudgetEntity>> getBudgetByUserId(String userId);

	Optional<List<BudgetEntity>> getBudgetByUserIdAndCategory(String userId, String category);
	
	BudgetCriteriaResponse checkBudgetCriteriaMet(String userId, String category, double amount);

}
