package com.sha.fin.app.transaction.external.model;

import java.util.List;

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
