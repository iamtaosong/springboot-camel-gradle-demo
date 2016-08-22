package uk.co.songt.ikea.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;
import uk.co.songt.ikea.util.ChairConvertor;

@Component
public class ChairJsonProcessor implements Processor {

    public void process(Exchange exchange) throws Exception {
        String input = exchange.getIn().getBody(String.class);
        exchange.getOut().setBody(ChairConvertor.getChair(input));
    }
}