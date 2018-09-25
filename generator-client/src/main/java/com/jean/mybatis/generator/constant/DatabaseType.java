package com.jean.mybatis.generator.constant;

/**
 * @author jinshubao
 * @date 2017/4/9
 */
public enum DatabaseType {

    /**
     * MySql
     */
    MySql("MySql", "com.mysql.jdbc.Driver", "serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&useSSL=false"),
    /**
     * Oracle
     */
    Oracle("Oracle", "oracle.jdbc.driver.OracleDriver", "");

    public String name;
    public String driverClass;
    public String properties;

    DatabaseType(String name, String driverClass, String properties) {
        this.driverClass = driverClass;
        this.name = name;
        this.properties = properties;
    }

    @Override
    public String toString() {
        return name;
    }
}