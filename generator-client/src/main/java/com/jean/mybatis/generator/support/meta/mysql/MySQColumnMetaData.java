package com.jean.mybatis.generator.support.meta.mysql;

import com.jean.mybatis.generator.support.meta.AbstractColumnMetaData;

public class MySQColumnMetaData extends AbstractColumnMetaData {

    @Override
    public String toString() {
        return this.getColumnName();
    }
}
