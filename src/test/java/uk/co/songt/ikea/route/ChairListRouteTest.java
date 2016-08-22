package uk.co.songt.ikea.route;


import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.apache.camel.CamelContext;
import org.apache.camel.PropertyInject;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.co.songt.ikea.IkeaApp;
import uk.co.songt.ikea.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static javax.ws.rs.core.Response.Status.FORBIDDEN;
import static javax.ws.rs.core.Response.Status.OK;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = IkeaApp.class)
public class ChairListRouteTest {

    @Autowired
    CamelContext camelContext;
    final String CHAIR_LIST_JON = "json/chairList.json";
    final String CHAIR_50212486_URL = "json/chair-50212486.json";
    final String JSON_TYPE="application/json charset=utf-8";
    final String CONTENT_TYPE="Content-Type";
    final String DATA_PATH="./data";
    @PropertyInject("{{csv_file_name}}")
    private  String csvFileName;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8089);


    @Before
    public void setUp() throws Exception {


    }

    @After
    public void tearDown() throws InterruptedException {
        WireMock.shutdownServer();
        deleteFTPFolder(DATA_PATH);
    }

    @Test
    public void testGenerateFile() throws Exception  {
        mockChairsEndpoint();
        triggerChairListRoute();
        List<String> fileNames = getFileNamesInFolder(DATA_PATH);
        assert fileNames.contains(csvFileName)==true;
    }

    @Test(expected=NullPointerException.class)
    public void testNon200Code() throws Exception  {
        mockChairsEndpointReturn403();
        triggerChairListRoute();
        getFileNamesInFolder(DATA_PATH);

    }

    private void mockChairsEndpoint() {
        String rootPayload =   FileUtil.getStringFromPath(this.getClass(),CHAIR_LIST_JON);
        stubFor(get(urlEqualTo("/chairs"))
                .willReturn(aResponse()
                        .withStatus(OK.getStatusCode())
                        .withHeader(CONTENT_TYPE, JSON_TYPE)
                        .withBody(rootPayload)));

        rootPayload =   FileUtil.getStringFromPath(this.getClass(),CHAIR_50212486_URL);
        stubFor(get(urlMatching("/chairs/.*"))
                .willReturn(aResponse()
                        .withStatus(OK.getStatusCode())
                        .withHeader(CONTENT_TYPE, JSON_TYPE)
                        .withBody(rootPayload)));
    }

    private void mockChairsEndpointReturn403() {
        stubFor(get(urlEqualTo("/chairs"))
                .willReturn(aResponse()
                        .withStatus(FORBIDDEN.getStatusCode())
                        .withHeader(CONTENT_TYPE, JSON_TYPE)));
    }

    private List<String> getFileNamesInFolder(String dir){
        File[] listOfFiles = getFiles(dir);
        List<String> fileNames = new ArrayList<String>();
        for (int i = 0; i < listOfFiles.length; i++) {
            fileNames.add(listOfFiles[i].getName());
        }
        return fileNames;
    }

    private File[] getFiles(String dir) {
        File folder = new File(dir);
        return folder.listFiles();
    }

    private static  void deleteFTPFolder(String dir) {
        try{
            FileUtils.deleteDirectory(new File(dir));
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    private void triggerChairListRoute() throws InterruptedException {
        camelContext.createProducerTemplate().sendBody(ChairListRoute.CHAIR_LIST_ROUTE, null);
    }

}

