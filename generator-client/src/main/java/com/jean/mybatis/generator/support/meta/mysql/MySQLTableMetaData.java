package com.jean.mybatis.generator.support.meta.mysql;

import com.jean.mybatis.generator.support.meta.AbstractTableMetaData;

public class MySQLTableMetaData extends AbstractTableMetaData {


    @Override
    public String toString() {
        return this.getTableName();
    }
}
