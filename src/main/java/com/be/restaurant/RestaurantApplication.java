package com.be.restaurant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.tools.agent.ReactorDebugAgent;

@SpringBootApplication
public class RestaurantApplication {

	public static void main(String[] args) {
		ReactorDebugAgent.init();
		SpringApplication.run(RestaurantApplication.class, args);
	}

}
