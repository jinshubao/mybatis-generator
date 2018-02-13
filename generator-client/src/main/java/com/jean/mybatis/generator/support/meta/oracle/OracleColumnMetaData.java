package com.jean.mybatis.generator.support.meta.oracle;

import com.jean.mybatis.generator.support.meta.AbstractColumnMetaData;

/**
 * @author jinshubao
 */
public class OracleColumnMetaData extends AbstractColumnMetaData {

    @Override
    public String toString() {
        return this.getColumnName();
    }
}
