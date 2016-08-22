package uk.co.songt.ikea.routes

import com.jayway.jsonpath.JsonPath
import spock.lang.Specification
import uk.co.songt.ikea.route.ChairListRoute
import uk.co.songt.ikea.util.FileUtil

class JsonPathSpec extends Specification {
    def  "valide json path is able exract  chair urls"(){
        given:
            String chairlListJson = FileUtil.getStringFromPath(this.getClass(),"json/chairList.json");
        when:
            def obj =  JsonPath.read(chairlListJson, "\$.extractorData.data[0].group[*].['Productpadding link'][0].href");
        then:
            assert obj.size()==25
    }


}
