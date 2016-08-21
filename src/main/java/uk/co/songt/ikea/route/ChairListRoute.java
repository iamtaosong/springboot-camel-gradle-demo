package uk.co.songt.ikea.route;


import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import static org.apache.camel.LoggingLevel.INFO;
@Component
public class ChairListRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("timer://foo?fixedRate=true&period=10s").log("Camel timer triggered.");
    }


}

