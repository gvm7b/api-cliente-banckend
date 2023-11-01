package com.br.senac.apiclientebackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ApiClienteBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiClienteBackendApplication.class, args);
	}

}
