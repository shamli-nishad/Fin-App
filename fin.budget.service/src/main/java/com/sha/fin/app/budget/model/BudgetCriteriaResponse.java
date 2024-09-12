package com.sha.fin.app.budget.model;

import java.util.List;

import com.sha.fin.app.budget.entity.BudgetEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BudgetCriteriaResponse {
	
	private String criteriaStatus;
	
	List<BudgetEntity> criteriaMetBudgets;

}
