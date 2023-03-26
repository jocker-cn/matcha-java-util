package org.jokerCN.matcha.json.pojo;

import java.util.List;


public class JsonTestPojo {

    private int id;


    private String name;


    private List<String> values;


    private List<JsonTestPojo> tests;


    private Long dtId;


    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public void setTests(List<JsonTestPojo> tests) {
        this.tests = tests;
    }

    public void setDtId(Long dtId) {
        this.dtId = dtId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getValues() {
        return values;
    }

    public List<JsonTestPojo> getTests() {
        return tests;
    }

    public Long getDtId() {
        return dtId;
    }

    public static JsonTestPojo create() {
        JsonTestPojo jacksonParse = new JsonTestPojo();
        jacksonParse.setId(1);
        jacksonParse.setName("name");
        jacksonParse.setValues(List.of("1", "2", "3", "4"));
        jacksonParse.setTests(List.of(new JsonTestPojo()));
        jacksonParse.setDtId(Long.MAX_VALUE);
        return jacksonParse;
    }
}
