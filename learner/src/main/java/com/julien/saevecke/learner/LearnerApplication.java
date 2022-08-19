package com.julien.saevecke.learner;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class LearnerApplication {
	public static void main(String[] args) {
		SpringApplication.run(LearnerApplication.class, args);
	}
}
