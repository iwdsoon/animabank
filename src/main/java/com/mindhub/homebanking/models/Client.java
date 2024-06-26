package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;
import org.jetbrains.annotations.NotNull;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Client {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private long id;

	@OneToMany(mappedBy="client", fetch=FetchType.EAGER)
	private Set<Account> accounts = new HashSet<Account>();

	@OneToMany(mappedBy="client", fetch=FetchType.EAGER)
	private Set<ClientLoan> clientLoans = new HashSet<>();

	@OneToMany(mappedBy="client", fetch=FetchType.EAGER)
	private Set <Card> cards = new HashSet<>();

	private String firstName;
	private String lastName;
	private String email;

	private String password;

	public Client() {}
	public Client (String firstName, String lastName, String email, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}

	public long getId() {return id;}

	public String getFirstName(){return firstName;}
	public void setFirstName(String firstName){this.firstName = firstName;}

	public String getLastName() {return lastName;}
	public void setLastName(String lastName) {this.lastName = lastName;}

	public String getFullName(){return firstName + " " + lastName;}

	public String getEmail(){return email;}
	public void setEmail(String email) {this.email = email;}

	public String getPassword() {return password;}
	public void setPassword(String password) {this.password = password;}

	public Set<Account> getAccounts(){return accounts;}
	public void addAccount(@NotNull Account account){
		account.setClient(this);
		accounts.add(account);
	}

	public Set<ClientLoan> getClientLoans() {return clientLoans;}
	public void addClientLoan (ClientLoan clientLoan) {
		clientLoan.setClient(this);
		clientLoans.add(clientLoan);

	}

	public Set<Loan> getLoans(){
		return clientLoans.stream().map(clientLoan -> clientLoan.getLoan()).collect(Collectors.toSet());
	}

	public Set<Card> getCards() {return cards;}
	public void addCard (Card card){
		card.setClient(this);
		cards.add(card);
	}

	@Override
	public String toString() {
		return "Client{" +
				"firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", email='" + email + '\'' +
				'}';
	}
}
