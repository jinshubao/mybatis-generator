package com.jean.mybatis.generator.support.connection;

import com.jean.mybatis.generator.constant.DatabaseType;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractConnectionConfig implements IConnectionConfig {

    protected DatabaseType type;

    protected String host;

    protected Integer port;

    protected String username;

    protected String password;

    protected String tableSchema;

    protected String tableCatalog;

    protected Map<String, String> properties = new HashMap<>();

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
    public Map<String, String> getProperties() {
        return properties;
    }

    public abstract void setProperties(String properties);

    public void setProperties(Map<String, String> properties) {
        if (properties != null)
            this.properties.putAll(properties);
    }

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(this.getConnectionUrl(), this.username, this.password);
    }

    @Override
    public boolean testConnection() throws SQLException {
        return getConnection() != null;
    }

}
