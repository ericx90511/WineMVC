package com.wine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class WinemvcApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(WinemvcApplication.class, args);
	}
}
