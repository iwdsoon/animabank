package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="client_id")
	private Client client;

	@OneToMany(mappedBy="account", fetch=FetchType.EAGER)
	private Set<Transaction> transactions = new HashSet<Transaction>();

	private String number;
	private LocalDateTime creationDate;
	private double balance;

	private boolean active;

	private AccountType type;



	public Account() {}

	public Account(String number, LocalDateTime creation, double balance, Client client, AccountType type){
		this.number = number;
		this.creationDate = creation;
		this.balance = balance;
		this.client = client;
		this.active = true;
		this.type = type;
	}

	public long getId() {return id;}

	public String getNumber() {return number;}
	public void setNumber(String number) {this.number = number;}

	public LocalDateTime getCreationDate() {return creationDate;}
	public void setCreationDate(LocalDateTime creationDate) {this.creationDate = creationDate;}

	public double getBalance() {return balance;}
	public void setBalance(double balance) {this.balance = balance;}

	public Client getClient() {return client;}
	public void setClient(Client client) {this.client = client;}

	public Set<Transaction> getTransactions() {return transactions;}
	public void setTransactions(Set<Transaction> transactions) {
		this.transactions = transactions;
	}

	public boolean isActive() {return active;}
	public void setActive(boolean active) {this.active = active;}

	public AccountType getType() {return type;}

	public void setType(AccountType type) {this.type = type;}
}
