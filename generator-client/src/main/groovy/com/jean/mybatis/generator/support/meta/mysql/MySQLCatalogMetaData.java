package com.jean.mybatis.generator.support.meta.mysql;

import com.jean.mybatis.generator.support.meta.AbstractCatalogMetaData;

/**
 * @author jinshubao
 */
public class MySQLCatalogMetaData extends AbstractCatalogMetaData {

    @Override
    public String toString() {
        return this.getTableCatalog();
    }
}
