package uk.co.songt.ikea.util;

import com.jayway.jsonpath.JsonPath;
import uk.co.songt.ikea.model.Chair;

import java.util.List;


public class ChairConvertor {

    public static Chair getChair(String json){
        Chair chair =  new Chair();
        chair.setUrl(JsonPath.read(json, "$.extractorData.url"));
        chair.setName(JsonPath.read(json, "$.extractorData.data[0].group[0].['Prodname value'][0].text"));
        chair.setPrice(JsonPath.read(json,"$.extractorData.data[0].group[0].['Prodprice price'][0].text"));
       return chair;
    }
}