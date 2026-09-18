package com.enigmacamp.chall_koperasikita;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ChallKoperasikitaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChallKoperasikitaApplication.class, args);
	}

}
