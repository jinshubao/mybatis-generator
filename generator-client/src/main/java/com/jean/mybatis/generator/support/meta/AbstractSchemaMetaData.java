package com.jean.mybatis.generator.support.meta;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author jinshubao
 */
public abstract class AbstractSchemaMetaData extends AbstractCatalogMetaData implements ISchemaMetaData {

    protected StringProperty tableSchema = new SimpleStringProperty();

    @Override
    public String getTableSchema() {
        return tableSchema.get();
    }

    @Override
    public StringProperty tableSchemaProperty() {
        return tableSchema;
    }

    @Override
    public void setTableSchema(String tableSchema) {
        this.tableSchema.set(tableSchema);
    }
}
