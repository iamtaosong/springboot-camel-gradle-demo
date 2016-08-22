package uk.co.songt.ikea.routes

import com.jayway.jsonpath.JsonPath
import spock.lang.Specification
import uk.co.songt.ikea.model.Chair
import uk.co.songt.ikea.util.ChairConvertor
import uk.co.songt.ikea.util.FileUtil


class ChairConvertorSpec extends Specification {

    def  "valide json path is able exract  chair urls"(){
        given:
            String chairlListJson = FileUtil.getStringFromPath(this.getClass(),"json/chair-50212486.json");
        when:
           def chair =  ChairConvertor.getChair(chairlListJson)
        then:
            assert chair.getName()=='ABSORB'
            assert chair.getUrl()=='http://www.ikea.com/gb/en/catalog/products/50212486/'
            assert chair.getPrice()=='£16'
    }
}
