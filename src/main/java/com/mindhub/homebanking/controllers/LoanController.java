package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientLoanDTO;
import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.LoanService;
import com.mindhub.homebanking.services.TransactionService;
import java.time.LocalDateTime;
import java.util.List;

import static com.mindhub.homebanking.models.TransactionType.CREDIT;


@RestController
@RequestMapping("/api")
public class LoanController {

	@Autowired
	private LoanService loanService;

	@Autowired
	private ClientService clientService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private TransactionService transactionService;

	@GetMapping("/client_loans")
	public List<ClientLoanDTO> getClientLoans(){
		return loanService.getClientLoans();
	}

	@GetMapping("/client_loans/{id}")
	public ClientLoanDTO getClientLoan(@PathVariable Long id){
		return loanService.getClientLoan(id);
	}

	@GetMapping("/loans")
	public List <LoanDTO> getLoans(){
		return loanService.getLoans();
	}

	@Transactional
	@PostMapping(path="/loans")
	public ResponseEntity <Object> newLoan(@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication){
		Client currentClient = clientService.getCurrentClient(authentication);
		Account currentAccount = accountService.findByNumber(loanApplicationDTO.getAccountDestination());
		Loan loan = loanService.findById(loanApplicationDTO.getLoanId());


		if (currentClient.getLoans().contains(loan)){
			return new ResponseEntity<>("You can't have another loan of the same type",HttpStatus.FORBIDDEN);
		}

		if (loanApplicationDTO.getAmount() == 0){
			return new ResponseEntity<>("Amount can't be 0", HttpStatus.FORBIDDEN);
		}

		if (loanApplicationDTO.getPayments() == 0){
			return new ResponseEntity<>("Payments can't be 0", HttpStatus.FORBIDDEN);
		}

		if (loanApplicationDTO.getAccountDestination().isEmpty()){
			return new ResponseEntity<>("Account destination is empty", HttpStatus.FORBIDDEN);
		}

		if (loanApplicationDTO.getLoanId() < 0){
			return new ResponseEntity<>("Missing loan", HttpStatus.FORBIDDEN);
		}

		if (loan == null){
			return new ResponseEntity<>("Loan doesn't exist", HttpStatus.FORBIDDEN);
		}

		if (loanApplicationDTO.getAmount() > loan.getMaxAmount()) {
			return new ResponseEntity<>("Loan can't pass the max amount", HttpStatus.FORBIDDEN);
		}

		if(!loan.getPayments().contains(loanApplicationDTO.getPayments())){
			return new ResponseEntity<>("Number of payments not available", HttpStatus.FORBIDDEN);
		}

		if (currentAccount == null){
			return new ResponseEntity<>("Account doesn't exist",HttpStatus.FORBIDDEN);
		}

		if (!currentClient.getAccounts().contains(currentAccount)){
			return new ResponseEntity<>("Not your account", HttpStatus.FORBIDDEN);
		}

		ClientLoan newClientLoan = new ClientLoan(loanApplicationDTO.getAmount()  * loan.getInterestRate(), loanApplicationDTO.getPayments(), currentClient,loan);
		loanService.saveClientLoan(newClientLoan);
		Transaction loanTransaction = new Transaction(CREDIT, loanApplicationDTO.getAmount(), LocalDateTime.now(), "loan approved", currentAccount, (currentAccount.getBalance() + loanApplicationDTO.getAmount()));
		transactionService.saveTransaction(loanTransaction);

		currentAccount.setBalance(currentAccount.getBalance() + loanTransaction.getAmount());
		accountService.saveAccount(currentAccount);

		return new ResponseEntity<>("Loan created",HttpStatus.CREATED);
	}

	@Transactional
	@PostMapping("/createloan")
	public ResponseEntity<Object> newLoan(@RequestParam String name, @RequestParam double maxAmount, @RequestParam List<Integer> payments, @RequestParam double interestRate){

		if(name == null || maxAmount <= 0 || payments == null || interestRate <= 0) {
			return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
		}

		Loan newLoan = new Loan (name, maxAmount, payments, interestRate);
		loanService.saveLoan(newLoan);

		return new ResponseEntity<>("Loan successfully created", HttpStatus.CREATED);
	}

}