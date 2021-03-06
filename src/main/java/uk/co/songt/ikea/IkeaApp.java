package uk.co.songt.ikea;

import org.apache.camel.spring.boot.CamelSpringBootApplicationController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class IkeaApp {


    public static void main(String[] args) throws Exception {

        ApplicationContext applicationContext = new SpringApplication(IkeaApp.class).run(args);
        CamelSpringBootApplicationController applicationController =
                applicationContext.getBean(CamelSpringBootApplicationController.class);
        applicationController.blockMainThread();
    }
}
