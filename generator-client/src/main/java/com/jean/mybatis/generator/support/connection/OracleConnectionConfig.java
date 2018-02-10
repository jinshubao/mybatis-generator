package com.jean.mybatis.generator.support.connection;

public class OracleConnectionConfig extends AbstractConnectionConfig {
    @Override
    public String getConnectionUrl() {
        return null;
    }

    @Override
    public void setProperties(String properties) {
        //TODO
    }

    @Override
    public boolean supportCatalog() {
        return false;
    }

    @Override
    public boolean supportSchema() {
        return false;
    }
}
