package com.jean.mybatis.generator.support.meta;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.mybatis.generator.config.ColumnOverride;
import org.mybatis.generator.config.IgnoredColumn;

import java.util.ArrayList;
import java.util.List;

/**
 * 表信息
 *
 * @author jinshubao
 */
public class TableMetaData extends SchemaMetaData {

    private StringProperty tableName = new SimpleStringProperty();

    private StringProperty remarks = new SimpleStringProperty();

    private StringProperty tableType = new SimpleStringProperty();

    private List<ColumnOverride> columnOverrides = new ArrayList<>();

    private List<IgnoredColumn> ignoredColumns = new ArrayList<>();

    private StringProperty primaryKeyColumn = new SimpleStringProperty();

    public String getTableName() {
        return tableName.get();
    }

    public StringProperty tableNameProperty() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName.set(tableName);
    }

    public String getRemarks() {
        return remarks.get();
    }

    public StringProperty remarksProperty() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks.set(remarks);
    }

    public String getTableType() {
        return tableType.get();
    }

    public StringProperty tableTypeProperty() {
        return tableType;
    }

    public void setTableType(String tableType) {
        this.tableType.set(tableType);
    }

    public List<ColumnOverride> getColumnOverrides() {
        return columnOverrides;
    }

    public void setColumnOverrides(List<ColumnOverride> columnOverrides) {
        this.columnOverrides = columnOverrides;
    }

    public List<IgnoredColumn> getIgnoredColumns() {
        return ignoredColumns;
    }

    public void setIgnoredColumns(List<IgnoredColumn> ignoredColumns) {
        this.ignoredColumns = ignoredColumns;
    }

    public String getPrimaryKeyColumn() {
        return primaryKeyColumn.get();
    }

    public StringProperty primaryKeyColumnProperty() {
        return primaryKeyColumn;
    }

    public void setPrimaryKeyColumn(String primaryKeyColumn) {
        this.primaryKeyColumn.set(primaryKeyColumn);
    }
}