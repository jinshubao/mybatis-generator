package com.jean.mybatis.generator.support.meta;

import javafx.beans.property.StringProperty;

/**
 * Schema信息
 * @author jinshubao
 */
public interface ISchemaMetaData extends ICatalogMetaData {

    String getTableSchema();

    void setTableSchema(String tableSchema);

    StringProperty tableSchemaProperty();
}
