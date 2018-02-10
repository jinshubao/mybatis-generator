package com.jean.mybatis.generator.support.meta;

/**
 * @author jinshubao
 */
public interface ITableMetaData extends ISchemaMetaData {

    String getTableName();

    void  setTableName(String tableName);

    String getRemarks();

    void setRemarks(String remarks);

    String getTableType();

    void setTableType(String tableType);

}