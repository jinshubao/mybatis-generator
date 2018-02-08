package com.jean.mybatis.generator.support.connection;

import com.jean.mybatis.generator.constant.DatabaseType;

public class ConnectionConfigFactory {

    public static AbstractConnectionConfig newInstance(DatabaseType type, String host, Integer port, String username, String password, String properties) {

        AbstractConnectionConfig config = null;
        if (type == DatabaseType.MySql) {
            config = new MySQLConnectionConfig();
        } else if (type == DatabaseType.Oracle) {
            config = new OracleConnectionConfig();
        }
        if (config != null) {
            config.setType(type);
            config.setHost(host);
            config.setPort(port);
            config.setUsername(username);
            config.setPassword(password);
            config.setProperties(properties);
            return config;
        }
        throw new RuntimeException("暂不支持【" + type.name + "】数据库");
    }
}
