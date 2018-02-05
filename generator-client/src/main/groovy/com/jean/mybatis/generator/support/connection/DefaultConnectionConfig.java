package com.jean.mybatis.generator.support.connection;

import com.jean.mybatis.generator.constant.DatabaseType;

import java.nio.charset.Charset;
import java.util.Map;

public class DefaultConnectionConfig implements IConnectionConfig {

    private DatabaseType type;

    private String host;

    private Integer port;

    private String username;

    private String password;

    private Charset charset;

    private Map<String, String> properties;

    private boolean savePassword;

    private String databaseName;

    @Override
    public DatabaseType getType() {
        return type;
    }

    @Override
    public void setType(DatabaseType type) {
        this.type = type;
    }

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public Integer getPort() {
        return port;
    }

    @Override
    public void setPort(Integer port) {
        this.port = port;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Charset getCharset() {
        return charset;
    }

    @Override
    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    @Override
    public Map<String, String> getProperties() {
        return properties;
    }

    @Override
    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    @Override
    public boolean isSavePassword() {
        return savePassword;
    }

    @Override
    public void setSavePassword(boolean savePassword) {
        this.savePassword = savePassword;
    }

    @Override
    public String getDatabaseName() {
        return databaseName;
    }

    @Override
    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }
}
