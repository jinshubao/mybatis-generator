package com.jean.mybatis.generator.factory;

import com.jean.mybatis.generator.constant.DatabaseType;
import com.jean.mybatis.generator.support.connection.AbstractConnectionConfig;
import com.jean.mybatis.generator.support.connection.MySqlConnectionConfig;
import com.jean.mybatis.generator.support.connection.OracleConnectionConfig;

/**
 * @author jinshubao
 */
public class ConnectionConfigFactory {

    public static AbstractConnectionConfig newInstance(DatabaseType type, String host, Integer port, String username, String password, String properties) {

        AbstractConnectionConfig config = null;
        if (type == DatabaseType.MySql) {
            config = new MySqlConnectionConfig();
        } else if (type == DatabaseType.Oracle) {
            config = new OracleConnectionConfig();
        }
        if (config == null) {
            throw new RuntimeException("暂不支持【" + type.name + "】数据库");
        }
        config.setType(type);
        config.setHost(host);
        config.setPort(port);
        config.setUsername(username);
        config.setPassword(password);
        config.setProperties(properties);
        return config;
    }
}
