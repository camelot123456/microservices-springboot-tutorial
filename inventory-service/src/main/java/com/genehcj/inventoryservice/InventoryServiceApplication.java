package com.genehcj.inventoryservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import com.genehcj.inventoryservice.model.Inventory;
import com.genehcj.inventoryservice.repository.InventoryRepository;

@SpringBootApplication
@EnableEurekaClient
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner lineRunner(InventoryRepository inventoryRepository) {
		return args -> {
			Inventory inventory = new Inventory();
			inventory.setSkuCode("iphone_13");
			inventory.setQuantity(100);
			
			Inventory inventory2 = new Inventory();
			inventory2.setSkuCode("iphone_13_red");
			inventory2.setQuantity(100);
			
			inventoryRepository.save(inventory);
			inventoryRepository.save(inventory2);
		};
	}
	
}
