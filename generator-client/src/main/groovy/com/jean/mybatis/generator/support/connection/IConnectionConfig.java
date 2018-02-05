package com.jean.mybatis.generator.support.connection;

import com.jean.mybatis.generator.constant.DatabaseType;
import com.jean.mybatis.generator.support.database.IDatabaseMetadata;

import java.nio.charset.Charset;
import java.util.Map;

public interface IConnectionConfig {

    DatabaseType getType();

    void setType(DatabaseType type);

    String getHost();

    void setHost(String host);

    Integer getPort();

    void setPort(Integer port);

    String getUsername();

    void setUsername(String username);

    String getPassword();

    void setPassword(String password);

    Charset getCharset();

    void setCharset(Charset charset);

    Map<String, String> getProperties();

    void setProperties(Map<String, String> properties);

    boolean isSavePassword();

    void setSavePassword(boolean savePassword);

    String getDatabaseName();

    void setDatabaseName(String databaseName);

}
