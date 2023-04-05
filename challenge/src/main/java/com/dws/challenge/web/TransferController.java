package com.dws.challenge.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dws.challenge.domain.AccountDto;
import com.dws.challenge.domain.AmountDto;
import com.dws.challenge.service.TransferService;

import lombok.extern.slf4j.Slf4j;

/**
 * This class is created to manage transfer process
 */
@Slf4j
@RestController
@RequestMapping("/v1/transfer")
public class TransferController {

	@Autowired
	private TransferService transferService;

	/**
	 * @param fromBankAccountId
	 * @param toBankAccountId
	 * @param amountDto
	 * @return
	 */
	@PostMapping(value = "{fromBankAccountId}/{toBankAccountId}")
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<Object> transfer(@PathVariable(name = "fromBankAccountId") String fromBankAccountId,
			@PathVariable(name = "toBankAccountId") String toBankAccountId, @RequestBody @Valid AmountDto amountDto) {
		try {
			transferService.transfer(fromBankAccountId, toBankAccountId, amountDto.getAmount());
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	/**
	 * @param accountId
	 * @return
	 */
	@GetMapping(path = "/{accountId}")
	public AccountDto getAccount(@PathVariable String accountId) {
		return this.transferService.getAccount(accountId);
	}

}
