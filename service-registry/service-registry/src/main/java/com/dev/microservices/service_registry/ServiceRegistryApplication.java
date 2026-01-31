package com.dev.microservices.service_registry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ServiceRegistryApplication {

	public static void main(String[] args) {
		System.out.println("starting the service registry application...");
		SpringApplication.run(ServiceRegistryApplication.class, args);
	}

}
