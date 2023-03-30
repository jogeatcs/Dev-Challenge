package com.dws.challenge.domain;

import com.dws.challenge.exception.InsufficientBalanceException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class Account {

	@NotNull
	@NotEmpty
	private final String accountId;

	@NotNull
	@Min(value = 0, message = "Initial balance must be positive.")
	private double balance;

	public Account(String accountId) {
		this.accountId = accountId;
		this.balance = 0;
	}

	@JsonCreator
	public Account(@JsonProperty("accountId") String accountId, @JsonProperty("balance") double balance) {
		this.accountId = accountId;
		this.balance = balance;
	}

	/**
	 * @param amount
	 */
	private static void checkAmountNonNegative(double amount) {
		if (amount < 0) {
			throw new IllegalArgumentException("negative amount");
		}
	}

	/**
	 * @param amount
	 */
	public synchronized void deposit(double amount) {
		checkAmountNonNegative(amount);
		balance += amount;
	}

	/**
	 * @param amount
	 */
	public synchronized void withdraw(double amount) {
		checkAmountNonNegative(amount);
		if (balance < amount) {
			throw new InsufficientBalanceException("Not enough money");
		}
		balance -= amount;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getAccountId() {
		return accountId;
	}

}
