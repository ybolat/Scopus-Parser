package kz.edu.astanait.diplomascopus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DiplomaScopusApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiplomaScopusApplication.class, args);
    }

}
