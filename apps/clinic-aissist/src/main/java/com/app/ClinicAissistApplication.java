package com.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.app.client")
public class ClinicAissistApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClinicAissistApplication.class, args);
	}

}
