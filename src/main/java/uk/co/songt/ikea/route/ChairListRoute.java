package uk.co.songt.ikea.route;

import org.apache.camel.PropertyInject;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import static org.apache.camel.Exchange.*;
import static org.apache.camel.LoggingLevel.INFO;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Component
public class ChairListRoute extends RouteBuilder {

    public static final String CHAIR_LIST_ROUTE = "direct:chairListRoute";
    public static final String CHAIR_ROUTE_ID = "chairListRouteId";
    @PropertyInject("{{import_io_base_url}}")
    private  String importIoBaseURl;

    @Override
    public void configure() throws Exception {
        from(CHAIR_LIST_ROUTE)
                .setHeader(HTTP_URI, simple(importIoBaseURl))
                .setHeader(HTTP_METHOD, simple(GET.name()))
                .setHeader(CONTENT_TYPE, simple("application/json}"))
                .log(INFO, "Fetching chairs from URI $simple{in.header.CamelHttpUri} started.");

    }

}

