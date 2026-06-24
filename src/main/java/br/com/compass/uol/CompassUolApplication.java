package br.com.compass.uol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class CompassUolApplication {

    public static void main(String[] args) {
        SpringApplication.run(CompassUolApplication.class, args);
    }

}
