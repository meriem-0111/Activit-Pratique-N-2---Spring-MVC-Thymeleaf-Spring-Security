package com.example.bdcc_enset_spring_mvc;

import com.example.bdcc_enset_spring_mvc.entities.Product;
import com.example.bdcc_enset_spring_mvc.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class BdccEnsetSpringMvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(BdccEnsetSpringMvcApplication.class, args);

	}

@Bean
public CommandLineRunner start(ProductRepository productRepository){
	return args -> {
		productRepository.save(Product.builder()
				.name("Computer")
				.price(6000)
				.quantity(21)
				.build());
		productRepository.save(Product.builder()
				.name("Telephone")
				.price(8000)
				.quantity(20)
				.build());
		productRepository.save(Product.builder()
				.name("Printer")
				.price(18000)
				.quantity(14)
				.build());
		productRepository.findAll().forEach(p->{
			System.out.println(p.toString());
		});
	};

}
}




