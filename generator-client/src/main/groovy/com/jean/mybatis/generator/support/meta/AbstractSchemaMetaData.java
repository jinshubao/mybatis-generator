package com.jean.mybatis.generator.support.meta;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class AbstractSchemaMetaData implements ISchemaMetaData {

    private StringProperty tableCatalog = new SimpleStringProperty();

    private StringProperty tableSchema = new SimpleStringProperty();

    public AbstractSchemaMetaData() {
    }

    public AbstractSchemaMetaData(String tableCatalog, String tableSchema) {
        this.tableSchema.set(tableSchema);
        this.tableCatalog.set(tableCatalog);
    }

    public String getTableCatalog() {
        return tableCatalog.get();
    }

    public StringProperty tableCatalogProperty() {
        return tableCatalog;
    }

    public void setTableCatalog(String tableCatalog) {
        this.tableCatalog.set(tableCatalog);
    }

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
    public String getName() {
        return getTableSchema();
    }
}
