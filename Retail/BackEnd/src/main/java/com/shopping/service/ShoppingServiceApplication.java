package com.shopping.service;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, scheme="bearer")
public class ShoppingServiceApplication {

//	@GetMapping("/test")
//	public String test(){
//		return "Testing docker!!!";
//	}

	public static void main(String[] args) {
		SpringApplication.run(ShoppingServiceApplication.class, args);
	}

}
