package com.jean.mybatis.generator.support.connection

import com.jean.mybatis.generator.constant.DatabaseType

import java.nio.charset.Charset

abstract class AbstractConnectionConfig implements IConnectionConfig {

    DatabaseType databaseType

    String host

    Integer port

    String username

    String password

    Charset charset

    Map<String, String> properties

    boolean savePassword

    DatabaseType getDatabaseType() {
        return databaseType
    }

    void setDatabaseType(DatabaseType databaseType) {
        this.databaseType = databaseType
    }

    String getHost() {
        return host
    }

    void setHost(String host) {
        this.host = host
    }

    Integer getPort() {
        return port
    }

    void setPort(Integer port) {
        this.port = port
    }

    String getUsername() {
        return username
    }

    void setUsername(String username) {
        this.username = username
    }

    String getPassword() {
        return password
    }

    void setPassword(String password) {
        this.password = password
    }

    Charset getCharset() {
        return charset
    }

    void setCharset(Charset charset) {
        this.charset = charset
    }


    boolean isSavePassword() {
        return savePassword
    }

    void setSavePassword(boolean savePassword) {
        this.savePassword = savePassword
    }

    Map<String, String> getProperties() {
        return properties
    }

    void setProperties(Map<String, String> properties) {
        this.properties = properties
    }

    protected String expandProperties() {
        def properties = []
        if (charset) {
            properties << "useUnicode=true"
            properties << "characterEncoding=${charset.name()}"
        }
        if (this.properties) {
            this.properties.each { key, value ->
                properties << key + "=" + value
            }
        }
        properties.join("&")
    }

}
