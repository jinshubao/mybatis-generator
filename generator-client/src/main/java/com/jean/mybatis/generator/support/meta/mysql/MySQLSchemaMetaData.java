package com.jean.mybatis.generator.support.meta.mysql;

import com.jean.mybatis.generator.support.meta.AbstractSchemaMetaData;

public class MySQLSchemaMetaData extends AbstractSchemaMetaData {

    @Override
    public String toString() {
        return this.getTableSchema();
    }
}
