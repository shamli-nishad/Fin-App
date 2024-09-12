package com.sha.fin.app.budget.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sha.fin.app.budget.entity.BudgetEntity;
import com.sha.fin.app.budget.respository.BudgetRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class BudgetServiceImpl implements BudgetService {
	
	@Autowired
	BudgetRepository budgetRepository;
	

	@Override
	public List<BudgetEntity> getAllBudgets() {
		return budgetRepository.findAll();
	}

	@Override
	public Optional<BudgetEntity> getBudgetById(Long id) {
		
		if(id == null || id == 0) {
			return Optional.empty();	
		} else {
			return budgetRepository.findById(id);
		}
		
	}

	@Override
	public BudgetEntity createBudget(BudgetEntity budget) {
		return budgetRepository.save(budget);
	}

	@Override
	public Optional<BudgetEntity> updateBudget(Long id, BudgetEntity budget) {
		
		Optional<BudgetEntity> existingBudgetOptional = budgetRepository.findById(id);
		
		existingBudgetOptional.ifPresent(existingBudget -> {
			
			copyNonNullProperties(budget, existingBudget);
			budgetRepository.save(existingBudget);
		});
		
		log.info("Transaction update {}", id);
		return existingBudgetOptional;
	}
	
	private void copyNonNullProperties(BudgetEntity source, BudgetEntity target) {
		Optional.ofNullable(source.getCategory()).ifPresent(target::setCategory);
		Optional.ofNullable(source.getAmount()).ifPresent(target::setAmount);
        Optional.ofNullable(source.getStartDate()).ifPresent(target::setStartDate);
        Optional.ofNullable(source.getEndDate()).ifPresent(target::setEndDate);        
    }

	@Override
	public void deleteBudget(Long id) {
		
		budgetRepository.deleteById(id);
		log.info("Transaction deleted {}", id);
		
	}

	@Override
	public Optional<List<BudgetEntity>> getBudgetByUserId(String userId) {
		
		return budgetRepository.findByUserId(userId);
	}

	@Override
	public Optional<List<BudgetEntity>> getBudgetByUserIdAndCategory(String userId, String category) {
		return budgetRepository.findByUserIdAndCategory(userId, category);
	}

}
