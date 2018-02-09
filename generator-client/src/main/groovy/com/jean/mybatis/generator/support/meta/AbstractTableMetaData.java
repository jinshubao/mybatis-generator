package com.jean.mybatis.generator.support.meta;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author jinshubao
 */
public abstract class AbstractTableMetaData extends AbstractSchemaMetaData implements ITableMetaData {

    protected StringProperty tableName = new SimpleStringProperty();

    protected StringProperty remarks = new SimpleStringProperty();

    protected StringProperty tableType = new SimpleStringProperty();

    @Override
    public String getTableName() {
        return tableName.get();
    }

    public StringProperty tableNameProperty() {
        return tableName;
    }

    @Override
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

    @Override
    public void setRemarks(String remarks) {
        this.remarks.set(remarks);
    }

    @Override
    public String getTableType() {
        return tableType.get();
    }

    public StringProperty tableTypeProperty() {
        return tableType;
    }

    @Override
    public void setTableType(String tableType) {
        this.tableType.set(tableType);
    }

    @Override
    public String getName() {
        return getTableName();
    }


}
