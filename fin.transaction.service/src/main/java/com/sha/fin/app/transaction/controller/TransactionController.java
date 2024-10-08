package com.sha.fin.app.transaction.controller;

import java.time.LocalDate;
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

import com.sha.fin.app.transaction.entity.TransactionEntity;
import com.sha.fin.app.transaction.model.TransactionType;
import com.sha.fin.app.transaction.model.TransactionUpdateRequest;
import com.sha.fin.app.transaction.service.TransactionService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/api/v1/transactions")
@Log4j2
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	@PostMapping
	public ResponseEntity<TransactionEntity> addTransaction(@RequestBody TransactionEntity transaction) {
		
		return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.addTransaction(transaction));
	}

	@PutMapping("/{id}")
	public ResponseEntity<TransactionEntity> updateTransaction(@PathVariable Long id,
			@RequestBody TransactionUpdateRequest transactionUpdateRequest) {
		
		TransactionEntity transactionDetails = new TransactionEntity().builder()
				.category(Optional.ofNullable(transactionUpdateRequest.getCategory()).orElse(null))
				.description(Optional.ofNullable(transactionUpdateRequest.getDescription()).orElse(null))
				.build();

		Optional<TransactionEntity> resultOptional = transactionService.updateTransaction(id, transactionDetails);

		return resultOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {

		transactionService.deleteTransaction(id);

		return ResponseEntity.ok().build();

	}

	@GetMapping
	public ResponseEntity<List<TransactionEntity>> getTransactionsByUserId(@RequestParam String userId,
			@RequestParam(required = false) LocalDate startDate, @RequestParam(required = false) LocalDate endDate,
			@RequestParam(required = false) TransactionType type, @RequestParam(required = false) String category) {
		
		
		if(null == userId || userId.isBlank()) {
			log.info("userId given is blank");
			return ResponseEntity.noContent().build();
		}

		List<TransactionEntity> result;

		if (startDate != null && endDate != null) {
			result = transactionService.getTransactionsByUserIdAndDateRange(userId, startDate, endDate);
		} else if (type != null) {
			result = transactionService.getTransactionsByUserIdAndType(userId, type);
		} else if (category != null) {
			result = transactionService.getTransactionsByUserIdAndCategory(userId, category);
		} else {
			result = transactionService.getTransactionsByUserId(userId);
		}

		if (result.isEmpty()) {
			log.info("No response for the given input {}",userId);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.ok(result);
		}

	}

}
