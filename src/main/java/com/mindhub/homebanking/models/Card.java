package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Card {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="client_id")
	private Client client;

	private String cardHolder;
	private CardType type;
	private CardColor color;
	private String number;
	private short cvv;
	private LocalDateTime fromDate;
	private LocalDateTime thruDate;
	private boolean active;

	public Card() {}

	public Card(CardType type, CardColor color, String number, short cvv, LocalDateTime fromDate, LocalDateTime thruDate, Client client) {
		this.cardHolder = client.getFullName();
		this.type = type;
		this.color = color;
		this.number = number;
		this.cvv = cvv;
		this.fromDate = fromDate;
		this.thruDate = thruDate;
		this.client = client;
		this.active = true;
	}

	public long getId() {return id;}

	public Client getClient() {return client;}
	public void setClient(Client client) {this.client = client;}

	public String getCardHolder() {return cardHolder;}
	public void setCardHolder(String cardholder) {this.cardHolder = cardholder;}

	public CardType getType() {return type;}
	public void setType(CardType type) {this.type = type;}

	public CardColor getColor() {return color;}
	public void setColor(CardColor color) {this.color = color;}

	public String getNumber() {return number;}
	public void setNumber(String number) {this.number = number;}

	public short getCvv() {return cvv;}
	public void setCvv(short cvv) {this.cvv = cvv;}

	public LocalDateTime getFromDate() {return fromDate;}
	public void setFromDate(LocalDateTime fromDate) {this.fromDate = fromDate;}

	public LocalDateTime getThruDate() {return thruDate;}
	public void setThruDate(LocalDateTime thruDate) {this.thruDate = thruDate;}

	public boolean isActive() {return active;}

	public void setActive(boolean active) {this.active = active;}
}


