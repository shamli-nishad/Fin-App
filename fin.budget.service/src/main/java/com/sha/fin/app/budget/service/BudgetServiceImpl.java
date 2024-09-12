package com.sha.fin.app.budget.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sha.fin.app.budget.entity.BudgetEntity;
import com.sha.fin.app.budget.model.BudgetCriteria;
import com.sha.fin.app.budget.model.BudgetCriteriaResponse;
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

	@Override
	public BudgetCriteriaResponse checkBudgetCriteriaMet(String userId, String category, double amount) {
		
		
		BudgetCriteriaResponse result = new BudgetCriteriaResponse();

		Optional<List<BudgetEntity>> budgetOptional = getBudgetByUserIdAndCategory(userId, category);

		List<BudgetEntity> satisfieldBudgets = null;

		if (budgetOptional.isPresent()) {
			satisfieldBudgets = budgetOptional.get().stream().filter((budget) -> {

				switch (budget.getCriteria()) {
				case BudgetCriteria.ZERO:
					return amount == 0;
				case BudgetCriteria.NON_ZERO:
					return amount != 0;
				case BudgetCriteria.EQUAL:
					return (budget.getAmount().compareTo(BigDecimal.valueOf(amount)) == 0);
				case BudgetCriteria.GREATER_OR_EQUAL:
					return (budget.getAmount().compareTo(BigDecimal.valueOf(amount)) <= 0);
				case BudgetCriteria.LESSER_OR_EQUAL:
					return (budget.getAmount().compareTo(BigDecimal.valueOf(amount)) >= 0);
				case BudgetCriteria.LESS:
					return (budget.getAmount().compareTo(BigDecimal.valueOf(amount)) > 0);
				case BudgetCriteria.GREATER:
					return (budget.getAmount().compareTo(BigDecimal.valueOf(amount)) < 0);
				default:
					return false;
				}
			}

			).collect(Collectors.toList());
		}

		if (null != satisfieldBudgets && !satisfieldBudgets.isEmpty()) {
			result.setCriteriaStatus("One more Budget Criteria has been met");
			result.setCriteriaMetBudgets(satisfieldBudgets);

		} else {
			result.setCriteriaStatus("Budget Criteria is not met");
		}
		
		return result;
	}
	

}
