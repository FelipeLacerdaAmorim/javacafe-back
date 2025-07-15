package com.cafeteria.java_cafe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.cafeteria.java_cafe.repository")
public class JavaCafeApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaCafeApplication.class, args);
	}

}
