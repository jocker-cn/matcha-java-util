package org.jokerCN.matcha.json;


import org.jokerCN.matcha.json.pojo.JsonTestPojo;
import org.junit.Assert;
import org.junit.Test;

public class JsonSupportTest {


    @Test
    public void jsonTest() {
        JsonTestPojo jsonTestPojo = JsonTestPojo.create();

        JsonParse defaultJsonParse = JsonSupport.DEFAULT_JSON_PARSE;

        String s = defaultJsonParse.toJson(jsonTestPojo);

        JsonTestPojo jsonTestPojo1 = defaultJsonParse.fromJson(s, JsonTestPojo.class);

        Assert.assertNotNull(jsonTestPojo1);
    }
}
