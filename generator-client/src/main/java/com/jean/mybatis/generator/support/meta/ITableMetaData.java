package com.jean.mybatis.generator.support.meta;

import javafx.beans.property.StringProperty;
import org.mybatis.generator.config.ColumnOverride;
import org.mybatis.generator.config.IgnoredColumn;

import java.util.List;

/**
 * 表信息
 *
 * @author jinshubao
 */
public interface ITableMetaData extends ISchemaMetaData {

    String getTableName();

    StringProperty tableNameProperty();

    void setTableName(String tableName);

    String getRemarks();

    StringProperty remarksProperty();

    void setRemarks(String remarks);

    String getTableType();

    StringProperty tableTypeProperty();

    void setTableType(String tableType);

    void addColumnOverride(ColumnOverride columnOverride);

    void clearColumnOverrides();

    List<ColumnOverride> getColumnOverrides();

     void addIgnoredColumn(IgnoredColumn ignoredColumn);

     void clearIgnoredColumns();

     List<IgnoredColumn> getIgnoredColumns();


}