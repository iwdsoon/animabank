package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Client;
import org.jetbrains.annotations.NotNull;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientDTO {

	private long id;

	private String firstName;
	private String lastName;

	private String fullName;
	private String email;
	private Set<AccountDTO> accounts = new HashSet<AccountDTO>();

	private Set <ClientLoanDTO> loans = new HashSet<>();

	private Set <CardDTO> cards = new HashSet<>();

	private String password; //remover

	public ClientDTO(){}

	public ClientDTO (@NotNull Client client) {
		this.id = client.getId();
		this.firstName = client.getFirstName();
		this.lastName = client.getLastName();
		this.fullName = client.getFullName();
		this.email = client.getEmail();
		this.accounts = client.getAccounts().stream().filter(account -> account.isActive()).map(AccountDTO::new).collect(Collectors.toSet());
		this.loans = client.getClientLoans().stream().map(ClientLoanDTO::new).collect(Collectors.toSet());
		this.cards = client.getCards().stream().filter(card -> card.isActive()).map(CardDTO::new).collect(Collectors.toSet());

	}

	public long getId() {return id;}

	public String getFirstName() {return firstName;}

	public String getLastName() {return lastName;}

	public String getFullName() {return fullName;}

	public String getEmail() {return email;}

	public Set<AccountDTO> getAccounts(){return accounts;}

	public Set<ClientLoanDTO> getLoans() {return loans;}

	public Set<CardDTO> getCards() {return cards;}
}
