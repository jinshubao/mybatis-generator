package com.jean.mybatis.generator.support.table;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TableMetadata implements ITableMetadata {

    private StringProperty name = new SimpleStringProperty();

    private StringProperty comment = new SimpleStringProperty();

    private BooleanProperty selected = new SimpleBooleanProperty();

    public TableMetadata() {

    }

    public TableMetadata(String name, String comment) {
        this.name.set(name);
        this.comment.set(comment);
        this.selected.set(false);
    }

    @Override
    public String getName() {
        return name.get();
    }

    @Override
    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    @Override
    public String getComment() {
        return comment.get();
    }

    @Override
    public StringProperty commentProperty() {
        return this.comment;
    }

    @Override
    public boolean isSelected() {
        return selected.get();
    }

    @Override
    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }

    public void setComment(String comment) {
        this.comment.set(comment);
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public BooleanProperty selectedProperty() {
        return this.selected;
    }
}
