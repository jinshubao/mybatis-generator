package com.jean.mybatis.generator.support.meta.mysql;

import com.jean.mybatis.generator.support.meta.AbstractSchemaMetaData;

/**
 * @author jinshubao
 */
public class MySqlSchemaMetaData extends AbstractSchemaMetaData {

    @Override
    public String toString() {
        return this.getTableSchema();
    }
}
