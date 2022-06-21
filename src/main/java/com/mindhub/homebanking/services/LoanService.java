package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.ClientLoanDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.Loan;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface LoanService {

	List<ClientLoanDTO> getClientLoans();
	ClientLoanDTO getClientLoan(@PathVariable Long id);
	List <LoanDTO> getLoans();
	Loan findById(long id);
	void saveClientLoan(ClientLoan clientLoan);
	void saveLoan(Loan loan);
}
