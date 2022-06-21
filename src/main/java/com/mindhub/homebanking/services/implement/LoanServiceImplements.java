package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dtos.ClientLoanDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.repositories.ClientLoanRepository;
import com.mindhub.homebanking.repositories.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import com.mindhub.homebanking.services.LoanService;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class LoanServiceImplements implements LoanService {

	@Autowired
	private ClientLoanRepository clientLoanRepository;

	@Autowired
	private LoanRepository loanRepository;

	@Override
	public List<ClientLoanDTO> getClientLoans(){
		return clientLoanRepository.findAll().stream().map(ClientLoanDTO::new).collect(toList());
	}

	@Override
	public ClientLoanDTO getClientLoan(@PathVariable Long id){
		return clientLoanRepository.findById(id).map(ClientLoanDTO::new).orElse(null);
	}

	@Override
	public List <LoanDTO> getLoans(){
		return loanRepository.findAll().stream().map(LoanDTO::new).collect(toList());
	}

	@Override
	public Loan findById(long id){
		return loanRepository.findById(id);
	}

	@Override
	public void saveClientLoan(ClientLoan clientLoan){
		clientLoanRepository.save(clientLoan);
	}

	@Override
	public void saveLoan(Loan loan){
		loanRepository.save(loan);
	}

}
