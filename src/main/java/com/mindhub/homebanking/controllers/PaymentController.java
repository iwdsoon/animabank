package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.PaymentDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class PaymentController {

	@Autowired
	ClientService clientService;

	@Autowired
	AccountService accountService;

	@Autowired
	CardService cardService;

//	@Transactional
//	@PostMapping(path = "/payments")
//	public ResponseEntity<Object> newPayment (Authentication authentication, @RequestBody PaymentDTO paymentDTO){
//
//		Client currentClient = clientService.getCurrentClient(authentication);
//		List<Account> accounts = new ArrayList<>(currentClient.getAccounts());
//		Account account = accounts.stream().filter(account1 -> account1.getBalance() >= paymentDTO.getAmount() && account1.isActive()).findFirst().orElse(null);
//		Card card = currentClient.getCards().stream().filter(card1 -> card1.isActive() && card1.getNumber() == paymentDTO.getNumber()).findFirst().orElse(null);
//
//		if (paymentDTO.getNumber().isEmpty() || paymentDTO.getName().isEmpty() || paymentDTO.getDescription().isEmpty() || paymentDTO.getThruDate() == null){
//			return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
//		}
//
//		if (paymentDTO.getAmount() <= 0 ){
//			return new ResponseEntity<>("Wrong amount", HttpStatus.FORBIDDEN);
//		}
//
//		if (card.getCvv() != paymentDTO.getCvv() || paymentDTO.getCvv() <= 0){
//			return new ResponseEntity<>("Wrong cvv", HttpStatus.FORBIDDEN);
//		}
//
//		if (card.getThruDate().isAfter(LocalDateTime.now())){
//			return new ResponseEntity<>("Expired card", HttpStatus.FORBIDDEN);
//		}
//
//
//	}

}
