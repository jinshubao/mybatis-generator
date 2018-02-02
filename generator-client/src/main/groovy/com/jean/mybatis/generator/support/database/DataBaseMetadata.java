package com.jean.mybatis.generator.support.database;

public class DataBaseMetadata implements IDatabaseMetadata {

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

    @Override
    public String toString() {
        return name;
    }
}
