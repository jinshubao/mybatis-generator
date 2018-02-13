package com.jean.mybatis.generator.support.connection;

import com.jean.mybatis.generator.constant.DatabaseType;

/**
 * @author jinshubao
 */
public class OracleConnectionConfig extends AbstractConnectionConfig {

    @Override
    public String getConnectionUrl() {
        return null;
    }

    @Override
    public boolean supportCatalog() {
        return false;
    }

    @Override
    public boolean supportSchema() {
        return false;
    }

    @Override
    public boolean support(DatabaseType type) {
        return type == DatabaseType.Oracle;
    }
}
