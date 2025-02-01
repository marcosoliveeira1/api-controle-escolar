package com.platformbuilders.controleescolar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class ControleEscolarApplication {

	public static void main(String[] args) {
		SpringApplication.run(ControleEscolarApplication.class, args);
	}

}
