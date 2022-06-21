package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import com.mindhub.homebanking.services.CardService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardServiceImplements implements CardService {

	@Autowired
	CardRepository cardRepository;

	@Override
	public List<CardDTO> getCards(){
		return cardRepository.findAll().stream().map(CardDTO::new).collect(Collectors.toList());
	}

	@Override
	public CardDTO getCard(@PathVariable Long id){
		return cardRepository.findById(id).map(CardDTO::new).orElse(null);
	}

	@Override
	public void saveCard(Card card){
		cardRepository.save(card);
	}

	@Override
	public Card getCardByNumber(String number){
		return cardRepository.findByNumber(number);
	}

}
