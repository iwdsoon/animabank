package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import static com.mindhub.homebanking.models.CardColor.*;
import static com.mindhub.homebanking.models.TransactionType.*;

@SpringBootApplication
public class HomebankingMindhubApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingMindhubApplication.class, args);
	}

	@Autowired
	PasswordEncoder passwordEncoder;

	@Bean
	public CommandLineRunner initData(ClientService clientService, AccountService accountService, TransactionService transactionService, LoanService loanService, CardService cardService, PasswordEncoder passwordEncoder) {
		return (args) -> {

			Client admin = new Client("admin", "admin", "admin@admin.com", passwordEncoder.encode("admin"));
			clientService.saveClient(admin);

			Client melba = new Client ("Melba", "Morel", "melba@mindhub.com",passwordEncoder.encode("melba123"));
			clientService.saveClient(melba);

			Account account1 = new Account ("VIN001", LocalDateTime.now(), 5000,melba,AccountType.CURRENT);
			Account account2 = new Account ("VIN002", LocalDateTime.now().plusDays(1), 7500, melba,AccountType.SAVING);
			accountService.saveAccount(account1);
			accountService.saveAccount(account2);

			Transaction transaction1 = new Transaction (CREDIT, 570, LocalDateTime.now(), "intereses ganados",account1);
			Transaction transaction2 = new Transaction (CREDIT, 690, LocalDateTime.now(), "devolucion",account1);
			Transaction transaction3 = new Transaction (DEBIT, 860, LocalDateTime.now(), "pago luz",account1);
			Transaction transaction4 = new Transaction (CREDIT, 450, LocalDateTime.now(), "tutoria ingles",account2);
			Transaction transaction5 = new Transaction (DEBIT, 1760, LocalDateTime.now(), "pago supermercado",account2);
			Transaction transaction6 = new Transaction (DEBIT, 800, LocalDateTime.now(), "pago verduleria",account2);
			Transaction transaction7 = new Transaction (CREDIT, 1005, LocalDateTime.now(), "deposito",account2);
			transactionService.saveTransaction(transaction1);
			transactionService.saveTransaction(transaction2);
			transactionService.saveTransaction(transaction3);
			transactionService.saveTransaction(transaction4);
			transactionService.saveTransaction(transaction5);
			transactionService.saveTransaction(transaction6);
			transactionService.saveTransaction(transaction7);

			Loan Mortgage = new Loan("Mortgage", 500000d, List.of(12,24,36,48,60), 1.45);
			Loan Personal = new Loan ("Personal", 100000d, List.of(6,12,24), 1.30);
			Loan Auto = new Loan ("Auto", 300000d, List.of(6,12,24,36), 1.35);
			loanService.saveLoan(Mortgage);
			loanService.saveLoan(Personal);
			loanService.saveLoan(Auto);

			ClientLoan loan1 = new ClientLoan(400000, 60, melba,Mortgage);
			ClientLoan loan2 = new ClientLoan(50000, 12, melba,Personal);
			loanService.saveClientLoan(loan1);
			loanService.saveClientLoan(loan2);

			Card card1 = new Card (CardType.DEBIT, GOLD,"2454-1236-1235-1234",(short)123,LocalDateTime.now(),LocalDateTime.now().plusYears(5),melba);
			Card card2 = new Card (CardType.CREDIT, TITANIUM,"2345-6542-2364-1235",(short) 321,LocalDateTime.now(),LocalDateTime.now().plusYears(5),melba);
			cardService.saveCard(card1);
			cardService.saveCard(card2);

			//
			Client paulo = new Client ("Paulo", "Londra", "paulo@session23.com",passwordEncoder.encode("leonesconflow"));
			clientService.saveClient (paulo);

			Account account3 = new Account ("VIN003", LocalDateTime.now().minusDays(1), 105000, paulo, AccountType.CURRENT);
			accountService.saveAccount(account3);

			Transaction transaction8 = new Transaction (CREDIT, 250000, LocalDateTime.now(), "1er deposito big ligas",account3);
			Transaction transaction9 = new Transaction (DEBIT, 45000, LocalDateTime.now(), "pago abogado",account3);
			transactionService.saveTransaction(transaction8);
			transactionService.saveTransaction(transaction9);

			ClientLoan loan3 = new ClientLoan(100000, 24, paulo,Personal);
			ClientLoan loan4 = new ClientLoan(200000, 36, paulo,Auto);
			loanService.saveClientLoan(loan3);
			loanService.saveClientLoan(loan4);

			Card card3 = new Card (CardType.CREDIT, SILVER,"2346-8795-1036-1564",(short) 666,LocalDateTime.now(),LocalDateTime.now().plusYears(4),paulo);
			cardService.saveCard(card3);

		};
	}

}
