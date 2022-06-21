package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import com.mindhub.homebanking.services.AccountService;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class AccountServiceImplements implements AccountService {

	@Autowired
	AccountRepository accountRepository;

	@Override
	public List<AccountDTO> getAccounts(){
		return accountRepository.findAll().stream().map(AccountDTO::new).collect(toList());
	}

	@Override
	public AccountDTO getAccount(@PathVariable Long id){
		return accountRepository.findById(id).map(AccountDTO::new).orElse(null);
	}

	@Override
	public void saveAccount (Account account){
		accountRepository.save(account);
	}

	@Override
	public Account findByNumber(String number){
		return accountRepository.findByNumber(number);
	}

	@Override
	public Account findById(long id){
		return accountRepository.findById(id);
	}

}
