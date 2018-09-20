package com.jean.mybatis.generator.support.provider;

import com.jean.mybatis.generator.constant.DatabaseType;

/**
 * @author jinshubao
 */
public interface IMetaDataProviderManager {

    /**
     * 获取对应数据库的 MetaDataProvider
     *
     * @param type 数据库类型
     * @return MetaDataProvider
     */
    IMetadataProvider getSupportedMetaDataProvider(DatabaseType type);
}
