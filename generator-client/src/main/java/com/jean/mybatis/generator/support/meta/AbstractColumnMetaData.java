package com.jean.mybatis.generator.support.meta;

import javafx.beans.property.*;

/**
 * @author jinshubao
 */
public abstract class AbstractColumnMetaData extends AbstractTableMetaData implements IColumnMetaData {

    protected StringProperty columnName = new SimpleStringProperty();

    /**
     * 对应的java.sql.Types的SQL类型(列类型ID)
     */
    protected IntegerProperty dataType = new SimpleIntegerProperty();
    /**
     * java.sql.Types类型名称(列类型名称)
     */
    protected StringProperty dataTypeName = new SimpleStringProperty();
    /**
     * 列大小
     */
    protected IntegerProperty columnSize = new SimpleIntegerProperty();
    /**
     * 小数位数
     */
    protected IntegerProperty decimalDigits = new SimpleIntegerProperty();

    protected IntegerProperty numPrecRadix = new SimpleIntegerProperty();
    /**
     * 是否允许为null
     * 0 (columnNoNulls) - 该列不允许为空
     * 1 (columnNullable) - 该列允许为空
     * 2 (columnNullableUnknown) - 不确定该列是否为空
     */
    protected IntegerProperty nullAble = new SimpleIntegerProperty();

    /**
     * 默认值
     */
    protected StringProperty columnDef = new SimpleStringProperty();


    /**
     * 对于 char 类型，该长度是列中的最大字节数
     */
    protected IntegerProperty charOctetLength = new SimpleIntegerProperty();
    /**
     * ISO规则用来确定某一列的是否可为空(等同于NULLABLE的值:[ 0:'YES'; 1:'NO'; 2:''; ])
     * YES -- 该列可以有空值;
     * NO -- 该列不能为空;
     * 空字符串--- 不知道该列是否可为空
     */
    protected StringProperty isNullAble = new SimpleStringProperty();

    protected StringProperty javaType = new SimpleStringProperty();

    protected StringProperty javaProperty = new SimpleStringProperty();

    protected BooleanProperty primaryKey = new SimpleBooleanProperty(false);

    @Override
    public String getColumnName() {
        return columnName.get();
    }

    @Override
    public StringProperty columnNameProperty() {
        return columnName;
    }

    @Override
    public void setColumnName(String columnName) {
        this.columnName.set(columnName);
    }

    @Override
    public int getDataType() {
        return dataType.get();
    }

    @Override
    public IntegerProperty dataTypeProperty() {
        return dataType;
    }

    @Override
    public void setDataType(int dataType) {
        this.dataType.set(dataType);
    }

    @Override
    public String getDataTypeName() {
        return dataTypeName.get();
    }

    @Override
    public StringProperty dataTypeNameProperty() {
        return dataTypeName;
    }

    @Override
    public void setDataTypeName(String dataTypeName) {
        this.dataTypeName.set(dataTypeName);
    }

    @Override
    public int getColumnSize() {
        return columnSize.get();
    }

    @Override
    public IntegerProperty columnSizeProperty() {
        return columnSize;
    }

    @Override
    public void setColumnSize(int columnSize) {
        this.columnSize.set(columnSize);
    }

    @Override
    public int getDecimalDigits() {
        return decimalDigits.get();
    }

    @Override
    public IntegerProperty decimalDigitsProperty() {
        return decimalDigits;
    }

    @Override
    public void setDecimalDigits(int decimalDigits) {
        this.decimalDigits.set(decimalDigits);
    }

    @Override
    public int getNumPrecRadix() {
        return numPrecRadix.get();
    }

    @Override
    public IntegerProperty numPrecRadixProperty() {
        return numPrecRadix;
    }

    @Override
    public void setNumPrecRadix(int numPrecRadix) {
        this.numPrecRadix.set(numPrecRadix);
    }

    @Override
    public int getNullAble() {
        return nullAble.get();
    }

    @Override
    public IntegerProperty nullAbleProperty() {
        return nullAble;
    }

    @Override
    public void setNullAble(int nullAble) {
        this.nullAble.set(nullAble);
    }

    @Override
    public String getColumnDef() {
        return columnDef.get();
    }

    @Override
    public StringProperty columnDefProperty() {
        return columnDef;
    }

    @Override
    public void setColumnDef(String columnDef) {
        this.columnDef.set(columnDef);
    }

    @Override
    public int getCharOctetLength() {
        return charOctetLength.get();
    }

    @Override
    public IntegerProperty charOctetLengthProperty() {
        return charOctetLength;
    }

    @Override
    public void setCharOctetLength(int charOctetLength) {
        this.charOctetLength.set(charOctetLength);
    }

    @Override
    public String getIsNullAble() {
        return isNullAble.get();
    }

    @Override
    public StringProperty isNullAbleProperty() {
        return isNullAble;
    }

    @Override
    public void setIsNullAble(String isNullAble) {
        this.isNullAble.set(isNullAble);
    }

    @Override
    public String getJavaProperty() {
        return javaProperty.get();
    }

    @Override
    public StringProperty javaPropertyProperty() {
        return javaProperty;
    }

    @Override
    public void setJavaProperty(String javaProperty) {
        this.javaProperty.set(javaProperty);
    }

    @Override
    public String getJavaType() {
        return javaType.get();
    }

    @Override
    public StringProperty javaTypeProperty() {
        return javaType;
    }

    @Override
    public void setJavaType(String javaType) {
        this.javaType.set(javaType);
    }

    @Override
    public boolean isPrimaryKey() {
        return primaryKey.get();
    }

    @Override
    public BooleanProperty primaryKeyProperty() {
        return primaryKey;
    }

    @Override
    public void setPrimaryKey(boolean primaryKey) {
        this.primaryKey.set(primaryKey);
    }
}
