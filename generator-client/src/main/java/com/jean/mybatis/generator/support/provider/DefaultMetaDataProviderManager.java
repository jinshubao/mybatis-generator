package com.jean.mybatis.generator.support.provider;

import com.jean.mybatis.generator.constant.DatabaseType;
import com.jean.mybatis.generator.exception.DatabaseNotSupportException;

import java.util.List;

/**
 * @author jinshubao
 */
public class DefaultMetaDataProviderManager implements IMetaDataProviderManager {

    private List<IMetadataProvider> metadataProviders;

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
        throw new DatabaseNotSupportException("不支持数据库类型[" + type.name + "]");
    }
}
