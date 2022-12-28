package com.epam.sport;

import com.epam.sport.service.SetupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SportApplication implements CommandLineRunner {

    @Autowired
    private SetupService setupService;

    public static void main(String[] args) {
        SpringApplication.run(SportApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        //setupService.setUpWithBackPressure();
        setupService.setUp();
    }
}
