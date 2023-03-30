package com.dws.challenge.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.dws.challenge.domain.Account;
import com.dws.challenge.exception.DuplicateAccountIdException;
import com.dws.challenge.exception.InsufficientBalanceException;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class TransferServiceTest {
	

	@Autowired
	private AccountsService accountsService;
	
	@Autowired
	private TransferService transferService;

	@Test
	void transfer() {
		Account account1 = new Account("Id-1211");
		account1.setBalance(1000);
		this.accountsService.createAccount(account1);
		Account account2 = new Account("Id-1212");
		account2.setBalance(1000);
		this.accountsService.createAccount(account2);
		
		this.transferService.transfer("Id-1211", "Id-1212", 100);

		assertThat(this.accountsService.getAccount("Id-1211").getBalance()).isEqualTo(900);
	}

	@Test
	void transfer_failsOnNegativeAmount() {
		Account account1 = new Account("Id-1213");
		account1.setBalance(1000);
		this.accountsService.createAccount(account1);
		Account account2 = new Account("Id-1214");
		account2.setBalance(1000);
		this.accountsService.createAccount(account2);
		try {
			this.transferService.transfer("Id-1213", "Id-1214", -100);
			fail("Transfer amount should be positive");
		} catch (IllegalArgumentException ex) {
			assertThat(ex.getMessage()).isEqualTo("negative amount");
		}
	}
	
	@Test
	void transfer_failsOnInsufficientAmount() {
		Account account1 = new Account("Id-1215");
		account1.setBalance(1000);
		this.accountsService.createAccount(account1);
		Account account2 = new Account("Id-1216");
		account2.setBalance(1000);
		this.accountsService.createAccount(account2);
		try {
			this.transferService.transfer("Id-1215", "Id-1216", 2100);
			fail("Insufficient amount");
		} catch (InsufficientBalanceException ex) {
			assertThat(ex.getMessage()).isEqualTo("Not enough money");
		}
	}

}
