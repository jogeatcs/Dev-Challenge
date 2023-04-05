package com.dws.challenge.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
		this.transferService.transfer("Id-1211", "Id-1212", new BigDecimal(100));

		assertThat(this.transferService.getAccount("Id-1211").getBalance().intValue()).isEqualTo(900);
	}

	@Test
	void transfer_failsOnNegativeAmount() {
		try {
			this.transferService.transfer("Id-1213", "Id-1214", new BigDecimal(-100));
			fail("Transfer amount should be positive");
		} catch (IllegalArgumentException ex) {
			assertThat(ex.getMessage()).isEqualTo("Transfer amount is negative : -100");
		}
	}
	
	@Test
	void transfer_failsOnInsufficientAmount() {
		try {
			this.transferService.transfer("Id-1215", "Id-1216", new BigDecimal(2100));
			fail("Insufficient amount");
		} catch (InsufficientBalanceException ex) {
			assertThat(ex.getMessage()).isEqualTo("Account with id:Id-1215 does not have enough balance to transfer.");
		}
	}

}
