package com.jean.mybatis.generator.support.meta.oracle;

import com.jean.mybatis.generator.support.meta.AbstractTableMetaData;

/**
 * @author jinshubao
 */
public class OracleTableMetaData extends AbstractTableMetaData {


    @Override
    public String toString() {
        return this.getTableName();
    }
}
