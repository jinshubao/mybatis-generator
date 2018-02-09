package com.jean.mybatis.generator.support.meta;

import com.jean.mybatis.generator.support.common.INaming;
import javafx.beans.property.StringProperty;

/**
 * Catalog信息
 * @author jinshubao
 */
public interface ICatalogMetaData extends INaming, Selectable {

    String getTableCatalog();

    void setTableCatalog(String tableCatalog);

    StringProperty tableCatalogProperty();

}
