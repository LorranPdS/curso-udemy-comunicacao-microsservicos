package br.com.curso_udemy.product_api;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

// A anotação @EnableRabbit é feita para habilitar o Rabbit na nossa aplicação
@EnableRabbit
@EnableFeignClients
@SpringBootApplication
public class ProductApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductApiApplication.class, args);
	}

}
