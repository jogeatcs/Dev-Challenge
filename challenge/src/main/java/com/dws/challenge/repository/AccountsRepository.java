package com.dws.challenge.repository;

import com.dws.challenge.domain.AccountDto;
import com.dws.challenge.exception.DuplicateAccountIdException;

public interface AccountsRepository {

  void createAccount(AccountDto account) throws DuplicateAccountIdException;

  AccountDto getAccount(String accountId);

  void clearAccounts();
}
