package com.dws.challenge.service;

import java.math.BigDecimal;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dws.challenge.domain.AccountDto;
import com.dws.challenge.entity.Account;
import com.dws.challenge.exception.AccountNotExistException;
import com.dws.challenge.exception.InsufficientBalanceException;
import com.dws.challenge.repository.TransferRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Transfer process management service
 */
@Slf4j
@Service
public class TransferService {

	@Autowired
	TransferRepository transferRepository;

	@Autowired
	private NotificationService notificationService;

	/**
	 * @param fromBankAccountId
	 * @param toBankAccountId
	 * @param amount
	 */
	@Transactional
	public void transfer(String fromBankAccountId, String toBankAccountId, BigDecimal amount) {
		if (amount.signum() < 0) {
			throw new IllegalArgumentException("Transfer amount is negative : " + amount);
		}
		Account fromBankAccount = transferRepository.findByAccountId(fromBankAccountId);
		if (fromBankAccount == null) {
			throw new AccountNotExistException("Account with id:" + fromBankAccountId + " does not exist.");
		}
		Account toBankAccount = transferRepository.findByAccountId(toBankAccountId);

		if (toBankAccount == null) {
			throw new AccountNotExistException("Account with id:" + toBankAccountId + " does not exist.");
		}
		if (fromBankAccount.getBalance().compareTo(amount) < 0) {
			throw new InsufficientBalanceException(
					"Account with id:" + fromBankAccount.getAccountId() + " does not have enough balance to transfer.");
		}
		synchronized (this) {
			fromBankAccount.setBalance(fromBankAccount.getBalance().subtract(amount));
			toBankAccount.setBalance(toBankAccount.getBalance().add(amount));
			transferRepository.save(fromBankAccount);
			transferRepository.save(toBankAccount);
		}

		notificationService.notifyAboutTransfer(new com.dws.challenge.domain.AccountDto(toBankAccountId),
				"Amount :" + amount + " transferd successfully to : " + toBankAccountId);
		notificationService.notifyAboutTransfer(new com.dws.challenge.domain.AccountDto(fromBankAccountId),
				"Amount :" + amount + " transferd successfully to : " + toBankAccountId);

	}

	/**
	 * @param accountId
	 * @return
	 */
	public AccountDto getAccount(String accountId) {
		Account accountEntity = transferRepository.findByAccountId(accountId);
		return new AccountDto(accountEntity.getAccountId(), accountEntity.getBalance());
	}

}
