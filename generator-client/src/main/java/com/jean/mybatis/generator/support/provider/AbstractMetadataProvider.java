package com.jean.mybatis.generator.support.provider;

import com.jean.mybatis.generator.constant.CommonConstant;
import com.jean.mybatis.generator.constant.TableType;
import com.jean.mybatis.generator.support.connection.ConnectionConfig;
import com.jean.mybatis.generator.support.meta.CatalogMetaData;
import com.jean.mybatis.generator.support.meta.ColumnMetaData;
import com.jean.mybatis.generator.support.meta.SchemaMetaData;
import com.jean.mybatis.generator.support.meta.TableMetaData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author jinshubao
 */
public abstract class AbstractMetadataProvider implements IMetadataProvider {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String USER = "user";

    private static final String PASSWORD = "password";

    private static final String REMARKS = "remarks";

    private ConnectionConfig connectionConfig;

    @Override
    public void setConnectionConfig(ConnectionConfig config) {
        this.connectionConfig = config;
    }

    @Override
    public ConnectionConfig getConnectionConfig() {
        return connectionConfig;
    }

    @Override
    public Connection getConnection() throws SQLException, ClassNotFoundException {
        Properties props = new Properties();
        Properties properties = getConnectionProperties();
        if (properties != null && !properties.isEmpty()) {
            props.putAll(properties);
        }
        if (connectionConfig.getUser() != null && !props.containsKey(USER)) {
            props.setProperty(USER, connectionConfig.getUser());
        }
        if (connectionConfig.getPassword() != null && !props.containsKey(PASSWORD)) {
            props.setProperty(PASSWORD, connectionConfig.getPassword());
        }
        Class.forName(connectionConfig.getType().driverClass);
        //设置可以获取remarks信息
        props.setProperty(REMARKS, Boolean.toString(true));
        return DriverManager.getConnection(getConnectionURL(), props);
    }

