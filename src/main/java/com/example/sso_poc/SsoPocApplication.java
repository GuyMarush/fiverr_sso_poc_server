package com.example.sso_poc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class SsoPocApplication {

	public static void main(String[] args) {
		SpringApplication.run(SsoPocApplication.class, args);
	}

}
