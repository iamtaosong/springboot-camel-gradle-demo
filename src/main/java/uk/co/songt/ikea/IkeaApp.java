package uk.co.songt.ikea;

@SpringBootApplication
public class IkeaApp {
    public static void main(String[] args) throws Exception {

        System.out.println("Let's inspect the beans provided by Spring Boot:");

        new SpringApplication(IkeaApp.class).run(args);
    }
}
