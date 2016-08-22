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
import static javax.ws.rs.core.Response.Status.OK;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = IkeaApp.class)
public class ChairRouteTest {

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


}

