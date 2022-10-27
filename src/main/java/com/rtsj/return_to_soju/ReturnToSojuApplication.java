package com.rtsj.return_to_soju;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing //jpa Auditing Enable
@EnableScheduling
public class ReturnToSojuApplication {
//	static {
//		System.setProperty("com.amazonaws.sdk.disableEc2Metadata", "true");
//	}

	public static void main(String[] args) {
		SpringApplication.run(ReturnToSojuApplication.class, args);
	}

}