package com.jean.mybatis.generator.support.provider;

import com.jean.mybatis.generator.constant.DatabaseType;

import java.util.List;

public class DefaultMetaDataProviderManager implements IMetaDataProviderManager {

    protected final List<IMetadataProvider> metadataProviders;

    public DefaultMetaDataProviderManager(List<IMetadataProvider> metadataProviders) {
        this.metadataProviders = metadataProviders;
    }

    @Override
    public IMetadataProvider getMetaDataProvider(DatabaseType type) {
        for (IMetadataProvider provider : metadataProviders) {
            if (provider.support(type)) {
                return provider;
            }
        }
        return null;
    }
}
