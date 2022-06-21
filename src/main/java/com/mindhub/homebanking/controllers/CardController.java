package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.services.ClientService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.mindhub.homebanking.utilities.Utility.getRandomNumber;

@RestController
@RequestMapping("/api")
public class CardController {

	@Autowired
	private CardService cardService;

	@Autowired
	private ClientService clientService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/cards")
	public List<CardDTO> getCards(){
		return cardService.getCards();
	}

	@GetMapping("/cards/{id}")
	public CardDTO getCard(@PathVariable Long id){
		return cardService.getCard(id);
	}


	@PostMapping(path="/clients/current/cards")
	public ResponseEntity<Object> createNewCard(Authentication authentication, @RequestParam CardColor cardColor, @RequestParam CardType cardType){
		Client currentClient = clientService.getCurrentClient(authentication);
		Set <Card> cardsType = currentClient.getCards().stream().filter(card -> card.getType() == cardType && card.isActive()).collect(Collectors.toSet());
		Set <Card> cardsColor = cardsType.stream().filter(card -> card.getColor() == cardColor && card.isActive()).collect(Collectors.toSet());

		if (cardsColor.size() >= 1){
			return new ResponseEntity<>("You can't have more cards of the same color", HttpStatus.FORBIDDEN);
		}

		if (cardsType.size() >= 3){
			return new ResponseEntity<>("You can't have more cards of the same type", HttpStatus.FORBIDDEN);
		}

		Card newCard = new Card(cardType, cardColor, getCardNumber(), (short) getRandomNumber(100,999),LocalDateTime.now(),LocalDateTime.now().plusYears(5),currentClient);
		cardService.saveCard(newCard);

		return new ResponseEntity<>("Card successfully created", HttpStatus.CREATED);

	}

	public static String getCardNumber() {
		return getRandomNumber(1000, 9999) + "-" + getRandomNumber(1000, 9999) + "-" + getRandomNumber(1000, 9999) + "-" + getRandomNumber(1000, 9999);
	}

	@GetMapping("/clients/current/cards")
	public List<CardDTO> getCurrentCards(Authentication authentication){
		Client currentClient = clientService.getCurrentClient(authentication);
		return currentClient.getCards().stream().map(CardDTO::new).collect(Collectors.toList());
	}


	@Transactional
	@PatchMapping(path="/clients/current/cards")
	public ResponseEntity <Object> deleteCard(Authentication authentication, @RequestParam long id, @RequestParam String password){
		Client currentClient = clientService.getCurrentClient(authentication);
		Card currentCard = currentClient.getCards().stream().filter(card -> card.getId() == id && card.isActive()).findFirst().orElse(null);

		if (!passwordEncoder.matches(password,currentClient.getPassword())) {
			return new ResponseEntity<>("wrong pass",HttpStatus.FORBIDDEN);
		}

		if (!currentClient.getCards().contains(currentCard)){
			return new ResponseEntity<>("not your card", HttpStatus.FORBIDDEN);
		}

		currentCard.setActive(false);
		cardService.saveCard(currentCard);
				return new ResponseEntity<>("card disabled",HttpStatus.CREATED);
	}



}
