package com.jean.mybatis.generator.support.provider;

import com.jean.mybatis.generator.constant.TableType;
import com.jean.mybatis.generator.support.connection.ConnectionConfig;
import com.jean.mybatis.generator.support.meta.ICatalogMetaData;
import com.jean.mybatis.generator.support.meta.IColumnMetaData;
import com.jean.mybatis.generator.support.meta.ISchemaMetaData;
import com.jean.mybatis.generator.support.meta.ITableMetaData;
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
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

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
    public Connection getConnection() throws SQLException {
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
        //设置可以获取remarks信息
        props.setProperty(REMARKS, Boolean.toString(true));
        return DriverManager.getConnection(getConnectionURL(), props);
    }

    @Override
    public List<ICatalogMetaData> getCatalogs() throws Exception {
        Connection connection = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            DatabaseMetaData metaData = connection.getMetaData();
            rs = metaData.getCatalogs();
            List<ICatalogMetaData> catalogs = new ArrayList<>();
            while (rs.next()) {
                catalogs.add(getCatalogMetaData(rs));
            }
            return catalogs;
        } finally {
            close(connection, rs);
        }
    }

    @Override
    public List<ISchemaMetaData> getSchemas() throws Exception {
        Connection connection = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            DatabaseMetaData metaData = connection.getMetaData();
            rs = metaData.getSchemas(connectionConfig.getTableCatalog(), null);
            List<ISchemaMetaData> schemas = new ArrayList<>();
            while (rs.next()) {
                schemas.add(getSchemaMetaData(rs));
            }
            return schemas;
        } finally {
            close(connection, rs);
        }
    }


    @Override
    public List<ITableMetaData> getTables() throws Exception {
        Connection connection = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            DatabaseMetaData metaData = connection.getMetaData();
            rs = metaData.getTables(connectionConfig.getTableCatalog(),
                    connectionConfig.getTableSchema(),
                    null,
                    new String[]{TableType.TABLE.getValue()});
            List<ITableMetaData> tables = new ArrayList<>();
            while (rs.next()) {
                tables.add(getTableMetaData(rs));
            }
            return tables;
        } finally {
            close(connection, rs);
        }
    }

    @Override
    public List<IColumnMetaData> getColumns(String tableNamePattern) throws Exception {
        Connection connection = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            DatabaseMetaData metaData = connection.getMetaData();
            rs = metaData.getColumns(connectionConfig.getTableCatalog(),
                    connectionConfig.getTableSchema(),
                    tableNamePattern,
                    null);
            List<IColumnMetaData> columns = new ArrayList<>();
            while (rs.next()) {
                columns.add(getColumnMetaData(rs));
            }
            return columns;
        } finally {
            close(connection, rs);
        }
    }

    protected ICatalogMetaData getCatalogMetaData(ResultSet resultSet) throws Exception {
        //表类别（可能为空）
        String tableCat = resultSet.getString("TABLE_CAT");
        ICatalogMetaData catalogMetaData = getCatalogMetaDataClass().newInstance();
        catalogMetaData.setTableCatalog(tableCat);
        return catalogMetaData;
    }

    protected ISchemaMetaData getSchemaMetaData(ResultSet resultSet) throws Exception {
        //表类别（可能为空）
        String tableCat = resultSet.getString("TABLE_CAT");
        //表模式（可能为空）,在oracle中获取的是命名空间,其它数据库未知
        String tableSchema = resultSet.getString("TABLE_SCHEM");
        ISchemaMetaData schemaMetaData = getSchemaMetaDataClass().newInstance();
        schemaMetaData.setTableCatalog(tableCat);
        schemaMetaData.setTableSchema(tableSchema);
        return schemaMetaData;
    }

    protected ITableMetaData getTableMetaData(ResultSet resultSet) throws Exception {
        //表类别（可能为空）
        String tableCat = resultSet.getString("TABLE_CAT");
        //表模式（可能为空）,在oracle中获取的是命名空间,其它数据库未知
        String tableSchema = resultSet.getString("TABLE_SCHEM");
        //表名
        String tableName = resultSet.getString("TABLE_NAME");
        //表类型,典型的类型是 "TABLE"、"VIEW"、"SYSTEM TABLE"、"GLOBAL TEMPORARY"、"LOCAL TEMPORARY"、"ALIAS" 和 "SYNONYM"。
        String tableType = resultSet.getString("TABLE_TYPE");
        //表备注
        String remarks = resultSet.getString("REMARKS");
        ITableMetaData data = getTableMetaDataClass().newInstance();
        data.setTableCatalog(tableCat);
        data.setTableSchema(tableSchema);
        data.setTableName(tableName);
        data.setTableType(tableType);
        data.setRemarks(remarks);
        return data;
    }

    protected IColumnMetaData getColumnMetaData(ResultSet resultSet) throws Exception {
        //表类别（可能为空）
        String tableCat = resultSet.getString("TABLE_CAT");
        //表模式（可能为空）,在oracle中获取的是命名空间,其它数据库未知
        String tableSchema = resultSet.getString("TABLE_SCHEM");
        //表名
        String tableName = resultSet.getString("TABLE_NAME");
        //列名
        String columnName = resultSet.getString("COLUMN_NAME");
        //对应的java.sql.Types的SQL类型(列类型ID)
        int dataType = resultSet.getInt("DATA_TYPE");
        //java.sql.Types类型名称(列类型名称)
        String dataTypeName = resultSet.getString("TYPE_NAME");
        //列大小
        int columnSize = resultSet.getInt("COLUMN_SIZE");
        //小数位数
        int decimalDigits = resultSet.getInt("DECIMAL_DIGITS");
        //基数（通常是10或2） --未知
        int numPrecRadix = resultSet.getInt("NUM_PREC_RADIX");
        //是否允许为null
        int nullAble = resultSet.getInt("NULLABLE");
        //列描述
        String remarks = resultSet.getString("REMARKS");
        //默认值
        String columnDef = resultSet.getString("COLUMN_DEF");
        // 对于 char 类型，该长度是列中的最大字节数
        int charOctetLength = resultSet.getInt("CHAR_OCTET_LENGTH");
        /*
         * YES,NO,""
         */
        String isNullAble = resultSet.getString("IS_NULLABLE");
        IColumnMetaData data = getColumnMetaDataClass().newInstance();
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

    protected void close(Connection connection, ResultSet resultSet) throws SQLException {
        close(connection);
        close(resultSet);
    }

    protected void close(Connection connection) throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    protected void close(ResultSet resultSet) throws SQLException {
        if (resultSet != null) {
            resultSet.close();
        }
    }

    @Override
    public boolean testConnection() throws SQLException {
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

    protected abstract Class<? extends ICatalogMetaData> getCatalogMetaDataClass();

    protected abstract Class<? extends ISchemaMetaData> getSchemaMetaDataClass();

    protected abstract Class<? extends ITableMetaData> getTableMetaDataClass();

    protected abstract Class<? extends IColumnMetaData> getColumnMetaDataClass();

}
