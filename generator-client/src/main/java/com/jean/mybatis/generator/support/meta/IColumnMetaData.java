package com.jean.mybatis.generator.support.meta;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

/**
 * @author jinshubao
 */
public interface IColumnMetaData extends ITableMetaData {

    String getColumnName();

    StringProperty columnNameProperty();

    void setColumnName(String columnName);

    int getDataType();

    IntegerProperty dataTypeProperty();

    void setDataType(int dataType);

    String getDataTypeName();

    StringProperty dataTypeNameProperty();

    void setDataTypeName(String dataTypeName);

    int getColumnSize();

    IntegerProperty columnSizeProperty();

    void setColumnSize(int columnSize);

    int getDecimalDigits();

    IntegerProperty decimalDigitsProperty();

    void setDecimalDigits(int decimalDigits);

    int getNumPrecRadix();

    IntegerProperty numPrecRadixProperty();

    void setNumPrecRadix(int numPrecRadix);

    int getNullAble();

    IntegerProperty nullAbleProperty();

    void setNullAble(int nullAble);

    String getColumnDef();

    StringProperty columnDefProperty();

    void setColumnDef(String columnDef);

    int getCharOctetLength();

    IntegerProperty charOctetLengthProperty();

    void setCharOctetLength(int charOctetLength);

    String getIsNullAble();

    StringProperty isNullAbleProperty();

    void setIsNullAble(String isNullAble);

    String getJavaType();

    StringProperty javaTypeProperty();

    void setJavaType(String javaType);

    String getJavaProperty();

    StringProperty javaPropertyProperty();

    void setJavaProperty(String javaProperty);

    boolean isPrimaryKey();

    BooleanProperty primaryKeyProperty();

    void setPrimaryKey(boolean isPrimaryKey);
}