package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Loan;
import java.util.ArrayList;
import java.util.List;

public class LoanDTO {

	private long id;
	private List<Integer> payments = new ArrayList<>();
	private String name;
	private double maxAmount;

	private double interestRate;


	public LoanDTO () {}

	public LoanDTO(Loan loan) {
		this.id = loan.getId();
		this.payments = loan.getPayments();
		this.name = loan.getName();
		this.maxAmount = loan.getMaxAmount();
		this.interestRate = loan.getInterestRate();
	}

	public long getId() {return id;}

	public List<Integer> getPayments() {return payments;}

	public String getName() {return name;}

	public double getMaxAmount() {return maxAmount;}

	public double getInterestRate() {return interestRate;}
}
