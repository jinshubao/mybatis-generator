package com.jean.mybatis.generator.support.connection;

import com.jean.mybatis.generator.constant.DatabaseType;
import com.jean.mybatis.generator.utils.StringUtils;

import java.util.Properties;

/**
 * @author jinshubao
 */
public class MySQLConnectionConfig extends ConnectionConfig {

    public MySQLConnectionConfig() {
    }

    public MySQLConnectionConfig(DatabaseType type) {
        super(type);
    }

    public MySQLConnectionConfig(DatabaseType type, String host, Integer port, String user, String password, String tableSchema, String tableCatalog, String properties) {
        super(type, host, port, user, password, tableSchema, tableCatalog, properties);
    }

    @Override
    public String getConnectionURL() {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(String.format("jdbc:mysql://%s:%d", this.getHost(), this.getPort()));
        if (StringUtils.hasText(this.getTableCatalog())) {
            urlBuilder.append("/").append(this.getTableCatalog());
        }
        if (StringUtils.hasText(this.getTableSchema())) {
            urlBuilder.append("/").append(this.getTableSchema());
        }
        return urlBuilder.toString();
    }

    @Override
    public Properties getConnectionProperties() {
        if (!StringUtils.hasText(properties)) {
            return null;
        }
        String[] props = properties.split("&");
        Properties p = new Properties();
        for (String pro : props) {
            if (StringUtils.hasText(pro)) {
                String[] split = pro.split("=");
                if (split.length == 2) {
                    p.put(split[0], split[1]);
                }
            }
        }
        return p;
    }
}
