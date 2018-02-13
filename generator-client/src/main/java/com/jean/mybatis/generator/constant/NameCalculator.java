package com.jean.mybatis.generator.constant;

/**
 * @author jinshubao
 */

public enum NameCalculator {

    DEFAULT("default"),
    EXTENDED("extended");

    protected String value;

    NameCalculator(String value) {
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
