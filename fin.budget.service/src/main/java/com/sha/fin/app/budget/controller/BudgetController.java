package com.sha.fin.app.budget.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sha.fin.app.budget.entity.BudgetEntity;
import com.sha.fin.app.budget.model.BudgetCriteriaResponse;
import com.sha.fin.app.budget.service.BudgetService;

@RestController
@RequestMapping("/api/v1/budgets")
public class BudgetController {

	@Autowired
	private BudgetService budgetService;

//	@GetMapping
//	public ResponseEntity<List<BudgetEntity>> getAllBudgets() {
//		return ResponseEntity.status(HttpStatus.OK).body(budgetService.getAllBudgets());
//	}

	@GetMapping("/{id}")
	public ResponseEntity<BudgetEntity> getBudgetById(@PathVariable Long id) {

		Optional<BudgetEntity> budget = budgetService.getBudgetById(id);

		return budget.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

	}
	
	@GetMapping
	public ResponseEntity<List<BudgetEntity>> getBudgetByUserId(@RequestParam String userId, 
			@RequestParam(required = false) String category ) {
		
		Optional<List<BudgetEntity>> budgetOptional;  
		
		if(null == category || category.isBlank()) {
			budgetOptional = budgetService.getBudgetByUserId(userId);
		} else {
			budgetOptional = budgetService.getBudgetByUserIdAndCategory(userId, category);
		}

		return budgetOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

	}
	
	@GetMapping("/validate")
	public ResponseEntity<BudgetCriteriaResponse> checkBudgetCriteriaMet (@RequestParam String userId, 
			@RequestParam(required = false) String category, double amount){
		return ResponseEntity.ok(budgetService.checkBudgetCriteriaMet(userId, category, amount));
	}

	
	@PostMapping
	public ResponseEntity<BudgetEntity> createBudget(@RequestBody BudgetEntity budget) {

		BudgetEntity result = budgetService.createBudget(budget);

		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<BudgetEntity> updateBudget(@PathVariable Long id, @RequestBody BudgetEntity budget){
		
		Optional<BudgetEntity> resultOptional = budgetService.updateBudget(id, budget);
		
		return resultOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteBudget(@PathVariable Long id) {

		budgetService.deleteBudget(id);

		return ResponseEntity.status(HttpStatus.OK).build();
	}

}
