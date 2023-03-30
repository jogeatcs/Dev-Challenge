package com.dws.challenge.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class is an integration class for rest services
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AmountDto {
    private double amount;

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
}