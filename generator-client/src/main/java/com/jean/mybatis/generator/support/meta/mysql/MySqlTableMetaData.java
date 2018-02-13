package com.jean.mybatis.generator.support.meta.mysql;

import com.jean.mybatis.generator.support.meta.AbstractTableMetaData;

/**
 * @author jinshubao
 */
public class MySqlTableMetaData extends AbstractTableMetaData {


    @Override
    public String toString() {
        return this.getTableName();
    }
}
