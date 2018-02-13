package com.jean.mybatis.generator.support.provider;

import com.jean.mybatis.generator.constant.DatabaseType;

/**
 * @author jinshubao
 */
public interface IMetaDataProviderManager {

    IMetadataProvider getMetaDataProvider(DatabaseType type);
}
