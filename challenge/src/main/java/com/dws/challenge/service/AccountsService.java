package com.dws.challenge.service;

import com.dws.challenge.domain.AccountDto;
import com.dws.challenge.repository.AccountsRepository;
import lombok.Getter;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountsService {

  private final AccountsRepository accountsRepository;

  @Autowired
  public AccountsService(AccountsRepository accountsRepository) {
    this.accountsRepository = accountsRepository;
  }

  public void createAccount(AccountDto account) {
    this.accountsRepository.createAccount(account);
  }

  public AccountDto getAccount(String accountId) {
    return this.accountsRepository.getAccount(accountId);
  }

public AccountsRepository getAccountsRepository() {
	return accountsRepository;
}
  

}
