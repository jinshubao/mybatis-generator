package com.jean.mybatis.generator.support.meta;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author jinshubao
 */
public abstract class AbstractCatalogMetaData implements ICatalogMetaData {

    protected StringProperty tableCatalog = new SimpleStringProperty();

    protected BooleanProperty selected = new SimpleBooleanProperty();


    @Override
    public boolean isSelected() {
        return selected.get();
    }

    @Override
    public BooleanProperty selectedProperty() {
        return selected;
    }

    @Override
    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }

    @Override
    public String getTableCatalog() {
        return tableCatalog.get();
    }

    @Override
    public StringProperty tableCatalogProperty() {
        return tableCatalog;
    }

    @Override
    public void setTableCatalog(String tableCatalog) {
        this.tableCatalog.set(tableCatalog);
    }

    @Override
    public String getName() {
        return getTableCatalog();
    }
}
