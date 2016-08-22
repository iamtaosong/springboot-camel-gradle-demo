package uk.co.songt.ikea.route;

import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.apache.camel.*;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.songt.ikea.AppConstant;
import uk.co.songt.ikea.processor.CsvProcessor;
import uk.co.songt.ikea.processor.UrlProcessor;

import static javax.ws.rs.core.Response.Status.OK;
import static org.apache.camel.Exchange.*;
import static org.apache.camel.LoggingLevel.INFO;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Component
public class ChairListRoute extends RouteBuilder {

    public static final String CHAIR_LIST_ROUTE = "direct:chairListRoute";
    public static final String CHAIR_ROUTE_ID = "chairListRouteId";
    @PropertyInject("{{import_io_base_url}}")
    private  String importIoBaseURl;
    @PropertyInject("{{csv_file_name}}")
    private  String csvFileName;
    @PropertyInject("{{file_csv}}")
    private  String fileCSV;

    @Autowired
    UrlProcessor urlProcessor;
    @Autowired
    CsvProcessor csvProcessor;
    @Override
    public void configure() throws Exception {
        from(CHAIR_LIST_ROUTE)
                .setHeader(HTTP_URI, simple(importIoBaseURl))
                .setHeader(HTTP_METHOD, simple(GET.name()))
                .setHeader(CONTENT_TYPE, simple("application/json}"))
                .log(INFO, "Fetching chairs from URI $simple{in.header.CamelHttpUri} started.")
                .to(AppConstant.HTTP_ENDPOINT_CONTENT)
                .choice()
                .when(header(HTTP_RESPONSE_CODE).isNotEqualTo(OK.getStatusCode()))
                .log(INFO, "Fetching chair list json failed with reponse code  $simple{in.header.CamelHttpResponseCode}.")
                .to("log:org?showHeaders=true")
                .otherwise()
                .process(urlProcessor)
                .split(body())
//                .aggregationStrategy()
//                .parallelProcessing()
                .to(ChairRoute.CHAIR_ROUTE)
                .end()
//                .log(LoggingLevel.INFO, "chair json  $simple{body}");
                .process(csvProcessor)
                .marshal().csv()
                .to(fileCSV+csvFileName);



    }

}

