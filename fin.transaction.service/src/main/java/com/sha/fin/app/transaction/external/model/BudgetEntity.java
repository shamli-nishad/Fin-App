package com.sha.fin.app.transaction.external.model;

import java.math.BigDecimal;
import java.time.LocalDate;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BudgetEntity {
	
	private Long id;
	
	private String userId;

	private String category;
    
    private BigDecimal amount;
	
    private Float percent; 
    
    private LocalDate startDate;
    
    private BudgetCriteria criteria;
    
    private LocalDate endDate;
    
    private String description;

}
