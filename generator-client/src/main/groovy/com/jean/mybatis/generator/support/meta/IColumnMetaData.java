package com.jean.mybatis.generator.support.meta;

public interface IColumnMetaData {

    String getTableCatalog();

    String getTableSchema();

    String getTableName();

    String getColumnName();

    int getDataType();

    String getDataTypeName();

    int getColumnSize();

    int getDecimalDigits();

    int getNumPrecRadix();

    int getNullAble();

    String getRemarks();

    String getColumnDef();

    int getCharOctetLength();

    String getIsNullAble();

}