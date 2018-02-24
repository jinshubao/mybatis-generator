package com.jean.mybatis.generator.support.meta;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Schema信息
 *
 * @author jinshubao
 */
public class SchemaMetaData extends CatalogMetaData {

    private StringProperty tableSchema = new SimpleStringProperty();

    public String getTableSchema() {
        return tableSchema.get();
    }

    public StringProperty tableSchemaProperty() {
        return tableSchema;
    }

    public void setTableSchema(String tableSchema) {
        this.tableSchema.set(tableSchema);
    }

    @Override
    public String toString() {
        return getTableSchema();
    }
}
