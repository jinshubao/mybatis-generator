package com.jean.mybatis.generator.support.meta;

import javafx.beans.property.*;

/**
 * @author jinshubao
 */
public class ColumnMetaData extends TableMetaData {

    private StringProperty columnName = new SimpleStringProperty();

    /**
     * 对应的java.sql.Types的SQL类型(列类型ID)
     */
    private IntegerProperty dataType = new SimpleIntegerProperty();
    /**
     * java.sql.Types类型名称(列类型名称)
     */
    private StringProperty dataTypeName = new SimpleStringProperty();
    /**
     * 列大小
     */
    private IntegerProperty columnSize = new SimpleIntegerProperty();
    /**
     * 小数位数
     */
    private IntegerProperty decimalDigits = new SimpleIntegerProperty();

    private IntegerProperty numPrecRadix = new SimpleIntegerProperty();
    /**
     * 是否允许为null
     * 0 (columnNoNulls) - 该列不允许为空
     * 1 (columnNullable) - 该列允许为空
     * 2 (columnNullableUnknown) - 不确定该列是否为空
     */
    private IntegerProperty nullAble = new SimpleIntegerProperty();

    /**
     * 默认值
     */
    private StringProperty columnDef = new SimpleStringProperty();


    /**
     * 对于 char 类型，该长度是列中的最大字节数
     */
    private IntegerProperty charOctetLength = new SimpleIntegerProperty();
    /**
     * ISO规则用来确定某一列的是否可为空(等同于NULLABLE的值:[ 0:'YES'; 1:'NO'; 2:''; ])
     * YES -- 该列可以有空值;
     * NO -- 该列不能为空;
     * 空字符串--- 不知道该列是否可为空
     */
    private StringProperty isNullAble = new SimpleStringProperty();

    private StringProperty javaType = new SimpleStringProperty();

    private StringProperty javaProperty = new SimpleStringProperty();

    private BooleanProperty primaryKey = new SimpleBooleanProperty(false);


    public String getColumnName() {
        return columnName.get();
    }

    public StringProperty columnNameProperty() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName.set(columnName);
    }

    public int getDataType() {
        return dataType.get();
    }

    public IntegerProperty dataTypeProperty() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType.set(dataType);
    }

    public String getDataTypeName() {
        return dataTypeName.get();
    }

    public StringProperty dataTypeNameProperty() {
        return dataTypeName;
    }

    public void setDataTypeName(String dataTypeName) {
        this.dataTypeName.set(dataTypeName);
    }

    public int getColumnSize() {
        return columnSize.get();
    }

    public IntegerProperty columnSizeProperty() {
        return columnSize;
    }

    public void setColumnSize(int columnSize) {
        this.columnSize.set(columnSize);
    }

    public int getDecimalDigits() {
        return decimalDigits.get();
    }

    public IntegerProperty decimalDigitsProperty() {
        return decimalDigits;
    }

    public void setDecimalDigits(int decimalDigits) {
        this.decimalDigits.set(decimalDigits);
    }

    public int getNumPrecRadix() {
        return numPrecRadix.get();
    }

    public IntegerProperty numPrecRadixProperty() {
        return numPrecRadix;
    }

    public void setNumPrecRadix(int numPrecRadix) {
        this.numPrecRadix.set(numPrecRadix);
    }

    public int getNullAble() {
        return nullAble.get();
    }

    public IntegerProperty nullAbleProperty() {
        return nullAble;
    }

    public void setNullAble(int nullAble) {
        this.nullAble.set(nullAble);
    }

    public String getColumnDef() {
        return columnDef.get();
    }

    public StringProperty columnDefProperty() {
        return columnDef;
    }

    public void setColumnDef(String columnDef) {
        this.columnDef.set(columnDef);
    }

    public int getCharOctetLength() {
        return charOctetLength.get();
    }

    public IntegerProperty charOctetLengthProperty() {
        return charOctetLength;
    }

    public void setCharOctetLength(int charOctetLength) {
        this.charOctetLength.set(charOctetLength);
    }

    public String getIsNullAble() {
        return isNullAble.get();
    }

    public StringProperty isNullAbleProperty() {
        return isNullAble;
    }

    public void setIsNullAble(String isNullAble) {
        this.isNullAble.set(isNullAble);
    }

    public String getJavaType() {
        return javaType.get();
    }

    public StringProperty javaTypeProperty() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType.set(javaType);
    }

    public String getJavaProperty() {
        return javaProperty.get();
    }

    public StringProperty javaPropertyProperty() {
        return javaProperty;
    }

    public void setJavaProperty(String javaProperty) {
        this.javaProperty.set(javaProperty);
    }

    public boolean isPrimaryKey() {
        return primaryKey.get();
    }

    public BooleanProperty primaryKeyProperty() {
        return primaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        this.primaryKey.set(primaryKey);
    }
}