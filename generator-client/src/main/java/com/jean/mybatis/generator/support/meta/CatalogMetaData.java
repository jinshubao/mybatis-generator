package com.jean.mybatis.generator.support.meta;

import com.jean.mybatis.generator.support.common.Selectable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Catalog信息
 *
 * @author jinshubao
 */
public class CatalogMetaData implements Selectable {

    private StringProperty tableCatalog = new SimpleStringProperty();

    private BooleanProperty selected = new SimpleBooleanProperty();

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
    public String toString() {
        return getTableCatalog();
    }
}
