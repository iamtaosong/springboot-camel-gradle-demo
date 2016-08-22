package uk.co.songt.ikea.route;

import org.apache.camel.LoggingLevel;
import org.apache.camel.PropertyInject;
import org.apache.camel.builder.RouteBuilder;

//@Component
public class Scheduler extends RouteBuilder {

    @PropertyInject("{{ikea.import.cron}}")
    private  String ikeaImportCron;
    @Override
    public void configure() throws Exception {
        from(ikeaImportCron)
                .log(LoggingLevel.INFO,"Ikea cron router started")
                .to(ChairListRoute.CHAIR_LIST_ROUTE);
    }

}

