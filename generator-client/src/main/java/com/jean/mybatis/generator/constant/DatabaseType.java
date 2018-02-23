package com.jean.mybatis.generator.constant;

/**
 *
 * @author jinshubao
 * @date 2017/4/9
 */

public enum DatabaseType {

    MySql("MySql", "com.mysql.jdbc.Driver", "jdbc:mysql://%s:%d/%s",  "serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&useSSL=false"),
    Oracle("Oracle", "oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:@//%s:%d/%s","");

    public String name;
    public String driverClass;
    public String urlTemplate;
    public String properties;

    DatabaseType(String name, String driverClass,String urlTemplate, String properties) {
        this.driverClass = driverClass;
        this.name = name;
        this.urlTemplate = urlTemplate;
        this.properties = properties;
    }

    @Override
    public String toString() {
        return name;
    }
}