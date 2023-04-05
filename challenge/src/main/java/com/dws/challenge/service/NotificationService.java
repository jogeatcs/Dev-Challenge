package com.dws.challenge.service;

import com.dws.challenge.domain.AccountDto;

public interface NotificationService {

  void notifyAboutTransfer(AccountDto account, String transferDescription);
}
