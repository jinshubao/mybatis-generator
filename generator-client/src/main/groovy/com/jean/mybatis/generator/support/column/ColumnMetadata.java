package com.jean.mybatis.generator.support.column;

public class ColumnMetadata implements IColumnMetadata {

    private String name;
    private String comment;

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

    public void setComment(String comment) {
        this.comment = comment;
    }
}
