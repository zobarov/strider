package com.gav.quiz.strider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author alex.gera
 */
@SpringBootApplication
@ComponentScan
public class StriderApplication {

	public static void main(String[] args) {
		SpringApplication.run(StriderApplication.class, args);
	}
}
