package uk.co.songt.ikea.route;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.apache.camel.CamelContext;
import org.apache.camel.PropertyInject;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import spock.lang.Specification;
import uk.co.songt.ikea.IkeaApp;
import uk.co.songt.ikea.util.FileUtil;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static javax.ws.rs.core.Response.Status.OK;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = IkeaApp.class)
class ChairListRouteTest extends Specification {

    @Autowired
    CamelContext camelContext;

    @PropertyInject("{{import_io_base_url}}")
    private  String importIoBaseURl;
    final String CHAIR_50212486_URL = "json/chair-50212486.json";
    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8089);

    @Before
    public void setUp() throws Exception {

        mockChairsEndpoint();
    }

    @After
    public void tearDown() throws InterruptedException {
        WireMock.shutdownServer();

    }

    @Test
    public void testEndToEndWithSuccessForDeactived() throws Exception  {
        triggerChairListRoute();
    }

    private void mockChairsEndpoint() {
        String rootPayload =   FileUtil.getStringFromPath(this.getClass(), "json/chairList.json");
        stubFor(get(urlEqualTo("/chairs"))
                .willReturn(aResponse()
                .withStatus(OK.getStatusCode())
                .withHeader("Content-Type", "application/json charset=utf-8")
                .withBody(rootPayload)));
    }

    private void triggerChairListRoute() throws InterruptedException {
        camelContext.createProducerTemplate().sendBody(ChairListRoute.CHAIR_LIST_ROUTE, null);
        Thread.sleep(1000);
    }

}

