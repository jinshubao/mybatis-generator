package com.jean.mybatis.generator.support.meta;

/**
 * @author jinshubao
 */
public interface IColumnMetaData extends ITableMetaData {

    String getColumnName();
    void setColumnName(String columnName);

    int getDataType();

    String getDataTypeName();

    int getColumnSize();

    int getDecimalDigits();

    int getNumPrecRadix();

    int getNullAble();

    String getColumnDef();

    int getCharOctetLength();

    String getIsNullAble();

    void setDataType(int dataType);

    void setDataTypeName(String dataTypeName);

    void setColumnSize(int columnSize);

    void setDecimalDigits(int decimalDigits);

    void setNumPrecRadix(int numPrecRadix);

    void setNullAble(int nullAble);

    void setColumnDef(String columnDef);

    void setCharOctetLength(int charOctetLength);

    void setIsNullAble(String isNullAble);
}