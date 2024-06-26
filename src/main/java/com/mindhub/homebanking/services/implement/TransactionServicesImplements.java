package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServicesImplements implements TransactionService {

	@Autowired
	TransactionRepository transactionRepository;

	@Override
	public void saveTransaction(Transaction transaction) {
		transactionRepository.save(transaction);
	}


}
