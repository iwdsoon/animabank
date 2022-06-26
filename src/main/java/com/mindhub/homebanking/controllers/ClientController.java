package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;
import com.mindhub.homebanking.models.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import java.time.LocalDateTime;
import java.util.List;

import static com.mindhub.homebanking.utilities.Utility.getRandomNumber;

@RestController
@RequestMapping("/api")
public class ClientController {

	@Autowired
	private ClientService clientService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private PasswordEncoder passwordEncoder;


	@GetMapping("/clients")
	public List<ClientDTO> getClients(){
		return clientService.getClients();
	}

	@GetMapping("/clients/{id}")
	public ClientDTO getClient(@PathVariable Long id){
		return clientService.getClient(id);
	}

	@PostMapping(path = "/clients")
	public ResponseEntity<Object> register(
			@RequestParam String firstName,
			@RequestParam String lastName,
			@RequestParam String email,
			@RequestParam String password) {

		if (firstName.isEmpty()) {
			return new ResponseEntity<>("Missing first name", HttpStatus.FORBIDDEN);
		}

		if (lastName.isEmpty()) {
			return new ResponseEntity<>("Missing last name", HttpStatus.FORBIDDEN);
		}

		if (email.isEmpty()) {
			return new ResponseEntity<>("Missing email", HttpStatus.FORBIDDEN);
		}
		if (!email.contains("@") || !email.contains(".")) {
			return new ResponseEntity<>("You need a valid email", HttpStatus.FORBIDDEN);
		}

		if (password.isEmpty()) {
			return new ResponseEntity<>("Missing password", HttpStatus.FORBIDDEN);
		}
		if (password.length() < 5){
			return new ResponseEntity<>("Your password is too short", HttpStatus.FORBIDDEN);
		}


		if (clientService.getClientByEmail(email) !=  null) {  //si es distinto de null es porque existe
			return new ResponseEntity<>("Email already in use", HttpStatus.FORBIDDEN);
		}

		Client newClient = new Client(firstName, lastName, email, passwordEncoder.encode(password));
		clientService.saveClient(newClient);
		Account newAccount = new Account("VIN-" + getRandomNumber(1, 99999999), LocalDateTime.now(), 0, newClient, AccountType.CURRENT);
		accountService.saveAccount(newAccount);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@GetMapping("/clients/current")
	public ClientDTO getCurrentClient(Authentication authentication){
		Client currentClient = clientService.getCurrentClient(authentication);
		return new ClientDTO(currentClient);
	}

}
