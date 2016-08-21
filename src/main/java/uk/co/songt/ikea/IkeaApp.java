package uk.co.songt.ikea;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class IkeaApp {
    public static void main(String... args){
        ApplicationContext applicationContext = new SpringApplication(IkeaApp.class).run(args);
    }
}
