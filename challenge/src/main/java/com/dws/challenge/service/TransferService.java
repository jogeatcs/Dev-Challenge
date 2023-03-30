package com.dws.challenge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dws.challenge.domain.Account;
import com.dws.challenge.exception.DuplicateAccountIdException;
import com.dws.challenge.exception.InsufficientBalanceException;

import lombok.extern.slf4j.Slf4j;

/**
 * Transfer process management service
 */
@Slf4j
@Service
public class TransferService {

	@Autowired
	private AccountsService accountsService;

	@Autowired
	private NotificationService notificationService;

	/**
	 * @param fromBankAccountId
	 * @param toBankAccountId
	 * @param amount
	 */
	public void transfer(String fromBankAccountId, String toBankAccountId, double amount) {
		Account fromBankAccount = accountsService.getAccount(fromBankAccountId);
		Account toBankAccount = accountsService.getAccount(toBankAccountId);
		fromBankAccount.withdraw(amount);
		toBankAccount.deposit(amount);
		notificationService.notifyAboutTransfer(toBankAccount,
				"Amount :" + amount + " transferd successfully to : " + toBankAccountId);
		notificationService.notifyAboutTransfer(fromBankAccount,
				"Amount :" + amount + " transferd successfully to : " + toBankAccountId);

	}

}
