package com.jean.mybatis.generator.factory;

import com.jean.mybatis.generator.support.connection.ConnectionConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author jinshubao
 */
public class ConnectionFactory {

    private static final String USER = "user";

    private static final String PASSWORD = "password";

    private static final String REMARKS = "remarks";

    /**
     * 获取连接
     *
     * @param url
     * @param user
     * @param password
     * @param properties
     * @return
     */
    public static Connection getConnection(String url, String user, String password, Properties properties) throws SQLException {
        Properties props = new Properties();
        if (properties != null && !properties.isEmpty()) {
            props.putAll(properties);
        }
        if (user != null && !props.containsKey(USER)) {
            props.setProperty(USER, user);
        }
        if (password != null && !props.containsKey(PASSWORD)) {
            props.setProperty(PASSWORD, password);
        }
        //设置可以获取remarks信息
        props.setProperty(REMARKS, Boolean.toString(true));
        return DriverManager.getConnection(url, props);
    }

    public static Connection getConnection(String url, String user, String password) throws SQLException {
        return getConnection(url, user, password, null);
    }

    public static Connection getConnection(ConnectionConfig config) throws SQLException {
        return getConnection(config.getConnectionURL(), config.getUser(), config.getPassword(), null);
    }

    public static boolean testConnection(ConnectionConfig config) throws SQLException {
        Connection connection = null;
        try {
            connection = getConnection(config);
            return config != null;
        } finally {
            close(connection);
        }
    }


    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                //
            }
        }
    }
}
