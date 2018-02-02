package com.jean.mybatis.generator.constant;

/**
 *
 * Created by jinshubao on 2017/4/9.
 */

public enum DatabaseType {
    MySql("MySql", "com.mysql.jdbc.Driver"),
    Oracle("Oracle", "oracle.jdbc.driver.OracleDriver");

    public String name;
    public String driverClass;

    DatabaseType(String name, String driverClass) {
        this.driverClass = driverClass;
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}