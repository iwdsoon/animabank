package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import static java.util.stream.Collectors.toList;

@Service
public class ClientServiceImplements implements ClientService {

	@Autowired
	ClientRepository clientRepository;

	@Override
	public List<ClientDTO> getClients(){
		return clientRepository.findAll().stream().map(ClientDTO::new).collect(toList());
	}

	@Override
	public ClientDTO getClient(Long id) {
		return clientRepository.findById(id).map(ClientDTO::new).orElse(null);
	}

	@Override
	public Client getCurrentClient(Authentication authentication){
		return clientRepository.findByEmail(authentication.getName());
	}

	@Override
	public void saveClient(Client client){
		clientRepository.save(client);
	}

	@Override
	public Client getClientByEmail(String email){
		return clientRepository.findByEmail(email);
	}

}
