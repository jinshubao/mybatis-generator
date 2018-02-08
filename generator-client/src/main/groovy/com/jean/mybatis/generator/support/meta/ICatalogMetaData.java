package com.jean.mybatis.generator.support.meta;

import com.jean.mybatis.generator.support.common.INaming;

/**
 * Catalog信息
 */
public interface ICatalogMetaData extends INaming {

    String getTableCatalog();
}
