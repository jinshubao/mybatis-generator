package com.jean.mybatis.generator.support.meta.mysql;

import com.jean.mybatis.generator.support.meta.AbstractColumnMetaData;

/**
 * @author jinshubao
 */
public class MySqlColumnMetaData extends AbstractColumnMetaData {

    @Override
    public String toString() {
        return this.getColumnName();
    }
}
