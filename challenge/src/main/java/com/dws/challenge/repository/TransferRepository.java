package com.dws.challenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dws.challenge.entity.Account;

public interface TransferRepository extends JpaRepository<Account, Long>{

	Account findByAccountId(String fromBankAccountId);

}
