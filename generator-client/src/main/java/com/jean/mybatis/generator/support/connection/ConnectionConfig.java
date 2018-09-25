package com.jean.mybatis.generator.support.connection;

import com.jean.mybatis.generator.constant.DatabaseType;
import com.jean.mybatis.generator.utils.StringUtils;

import java.util.Properties;

/**
 * @author jinshubao
 */
public abstract class ConnectionConfig {

    protected DatabaseType type;

    protected String host;

    protected Integer port;

    protected String user;

    protected String password;

    protected String tableSchema;

    protected String tableCatalog;

    protected String properties;


    public ConnectionConfig() {
    }

    public ConnectionConfig(DatabaseType type) {
        this.type = type;
    }

    public ConnectionConfig(DatabaseType type, String host, Integer port, String user, String password,
                            String tableSchema, String tableCatalog, String properties) {
        this.type = type;
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.tableSchema = tableSchema;
        this.tableCatalog = tableCatalog;
        this.properties = properties;
    }

    /**
     * 获取连接字符串
     *
     * @return
     */
    public abstract String getConnectionURL();

    /**
     *
     * @return
     */
    public abstract Properties getConnectionProperties();

    public DatabaseType getType() {
        return type;
    }

    public void setType(DatabaseType type) {
        this.type = type;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTableSchema() {
        return tableSchema;
    }

    public void setTableSchema(String tableSchema) {
        this.tableSchema = tableSchema;
    }

    public String getTableCatalog() {
        return tableCatalog;
    }

    public void setTableCatalog(String tableCatalog) {
        this.tableCatalog = tableCatalog;
    }

    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }
}
