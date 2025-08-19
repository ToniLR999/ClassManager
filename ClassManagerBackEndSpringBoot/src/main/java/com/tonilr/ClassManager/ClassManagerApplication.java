package com.tonilr.ClassManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ClassManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClassManagerApplication.class, args);
	}

}
