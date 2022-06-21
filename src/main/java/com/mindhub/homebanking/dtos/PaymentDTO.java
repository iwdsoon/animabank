package com.mindhub.homebanking.dtos;

import java.time.LocalDateTime;

public class PaymentDTO {

	private String number;
	private short cvv;
	private String name;
	private double amount;
	private String description;
	private LocalDateTime thruDate;

	public PaymentDTO(){}

	public PaymentDTO(String number, short cvv, String name, double amount, String description, LocalDateTime thruDate) {
		this.number = number;
		this.cvv = cvv;
		this.name = name;
		this.amount = amount;
		this.description = description;
		this.thruDate = thruDate;
	}

	public String getNumber() {return number;}

	public short getCvv() {return cvv;}

	public String getName() {return name;}

	public double getAmount() {return amount;}

	public String getDescription() {return description;}

	public LocalDateTime getThruDate() {return thruDate;}
}
