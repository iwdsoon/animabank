package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class ClientLoan {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private long id;

	@ManyToOne
	@JoinColumn(name = "client_id")
	private Client client;

	@ManyToOne
	@JoinColumn(name = "loan_id")
	private Loan loan;

	private double amount;
	private int payments;

	public ClientLoan() {}

	public ClientLoan(double amount, int payments, Client client, Loan loan) {
		this.amount = amount;
		this.payments = payments;
		this.client = client;
		this.loan = loan;
	}

	public long getId() {return id;}

	public double getAmount() {return amount;}
	public void setAmount(double amount) {this.amount = amount;}

	public int getPayments() {return payments;}
	public void setPayments(int payments) {this.payments = payments;}

	public Loan getLoan() {return loan;}
	public void setLoan(Loan loan) {this.loan = loan;}

	public Client getClient() {return client;}
	public void setClient(Client client) {this.client = client;}

}
