package uk.co.songt.ikea.processor;

import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.PropertyInject;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class CsvProcessor implements Processor {

    public void process(Exchange exchange) throws Exception {
        String input = exchange.getIn().getBody(String.class);
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("url", Arrays.asList(input.split(",")) );

        exchange.getOut().setBody(body);
    }
}