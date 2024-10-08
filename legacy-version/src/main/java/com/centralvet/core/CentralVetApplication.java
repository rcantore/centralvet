package com.centralvet.core;

import com.centralvet.core.config.DataLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class CentralVetApplication implements CommandLineRunner {

	@Autowired
	DataLoader dataLoader;

	public static void main(String[] args) {
		SpringApplication.run(CentralVetApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		dataLoader.loadData();
	}
}

