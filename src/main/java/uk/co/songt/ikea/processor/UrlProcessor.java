package uk.co.songt.ikea.processor;

import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.PropertyInject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class UrlProcessor implements Processor {

    @PropertyInject("{{import_io_base_url}}")
    private  String importIoBaseURl;
    @PropertyInject("{{chair_link_jsonpath}}")
    private  String chairLinkJsonPath;
    private static final String SLASH="/";

    public void process(Exchange exchange) throws Exception {
        String input = exchange.getIn().getBody(String.class);
        JSONArray obj = JsonPath.read(input, chairLinkJsonPath);
        exchange.getOut().setBody( obj.stream().map(url->importIoBaseURl+SLASH+url.toString().split("/")[url.toString().split("/").length-1]).toArray());
    }
}