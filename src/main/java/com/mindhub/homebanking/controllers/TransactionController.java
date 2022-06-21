package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.TransactionDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.TransactionService;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.mindhub.homebanking.models.TransactionType.CREDIT;
import static com.mindhub.homebanking.models.TransactionType.DEBIT;

@RestController
@RequestMapping("/api")
public class TransactionController {

	@Autowired
	private AccountService accountService;

	@Autowired
	private ClientService clientService;

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private TransactionRepository transactionRepository;



	@GetMapping("clients/current/accounts/transactions/latest")
	public List<TransactionDTO> lastTransactions(Authentication authentication){
		Client currentClient = clientService.getCurrentClient(authentication);
		List <Account> accounts = currentClient.getAccounts().stream().filter(Account::isActive).collect(Collectors.toList());
		Set <Transaction> transactions = new HashSet<>();
		accounts.stream().forEach(account -> { transactions.addAll(account.getTransactions());
		});
		return transactions.stream().map(transaction -> new TransactionDTO(transaction)).sorted(Comparator.comparing(TransactionDTO::getId).reversed()).limit(5).collect(Collectors.toList());
	}


	@Transactional
	@PostMapping(path = "/transactions")
	public ResponseEntity<Object> newTransaction (Authentication authentication, @RequestParam String ownNumberAccount, @RequestParam Double amount, @RequestParam String description, @RequestParam String numberDestiny){

		Account originAccount = accountService.findByNumber(ownNumberAccount);
		Account destinyAccount = accountService.findByNumber(numberDestiny);
		Client currentClient = clientService.getCurrentClient(authentication);

		if (ownNumberAccount.isEmpty()){
			return new ResponseEntity<>("Missing own number account", HttpStatus.FORBIDDEN);
		}

		if (description.isEmpty()){
			return new ResponseEntity<>("Missing Description", HttpStatus.FORBIDDEN);
		}

		if (numberDestiny.isEmpty()){
			return new ResponseEntity<>("Missing destiny number account", HttpStatus.FORBIDDEN);
		}

		if (amount == 0 || amount.isNaN() || amount.isInfinite()){
			return new ResponseEntity<>("Wrong amount", HttpStatus.FORBIDDEN);
		}

		if (ownNumberAccount.equals(numberDestiny)){
				return new ResponseEntity<>("Can't transfer to the same account", HttpStatus.FORBIDDEN);
		}

		if (originAccount == null){
			return new ResponseEntity<>("Account doesn't exist",HttpStatus.FORBIDDEN);
		}

		if (!currentClient.getAccounts().contains(originAccount)){
			return new ResponseEntity<>("Not your account", HttpStatus.FORBIDDEN);
		}

		if (destinyAccount == null){
			return new ResponseEntity<>("Account doesn't exist",HttpStatus.FORBIDDEN);
		}

		if(originAccount.getBalance() < amount){
			return new ResponseEntity<>("The amount can't be more than the current balance",HttpStatus.FORBIDDEN);
		}

		Transaction transactionDebit = new Transaction(DEBIT, amount * -1, LocalDateTime.now(),description + " " + "transferred to" + " " + numberDestiny,originAccount,(originAccount.getBalance()) - amount);
		transactionService.saveTransaction(transactionDebit);
		Transaction transactionCredit = new Transaction(CREDIT, amount,LocalDateTime.now(),description + " " + "received from" + " " + ownNumberAccount,destinyAccount, (destinyAccount.getBalance()) + amount);
		transactionService.saveTransaction(transactionCredit);

		originAccount.setBalance(originAccount.getBalance() - amount);
		accountService.saveAccount(originAccount);
		destinyAccount.setBalance(destinyAccount.getBalance() + amount);
		accountService.saveAccount(destinyAccount);

		return new ResponseEntity<>("Transaction successful", HttpStatus.CREATED);

	}


}
