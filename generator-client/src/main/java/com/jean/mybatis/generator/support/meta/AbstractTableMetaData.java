package com.jean.mybatis.generator.support.meta;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.mybatis.generator.config.ColumnOverride;
import org.mybatis.generator.config.IgnoredColumn;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jinshubao
 */
public abstract class AbstractTableMetaData extends AbstractSchemaMetaData implements ITableMetaData {

    protected StringProperty tableName = new SimpleStringProperty();

    protected StringProperty remarks = new SimpleStringProperty();

    protected StringProperty tableType = new SimpleStringProperty();

    protected List<ColumnOverride> columnOverrides = new ArrayList<>();

    protected List<IgnoredColumn> ignoredColumns = new ArrayList<>();

    @Override
    public String getTableName() {
        return tableName.get();
    }

    @Override
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

    @Override
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

    @Override
    public StringProperty tableTypeProperty() {
        return tableType;
    }

    @Override
    public void setTableType(String tableType) {
        this.tableType.set(tableType);
    }

    @Override
    public void addColumnOverride(ColumnOverride columnOverride) {
        this.columnOverrides.add(columnOverride);
    }

    @Override
    public void clearColumnOverrides() {
        this.columnOverrides.clear();
    }

    @Override
    public List<ColumnOverride> getColumnOverrides() {
        return columnOverrides;
    }

    public void setColumnOverrides(List<ColumnOverride> columnOverrides) {
        this.columnOverrides = columnOverrides;
    }

    @Override
    public List<IgnoredColumn> getIgnoredColumns() {
        return ignoredColumns;
    }

    public void setIgnoredColumns(List<IgnoredColumn> ignoredColumns) {
        this.ignoredColumns = ignoredColumns;
    }


    @Override
    public void addIgnoredColumn(IgnoredColumn ignoredColumn) {
        this.ignoredColumns.add(ignoredColumn);
    }

    @Override
    public void clearIgnoredColumns() {
        this.ignoredColumns.clear();
    }

    @Override
    public String getName() {
        return getTableName();
    }

}
