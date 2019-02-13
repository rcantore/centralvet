package com.centralvet.core;

import com.centralvet.core.conf.DataLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

