package com.sha.fin.app.transaction.entity;

import java.time.Instant;
import java.time.LocalDate;

import com.sha.fin.app.transaction.model.TransactionType;

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
@Table(name = "transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String userId;
	
	@Column(nullable =  false)
	private double amount;
	
	@Column(nullable =  false)
	private Instant date;
	
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
	private TransactionType type;
    
    private double postTransactionBalance;
	
	private String category;

    private String description;

}
