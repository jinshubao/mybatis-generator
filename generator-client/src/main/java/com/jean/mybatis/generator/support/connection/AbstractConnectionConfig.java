package com.jean.mybatis.generator.support.connection;

import com.jean.mybatis.generator.constant.DatabaseType;
import com.jean.mybatis.generator.utils.StringUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author jinshubao
 */
public abstract class AbstractConnectionConfig implements IConnectionConfig {

    protected DatabaseType type;

    protected String host;

    protected Integer port;

    protected String username;

    protected String password;

    protected String tableSchema;

    protected String tableCatalog;

    protected String properties;

    @Override
    public DatabaseType getType() {
        return type;
    }

    public void setType(DatabaseType type) {
        this.type = type;
    }

    @Override
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getTableSchema() {
        return tableSchema;
    }

    public void setTableSchema(String tableSchema) {
        this.tableSchema = tableSchema;
    }

    @Override
    public String getTableCatalog() {
        return tableCatalog;
    }

    public void setTableCatalog(String tableCatalog) {
        this.tableCatalog = tableCatalog;
    }

    @Override
    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }

    @Override
    public Connection getConnection() throws SQLException {
        Properties properties = new Properties();
        properties.putAll(StringUtil.parseMysqlProperties(getProperties()));
        if (username != null) {
            properties.setProperty("user", username);
        }
        if (password != null) {
            properties.setProperty("password", password);
        }
        //设置可以获取remarks信息
        properties.setProperty("remarks", Boolean.toString(true));
        return DriverManager.getConnection(getConnectionUrl(), properties);
    }

    @Override
    public boolean testConnection() throws SQLException {
        return getConnection() != null;
    }

}
