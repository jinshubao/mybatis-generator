package com.jean.mybatis.generator.constant;

public enum JavaClientType {
    ANNOTATEDMAPPER("ANNOTATEDMAPPER"),
    MIXEDMAPPER("MIXEDMAPPER"),
    XMLMAPPER("XMLMAPPER"),
    IBATIS("IBATIS"),
    GENERIC_CI("GENERIC-CI"),
    GENERIC_SI("GENERIC-SI"),
    SPRING("SPRING");

    private String value;

    JavaClientType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
