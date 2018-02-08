package com.jean.mybatis.generator.support.meta;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class AbstractTableMetaData implements ITableMetaData {

    private StringProperty tableSchema = new SimpleStringProperty();

    private StringProperty tableCatalog = new SimpleStringProperty();

    private StringProperty tableName = new SimpleStringProperty();

    private StringProperty remarks = new SimpleStringProperty();

    private BooleanProperty selected = new SimpleBooleanProperty();

    private StringProperty tableType = new SimpleStringProperty();

    @Override
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
    public String getTableCatalog() {
        return tableCatalog.get();
    }

    public StringProperty tableCatalogProperty() {
        return tableCatalog;
    }

    public void setTableCatalog(String tableCatalog) {
        this.tableCatalog.set(tableCatalog);
    }

    @Override
    public String getTableName() {
        return tableName.get();
    }

    public StringProperty tableNameProperty() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName.set(tableName);
    }

    @Override
    public String getRemarks() {
        return remarks.get();
    }

    public StringProperty remarksProperty() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks.set(remarks);
    }

    public boolean isSelected() {
        return selected.get();
    }

    @Override
    public BooleanProperty selectedProperty() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }

    @Override
    public String getTableType() {
        return tableType.get();
    }

    public StringProperty tableTypeProperty() {
        return tableType;
    }

    public void setTableType(String tableType) {
        this.tableType.set(tableType);
    }

    @Override
    public String getName() {
        return getTableName();
    }


}
