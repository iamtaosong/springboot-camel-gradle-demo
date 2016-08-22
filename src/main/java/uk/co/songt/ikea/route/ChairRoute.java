package uk.co.songt.ikea.route;

import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.songt.ikea.AppConstant;
import uk.co.songt.ikea.processor.ChairJsonProcessor;

import static javax.ws.rs.core.Response.Status.OK;
import static org.apache.camel.Exchange.*;
import static org.apache.camel.LoggingLevel.INFO;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Component
public class ChairRoute extends RouteBuilder {

    public static final String CHAIR_ROUTE = "direct:chairRoute";
    public static final String CHAIR_ROUTE_ID = "chairRouteId";
    @Autowired
    ChairJsonProcessor chairJsonProcessor;
    @Override
    public void configure() throws Exception {
        from(CHAIR_ROUTE)
                .setHeader(HTTP_URI,  body(String.class))
                .setHeader(HTTP_METHOD, simple(GET.name()))
                .setHeader(CONTENT_TYPE, simple("application/json}"))
                .to(AppConstant.HTTP_ENDPOINT_CONTENT)
                .choice()
                .when(header(HTTP_RESPONSE_CODE).isNotEqualTo(OK.getStatusCode()))
                .log(INFO, "Fetching chair json failed with reponse code  $simple{in.header.CamelHttpResponseCode}.")
                .to("log:org?showHeaders=true")
                .otherwise()
                .process(chairJsonProcessor);
    }
}