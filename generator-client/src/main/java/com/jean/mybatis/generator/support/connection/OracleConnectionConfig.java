package com.jean.mybatis.generator.support.connection;

import com.jean.mybatis.generator.constant.DatabaseType;
import com.jean.mybatis.generator.utils.StringUtils;

import java.util.Properties;

/**
 * @author jinshubao
 */
public class OracleConnectionConfig extends ConnectionConfig {
    public OracleConnectionConfig() {
    }

    public OracleConnectionConfig(DatabaseType type) {
        super(type);
    }

    public OracleConnectionConfig(DatabaseType type, String host, Integer port, String user, String password, String tableSchema, String tableCatalog, String properties) {
        super(type, host, port, user, password, tableSchema, tableCatalog, properties);
    }

    @Override
    public String getConnectionURL() {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(String.format("jdbc:oracle:thin:@//%s:%d", this.getHost(), this.getPort()));
        if (StringUtils.hasText(this.getTableCatalog()) || StringUtils.hasText(this.getTableSchema())) {
            urlBuilder.append("/");
            if (StringUtils.hasText(this.getTableCatalog())) {
                urlBuilder.append(this.getTableCatalog());
                if (StringUtils.hasText(this.getTableSchema())) {
                    urlBuilder.append("/").append(this.getTableSchema());
                }
            } else {
                urlBuilder.append(this.getTableSchema());
            }
        }
        return urlBuilder.toString();
    }

    @Override
    public Properties getConnectionProperties() {
        return null;
    }
}
