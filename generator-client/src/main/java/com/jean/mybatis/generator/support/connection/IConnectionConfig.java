package com.jean.mybatis.generator.support.connection;

import com.jean.mybatis.generator.constant.DatabaseType;
import com.jean.mybatis.generator.support.common.ISupport;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

/**
 * @author jinshubao
 */
public interface IConnectionConfig extends ISupport {

    DatabaseType getType();

    String getHost();

    Integer getPort();

    String getUsername();

    String getPassword();

    String getTableSchema();

    String getTableCatalog();

    String getProperties();

    Connection getConnection() throws SQLException;

    String getConnectionUrl();

    boolean testConnection()throws SQLException;

    boolean supportCatalog();

    boolean supportSchema();

}
