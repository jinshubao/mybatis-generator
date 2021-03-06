package com.jean.mybatis.generator.constant;

/**
 * @author jinshubao
 */
public enum JavaClientType {
    ANNOTATEDMAPPER("ANNOTATEDMAPPER"),
    MIXEDMAPPER("MIXEDMAPPER"),
    XMLMAPPER("XMLMAPPER"),
    IBATIS("IBATIS"),
    GENERIC_CI("GENERIC-CI"),
    GENERIC_SI("GENERIC-SI"),
    SPRING("SPRING");

    protected String value;

    JavaClientType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
