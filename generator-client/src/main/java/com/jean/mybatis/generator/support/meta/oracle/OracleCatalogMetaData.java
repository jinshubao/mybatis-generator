package com.jean.mybatis.generator.support.meta.oracle;

import com.jean.mybatis.generator.support.meta.AbstractCatalogMetaData;

/**
 * @author jinshubao
 */
public class OracleCatalogMetaData extends AbstractCatalogMetaData {

    @Override
    public String toString() {
        return this.getTableCatalog();
    }
}
