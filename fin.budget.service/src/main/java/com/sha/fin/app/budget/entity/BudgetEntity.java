package com.sha.fin.app.budget.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.sha.fin.app.budget.model.BudgetCriteria;
import com.sha.fin.app.budget.model.BudgetCriteriaType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "budget")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BudgetEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String userId;

	@Column(nullable = false)
	private String category;
    
	@Column(nullable = false)
    private BigDecimal amount;
	
	@Column(nullable = false)
    private Float percent; 
    
    @Column(nullable = false)
    private LocalDate startDate;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BudgetCriteria criteria;
    
    private LocalDate endDate;
    
    private String description;

}
