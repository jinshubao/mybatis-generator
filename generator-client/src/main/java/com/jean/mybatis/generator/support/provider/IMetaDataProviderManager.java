package com.jean.mybatis.generator.support.provider;

import com.jean.mybatis.generator.constant.DatabaseType;
import com.jean.mybatis.generator.support.connection.ConnectionConfig;

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

    /**
     * 返回新连接配置
     *
     * @param type
     * @return
     */
    ConnectionConfig newConnectionConfig(DatabaseType type);
}
