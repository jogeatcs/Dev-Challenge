package com.dws.challenge.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.dws.challenge.domain.AccountDto;
import com.dws.challenge.service.AccountsService;
import com.dws.challenge.service.NotificationService;
import com.dws.challenge.service.TransferService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@WebAppConfiguration
class TransferControllerTest {

	private MockMvc mockMvc;

	  @Autowired
	  private AccountsService accountsService;
	  
	  @Autowired
	  private WebApplicationContext webApplicationContext;

	  @BeforeEach
	  void prepareMockMvc() {
	    this.mockMvc = webAppContextSetup(this.webApplicationContext).build();

	    // Reset the existing accounts before each test.
	    accountsService.getAccountsRepository().clearAccounts();
	  }

	 
	  @Test
	  void transfer() throws Exception {
		  this.mockMvc.perform(post("/v1/accounts").contentType(MediaType.APPLICATION_JSON)
			      .content("{\"accountId\":\"ABC001\",\"balance\":1000}"));
		  this.mockMvc.perform(post("/v1/accounts").contentType(MediaType.APPLICATION_JSON)
			      .content("{\"accountId\":\"ABC002\",\"balance\":100}"));
		  this.mockMvc.perform(post("/v1/transfer/ABC002/ABC001").contentType(MediaType.APPLICATION_JSON)
		  	      .content("{\"amount\":100}")).andExpect(status().isCreated());
	  }
	  
	  @Test
	  void transferNegativeAmount() throws Exception {
		  this.mockMvc.perform(post("/v1/accounts").contentType(MediaType.APPLICATION_JSON)
			      .content("{\"accountId\":\"ABC001\",\"balance\":1000}"));
		  this.mockMvc.perform(post("/v1/accounts").contentType(MediaType.APPLICATION_JSON)
			      .content("{\"accountId\":\"ABC002\",\"balance\":100}"));
		  this.mockMvc.perform(post("/v1/transfer/ABC002/ABC001").contentType(MediaType.APPLICATION_JSON)
		  	      .content("{\"amount\":-100}")).andExpect(status().isBadRequest());
	  }
	  
	  
	

}
