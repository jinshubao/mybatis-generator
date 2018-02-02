package com.jean.mybatis.generator.support.table;

public class TableMetadata implements ITableMetadata {

    private String name;
    private String comment;

    private boolean selected;

    public TableMetadata() {
    }

    public TableMetadata(String name, String comment) {
        this.name = name;
        this.comment = comment;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getComment() {
        return comment;
    }

    @Override
    public boolean isSelected() {
        return selected;
    }

    @Override
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return name;
    }
}
