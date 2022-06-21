package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;
import com.mindhub.homebanking.models.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static java.util.stream.Collectors.toList;
import static com.mindhub.homebanking.utilities.Utility.getRandomNumber;

@RestController
@RequestMapping("/api")
public class AccountController {

	@Autowired
	private AccountService accountService;

	@Autowired
	private ClientService clientService;

	@Autowired
	PasswordEncoder passwordEncoder;

	@GetMapping("/accounts")
	public List<AccountDTO> getAccounts(){
		return accountService.getAccounts();
	}

	@GetMapping("/accounts/{id}")
	public AccountDTO getAccount(@PathVariable Long id){
		return accountService.getAccount(id);
	}

	@GetMapping("/clients/current/accounts/{id}")
	public AccountDTO getAccount(Authentication authentication, @PathVariable Long id){
		Client currentClient = clientService.getCurrentClient(authentication);
		Account currentAccount = currentClient.getAccounts().stream().filter(account -> account.getId() == id && account.isActive()).findFirst().orElse(null);
		return new AccountDTO(currentAccount);
	}

	@PostMapping(path= "/clients/current/accounts")
	public ResponseEntity<Object> createNewAccount(Authentication authentication, @RequestParam AccountType type){
		Client currentClient = clientService.getCurrentClient(authentication);
		if (currentClient.getAccounts().stream().filter(account -> account.isActive()).count() >= 3){
			return new ResponseEntity<>("You reached the max amount of accounts you can create", HttpStatus.FORBIDDEN);
		}

		Account newAccount = new Account("VIN-" + getRandomNumber(10000000, 99999999), LocalDateTime.now(), 0, currentClient, type);
		accountService.saveAccount(newAccount);
		return new ResponseEntity<>("Account successfully created", HttpStatus.CREATED);
	}

	@GetMapping("/clients/current/accounts")
	public List<AccountDTO> getCurrentAccounts(Authentication authentication){
		Client currentClient = clientService.getCurrentClient(authentication);
		return currentClient.getAccounts().stream().filter(account -> account.isActive()).map(AccountDTO::new).collect(toList());
	}

	@Transactional
	@PatchMapping("/clients/current/accounts")
	public ResponseEntity <Object> deleteAccount(Authentication authentication, @RequestParam long id, @RequestParam String password){
		Client currentClient = clientService.getCurrentClient(authentication);
		Account currentAccount = currentClient.getAccounts().stream().filter(account -> account.getId() == id && account.isActive()).findFirst().orElse(null);

		if (!passwordEncoder.matches(password,currentClient.getPassword())) {
			return new ResponseEntity<>("wrong pass",HttpStatus.FORBIDDEN);
		}

		if(currentAccount.getBalance() > 0){
			return new ResponseEntity<>("you need to transfer the money in this account before disable it",HttpStatus.FORBIDDEN);
		}

		if (!currentClient.getAccounts().contains(currentAccount)){
			return new ResponseEntity<>("not your account", HttpStatus.FORBIDDEN);
		}

		currentAccount.setActive(false);
		accountService.saveAccount(currentAccount);
		return new ResponseEntity<>("account disabled",HttpStatus.CREATED);

	}

}
