package com.starter;

import com.starter.entity.Employee;
import com.starter.entity.Product;
import com.starter.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@SpringBootApplication
public class StarterApplication implements CommandLineRunner {

	@Autowired
	private EmployeeRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(StarterApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Employee empOne = new Employee();
		empOne.setName("EMP-ONE");
		empOne.setDesignation("programmer");

		empOne.getProducts().addAll(Arrays.asList(
				new Product(null, "Keyboard", 54884),
				new Product(null, "Mouse", 54884),
				new Product(null, "Laptop", 54884)

		));

		Employee empTwo = new Employee();
		empTwo.setName("EMP-TWO");
		empTwo.setDesignation("developer");

		empTwo.getProducts().addAll(Arrays.asList(
				new Product(null, "Mobile", 54884),
				new Product(null, "Headphone", 54884)

		));

		repository.saveAll(Arrays.asList(empOne, empTwo));
	}

	@Bean
	public WebMvcConfigurer configure() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("*");
			}
		};
	}
}
