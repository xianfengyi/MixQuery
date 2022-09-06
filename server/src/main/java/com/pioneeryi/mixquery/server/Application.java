package com.pioneeryi.mixquery.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.pioneeryi.mixquery"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
