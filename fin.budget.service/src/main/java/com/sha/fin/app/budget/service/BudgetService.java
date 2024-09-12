package com.sha.fin.app.budget.service;

import java.util.List;
import java.util.Optional;

import com.sha.fin.app.budget.entity.BudgetEntity;

public interface BudgetService {
	
	public List<BudgetEntity> getAllBudgets();
	
	public Optional<BudgetEntity> getBudgetById(Long id);
	
	public BudgetEntity createBudget(BudgetEntity budget);
	
	public Optional<BudgetEntity> updateBudget(Long id, BudgetEntity budget);
	
	public void deleteBudget(Long id);

	public Optional<List<BudgetEntity>> getBudgetByUserId(String userId);

	public Optional<List<BudgetEntity>> getBudgetByUserIdAndCategory(String userId, String category);

}
