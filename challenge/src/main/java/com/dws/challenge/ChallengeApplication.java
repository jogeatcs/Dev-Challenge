package com.dws.challenge;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class ChallengeApplication {

	public static void main(String[] args) {
		//SpringApplication.run(ChallengeApplication.class, args);
		 new SpringApplicationBuilder().sources(ChallengeApplication.class).run(args);
	}

}
