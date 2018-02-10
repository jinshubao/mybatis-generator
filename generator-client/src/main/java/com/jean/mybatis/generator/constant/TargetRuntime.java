package com.jean.mybatis.generator.constant;

public enum TargetRuntime {
    MyBatis3("MyBatis3"),
    MyBatis3Simple("MyBatis3Simple"),
    MyBatis3DynamicSql("MyBatis3DynamicSql"),
    Ibatis2Java2("Ibatis2Java2"),
    Ibatis2Java5("Ibatis2Java5");


    private String value;

    TargetRuntime(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
