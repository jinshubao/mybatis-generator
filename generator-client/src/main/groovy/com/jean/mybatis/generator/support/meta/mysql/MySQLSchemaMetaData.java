package com.jean.mybatis.generator.support.meta.mysql;

import com.jean.mybatis.generator.support.meta.AbstractSchemaMetaData;

public class MySQLSchemaMetaData extends AbstractSchemaMetaData {

    public MySQLSchemaMetaData() {
    }

    public MySQLSchemaMetaData(String tableCatalog, String tableSchema) {
        super(tableCatalog, tableSchema);
    }

    @Override
    public String toString() {
        return this.getTableSchema();
    }
}
