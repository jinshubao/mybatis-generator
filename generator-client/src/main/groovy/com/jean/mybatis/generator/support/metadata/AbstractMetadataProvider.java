package com.jean.mybatis.generator.support.metadata;

import com.jean.mybatis.generator.support.connection.IConnectionConfig;

abstract class AbstractMetadataProvider implements IMetadataProvider {

    protected IConnectionConfig connectionConfig;

    @Override
    public IConnectionConfig getConnectionConfig() {
        return this.connectionConfig;
    }

    @Override
    public void setConnectionConfig(IConnectionConfig config) {
        this.connectionConfig = config;
    }
}