    @Override
    public List<CatalogMetaData> getCatalogs() throws Exception {
        Connection connection = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            DatabaseMetaData metaData = connection.getMetaData();
            rs = metaData.getCatalogs();
            List<CatalogMetaData> catalogs = new ArrayList<>();
            while (rs.next()) {
                catalogs.add(getCatalogMetaData(rs));
            }
            return catalogs;
        } finally {
            close(connection, rs);
        }
    }

    @Override
    public List<SchemaMetaData> getSchemas() throws Exception {
        Connection connection = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            DatabaseMetaData metaData = connection.getMetaData();
            rs = metaData.getSchemas(connectionConfig.getTableCatalog(), null);
            List<SchemaMetaData> schemas = new ArrayList<>();
            while (rs.next()) {
                schemas.add(getSchemaMetaData(rs));
            }
            return schemas;
        } finally {
            close(connection, rs);
        }
    }


    @Override
    public List<TableMetaData> getTables() throws Exception {
        Connection connection = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            DatabaseMetaData metaData = connection.getMetaData();
            rs = metaData.getTables(connectionConfig.getTableCatalog(),
                    connectionConfig.getTableSchema(),
                    null,
                    new String[]{TableType.TABLE.getValue()});
            List<TableMetaData> tables = new ArrayList<>();
            while (rs.next()) {
                TableMetaData tableMetaData = getTableMetaData(rs);
                ResultSet primaryKeys = metaData.getPrimaryKeys(connectionConfig.getTableCatalog(), connectionConfig.getTableSchema(), tableMetaData.getTableName());
                if (primaryKeys.next()) {
                    tableMetaData.setPrimaryKeyColumn(primaryKeys.getString(CommonConstant.MetaDataType.COLUMN_NAME));
                    close(primaryKeys);
                }
                tables.add(tableMetaData);
            }

            return tables;
        } finally {
            close(connection, rs);
        }
    }

    @Override
    public List<ColumnMetaData> getColumns(String tableNamePattern) throws Exception {
        Connection connection = null;
        ResultSet rs = null;
        ResultSet primaryKeys = null;
        try {
            connection = getConnection();
            DatabaseMetaData metaData = connection.getMetaData();
            rs = metaData.getColumns(connectionConfig.getTableCatalog(),
                    connectionConfig.getTableSchema(),
                    tableNamePattern,
                    null);
            List<ColumnMetaData> columns = new ArrayList<>();
            while (rs.next()) {
                columns.add(getColumnMetaData(rs));
            }
            primaryKeys = metaData.getPrimaryKeys(connectionConfig.getTableCatalog(), connectionConfig.getTableSchema(), tableNamePattern);
            List<String> names = new ArrayList<>();
            while (primaryKeys.next()) {
                names.add(primaryKeys.getString(CommonConstant.MetaDataType.COLUMN_NAME));
            }
            for (ColumnMetaData column : columns) {
                for (String name : names) {
                    column.setPrimaryKey(column.getColumnName().equals(name));
                }
            }
            return columns;
        } finally {
            close(primaryKeys);
            close(connection, rs);
        }
    }

    CatalogMetaData getCatalogMetaData(ResultSet resultSet) throws Exception {
        //表类别（可能为空）
        String tableCat = resultSet.getString(CommonConstant.MetaDataType.TABLE_CAT);
        CatalogMetaData catalogMetaData = new CatalogMetaData();
        catalogMetaData.setTableCatalog(tableCat);
        return catalogMetaData;
    }

    SchemaMetaData getSchemaMetaData(ResultSet resultSet) throws Exception {
        //表类别（可能为空）
        String tableCat = resultSet.getString(CommonConstant.MetaDataType.TABLE_CAT);
        //表模式（可能为空）,在oracle中获取的是命名空间,其它数据库未知
        String tableSchema = resultSet.getString(CommonConstant.MetaDataType.TABLE_SCHEM);
        SchemaMetaData schemaMetaData = new SchemaMetaData();
        schemaMetaData.setTableCatalog(tableCat);
        schemaMetaData.setTableSchema(tableSchema);
        return schemaMetaData;
    }

    TableMetaData getTableMetaData(ResultSet resultSet) throws Exception {
        //表类别（可能为空）
        String tableCat = resultSet.getString(CommonConstant.MetaDataType.TABLE_CAT);
        //表模式（可能为空）,在oracle中获取的是命名空间,其它数据库未知
        String tableSchema = resultSet.getString(CommonConstant.MetaDataType.TABLE_SCHEM);
        //表名
        String tableName = resultSet.getString(CommonConstant.MetaDataType.TABLE_NAME);
        //表类型,典型的类型是 "TABLE"、"VIEW"、"SYSTEM TABLE"、"GLOBAL TEMPORARY"、"LOCAL TEMPORARY"、"ALIAS" 和 "SYNONYM"。
        String tableType = resultSet.getString(CommonConstant.MetaDataType.TABLE_TYPE);
        //表备注
        String remarks = resultSet.getString(CommonConstant.MetaDataType.REMARKS);
        TableMetaData data = new TableMetaData();
        data.setTableCatalog(tableCat);
        data.setTableSchema(tableSchema);
        data.setTableName(tableName);
        data.setTableType(tableType);
        data.setRemarks(remarks);
        return data;
    }

    ColumnMetaData getColumnMetaData(ResultSet resultSet) throws Exception {
        //表类别（可能为空）
        String tableCat = resultSet.getString(CommonConstant.MetaDataType.TABLE_CAT);
        //表模式（可能为空）,在oracle中获取的是命名空间,其它数据库未知
        String tableSchema = resultSet.getString(CommonConstant.MetaDataType.TABLE_SCHEM);
        //表名
        String tableName = resultSet.getString(CommonConstant.MetaDataType.TABLE_NAME);
        //列名
        String columnName = resultSet.getString(CommonConstant.MetaDataType.COLUMN_NAME);
        //对应的java.sql.Types的SQL类型(列类型ID)
        int dataType = resultSet.getInt(CommonConstant.MetaDataType.DATA_TYPE);
        //java.sql.Types类型名称(列类型名称)
        String dataTypeName = resultSet.getString(CommonConstant.MetaDataType.TYPE_NAME);
        //列大小
        int columnSize = resultSet.getInt(CommonConstant.MetaDataType.COLUMN_SIZE);
        //小数位数
        int decimalDigits = resultSet.getInt(CommonConstant.MetaDataType.DECIMAL_DIGITS);
        //基数（通常是10或2） --未知
        int numPrecRadix = resultSet.getInt(CommonConstant.MetaDataType.NUM_PREC_RADIX);
        //是否允许为null
        int nullAble = resultSet.getInt(CommonConstant.MetaDataType.NULLABLE);
        //列描述
        String remarks = resultSet.getString(CommonConstant.MetaDataType.REMARKS);
        //默认值
        String columnDef = resultSet.getString(CommonConstant.MetaDataType.COLUMN_DEF);
        // 对于 char 类型，该长度是列中的最大字节数
        int charOctetLength = resultSet.getInt(CommonConstant.MetaDataType.CHAR_OCTET_LENGTH);
        /*
         * YES,NO,""
         */
        String isNullAble = resultSet.getString(CommonConstant.MetaDataType.IS_NULLABLE);
        ColumnMetaData data = new ColumnMetaData();
        data.setTableCatalog(tableCat);
        data.setTableSchema(tableSchema);
        data.setTableName(tableName);
        data.setColumnName(columnName);
        data.setDataType(dataType);
        data.setDataTypeName(dataTypeName);
        data.setColumnSize(columnSize);
        data.setDecimalDigits(decimalDigits);
        data.setNumPrecRadix(numPrecRadix);
        data.setNullAble(nullAble);
        data.setRemarks(remarks);
        data.setColumnDef(columnDef);
        data.setCharOctetLength(charOctetLength);
        data.setIsNullAble(isNullAble);
        return data;
    }

    void close(Connection connection, ResultSet resultSet) {
        close(connection);
        close(resultSet);
    }

    void close(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }

    }

    void close(ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public boolean testConnection() throws Exception {
        Connection connection = null;
        try {
            connection = getConnection();
            return connection != null;
        } finally {
            close(connection);
        }
    }


    protected Properties getConnectionProperties() {
        return null;
    }

}
