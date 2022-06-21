package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import java.time.LocalDateTime;

public class TransactionDTO {

	private long id;
	private TransactionType type;
	private double amount;
	private LocalDateTime date;
	private String description;
	private double newAmount;


	public TransactionDTO () {}

	public TransactionDTO (Transaction transaction) {
		this.id = transaction.getId();
		this.type = transaction.getType();
		this.amount = transaction.getAmount();
		this.date = transaction.getDate();
		this.description = transaction.getDescription();
		this.newAmount = transaction.getNewAmount();
	}

	public long getId() {return id;}

	public TransactionType getType() {return type;}

	public double getAmount() {return amount;}

	public LocalDateTime getDate() {return date;}

	public String getDescription() {return description;}

	public double getNewAmount() {return newAmount;}

}
