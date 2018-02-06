package com.jean.mybatis.generator.constant;

public enum MethodVisibility {

    PUBLIC("public"),
    PROTECTED("protected"),
    DEFAULT("default"),
    PRIVATE("private");

    protected String value;

    MethodVisibility(String value) {
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
