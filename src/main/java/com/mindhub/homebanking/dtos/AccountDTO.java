package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDTO {

	private long id;
	private String number;
	private LocalDateTime creationDate;
	private double balance;

	private AccountType type;

	private Set<TransactionDTO> transactions = new HashSet<TransactionDTO>();



	public AccountDTO(){}

	public AccountDTO (Account account) {
		this.id = account.getId();
		this.number = account.getNumber();
		this.type = account.getType();
		this.creationDate = account.getCreationDate();
		this.balance = account.getBalance();
		this.transactions = account.getTransactions().stream().filter(transaction -> account.isActive()).map(TransactionDTO::new).collect(Collectors.toSet());
	}

	public long getId() {return id;}

	public String getNumber() {return number;}

	public LocalDateTime getCreationDate() {return creationDate;}

	public double getBalance() {return balance;}

	public Set<TransactionDTO> getTransactions() {return transactions;}

	public AccountType getType() {return type;}
}
