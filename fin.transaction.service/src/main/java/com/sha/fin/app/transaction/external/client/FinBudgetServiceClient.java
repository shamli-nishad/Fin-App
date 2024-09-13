package com.sha.fin.app.transaction.external.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sha.fin.app.transaction.external.model.BudgetCriteriaResponse;

@FeignClient(name = "BUDGET-SERVICE/api/v1/budgets")
public interface FinBudgetServiceClient {
	
	
	@GetMapping("/validate")
	public ResponseEntity<BudgetCriteriaResponse> checkBudgetCriteriaMet (@RequestParam String userId, 
			@RequestParam String category, @RequestParam double amount);

}
