package com.hfcommunity.hf_community_hub;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HfCommunityHubApplication {

	public static void main(String[] args) {
		SpringApplication.run(HfCommunityHubApplication.class, args);
	}

	@PostConstruct
	public void printPort() {
		System.out.println(">>> Listening on port: " + System.getProperty("server.port"));
	}

}
