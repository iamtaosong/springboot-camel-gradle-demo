package uk.co.songt.ikea.route

import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.junit.WireMockRule
import org.apache.camel.CamelContext
import org.apache.camel.PropertyInject
import org.junit.Rule
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import spock.lang.Specification
import uk.co.songt.ikea.IkeaApp
import uk.co.songt.ikea.util.FileUtil
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static javax.ws.rs.core.Response.Status.FOUND;
import static javax.ws.rs.core.Response.Status.OK;

@SpringApplicationConfiguration(classes = IkeaApp)
class ChairListRouteSpec extends Specification {

    @Autowired
    CamelContext camelContext

    @PropertyInject("{{import_io_base_url}}")
    private  String importIoBaseURl
    final String CHAIR_50212486_URL = "json/chair-50212486.json"
    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8089);
    def setupSpec() {

    }

    def cleanupSpec() {
       // WireMock.shutdownServer()
    }


//    def "asdf"(){
//        when:
//            mockChairsEndpoint()
//            triggerChairListRoute()
//        then:
//            assert true
//    }
    private void mockChairsEndpoint() {
        String rootPayload =   FileUtil.getStringFromPath(this.getClass(), "json/chairList.json")
        stubFor(get(urlEqualTo("/chairs"))
                .willReturn(aResponse()
                .withStatus(OK.getStatusCode())
                .withHeader("Content-Type", "application/json charset=utf-8")
                .withBody(rootPayload)))
    }

    private void triggerChairListRoute() throws InterruptedException {
        camelContext.createProducerTemplate().sendBody(ChairListRoute.CHAIR_LIST_ROUTE, null)
        Thread.sleep(1000)
    }

}

