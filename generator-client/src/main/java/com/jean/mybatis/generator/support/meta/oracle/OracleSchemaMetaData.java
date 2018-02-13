package com.jean.mybatis.generator.support.meta.oracle;

import com.jean.mybatis.generator.support.meta.AbstractSchemaMetaData;

/**
 * @author jinshubao
 */
public class OracleSchemaMetaData extends AbstractSchemaMetaData {

    @Override
    public String toString() {
        return this.getTableSchema();
    }
}
