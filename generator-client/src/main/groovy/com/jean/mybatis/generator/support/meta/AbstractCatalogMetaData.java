package com.jean.mybatis.generator.support.meta;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class AbstractCatalogMetaData implements ICatalogMetaData {

    private StringProperty tableCatalog = new SimpleStringProperty();

    public AbstractCatalogMetaData() {
    }

    public AbstractCatalogMetaData(String tableCatalog) {
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

    @Override
    public String getName() {
        return getTableCatalog();
    }
}
