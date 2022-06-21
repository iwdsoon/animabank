package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Loan {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private long id;

	@ElementCollection
	@Column(name="payments")
	private List<Integer> payments = new ArrayList<>();

	@OneToMany(mappedBy="client", fetch=FetchType.EAGER) //mappedby=loan?
	private Set<ClientLoan> clientLoans = new HashSet<>();

	private String name;
	private double maxAmount;

	private double interestRate;


	public Loan() {}

	public Loan(String name, double maxAmount, List<Integer> payments, double interestRate) {
		this.name = name;
		this.maxAmount = maxAmount;
		this.payments = payments;
		this.interestRate = interestRate;
	}

	public long getId() {return id;}

	public String getName() {return name;}
	public void setName(String name) {this.name = name;}

	public double getMaxAmount() {return maxAmount;}
	public void setMaxAmount(double maxAmount) {this.maxAmount = maxAmount;}

	public List<Integer> getPayments() {return payments;}
	public void setPayments(List<Integer> payments) {this.payments = payments;}

	public Set<ClientLoan> getClientLoans() {return clientLoans;}

	public void addClientLoan(ClientLoan clientLoan){
		clientLoan.setLoan(this);
		clientLoans.add(clientLoan);
	}

	public List <Client> getClients(){
		return clientLoans.stream().map(clientLoan -> clientLoan.getClient()).collect(Collectors.toList());
	}

	public double getInterestRate() {return interestRate;}
	public void setInterestRate(double interestRate) {this.interestRate = interestRate;}

}
