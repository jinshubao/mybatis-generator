package com.jean.mybatis.generator.support.meta.mysql;

import com.jean.mybatis.generator.support.meta.AbstractCatalogMetaData;

public class MySQLCatalogMetaData extends AbstractCatalogMetaData {
    public MySQLCatalogMetaData() {
    }

    public MySQLCatalogMetaData(String catalog) {
        super(catalog);
    }

    @Override
    public String toString() {
        return this.getTableCatalog();
    }
}
