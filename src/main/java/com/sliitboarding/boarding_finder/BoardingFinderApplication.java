package com.sliitboarding.boarding_finder;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BoardingFinderApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardingFinderApplication.class, args);
	}

	@Bean
	public CommandLineRunner printSiteLink() {
		return args -> {
			System.out.println();
			System.out.println("SLIIT Boarding Finder : http://localhost:8080");
		};
	}
}