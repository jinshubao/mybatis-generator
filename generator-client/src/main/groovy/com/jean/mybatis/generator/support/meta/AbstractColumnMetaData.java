package com.jean.mybatis.generator.support.meta;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AbstractColumnMetaData implements IColumnMetaData {

    private StringProperty tableSchema = new SimpleStringProperty();

    private StringProperty tableCatalog = new SimpleStringProperty();

    private StringProperty tableName = new SimpleStringProperty();


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
     * 列描述
     */
    protected StringProperty remarks = new SimpleStringProperty();
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

    @Override
    public String getTableSchema() {
        return tableSchema.get();
    }

    public StringProperty tableSchemaProperty() {
        return tableSchema;
    }

    public void setTableSchema(String tableSchema) {
        this.tableSchema.set(tableSchema);
    }

    @Override
    public String getTableCatalog() {
        return tableCatalog.get();
    }

    public StringProperty tableCatalogProperty() {
        return tableCatalog;
    }

    public void setTableCatalog(String tableCatalog) {
        this.tableCatalog.set(tableCatalog);
    }

    @Override
    public String getTableName() {
        return tableName.get();
    }

    public StringProperty tableNameProperty() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName.set(tableName);
    }

    @Override
    public String getColumnName() {
        return columnName.get();
    }

    public StringProperty columnNameProperty() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName.set(columnName);
    }

    @Override
    public int getDataType() {
        return dataType.get();
    }

    public IntegerProperty dataTypeProperty() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType.set(dataType);
    }

    @Override
    public String getDataTypeName() {
        return dataTypeName.get();
    }

    public StringProperty dataTypeNameProperty() {
        return dataTypeName;
    }

    public void setDataTypeName(String dataTypeName) {
        this.dataTypeName.set(dataTypeName);
    }

    @Override
    public int getColumnSize() {
        return columnSize.get();
    }

    public IntegerProperty columnSizeProperty() {
        return columnSize;
    }

    public void setColumnSize(int columnSize) {
        this.columnSize.set(columnSize);
    }

    @Override
    public int getDecimalDigits() {
        return decimalDigits.get();
    }

    public IntegerProperty decimalDigitsProperty() {
        return decimalDigits;
    }

    public void setDecimalDigits(int decimalDigits) {
        this.decimalDigits.set(decimalDigits);
    }

    @Override
    public int getNumPrecRadix() {
        return numPrecRadix.get();
    }

    public IntegerProperty numPrecRadixProperty() {
        return numPrecRadix;
    }

    public void setNumPrecRadix(int numPrecRadix) {
        this.numPrecRadix.set(numPrecRadix);
    }

    @Override
    public int getNullAble() {
        return nullAble.get();
    }

    public IntegerProperty nullAbleProperty() {
        return nullAble;
    }

    public void setNullAble(int nullAble) {
        this.nullAble.set(nullAble);
    }

    @Override
    public String getRemarks() {
        return remarks.get();
    }

    public StringProperty remarksProperty() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks.set(remarks);
    }

    @Override
    public String getColumnDef() {
        return columnDef.get();
    }

    public StringProperty columnDefProperty() {
        return columnDef;
    }

    public void setColumnDef(String columnDef) {
        this.columnDef.set(columnDef);
    }

    @Override
    public int getCharOctetLength() {
        return charOctetLength.get();
    }

    public IntegerProperty charOctetLengthProperty() {
        return charOctetLength;
    }

    public void setCharOctetLength(int charOctetLength) {
        this.charOctetLength.set(charOctetLength);
    }

    @Override
    public String getIsNullAble() {
        return isNullAble.get();
    }

    public StringProperty isNullAbleProperty() {
        return isNullAble;
    }

    public void setIsNullAble(String isNullAble) {
        this.isNullAble.set(isNullAble);
    }
}
