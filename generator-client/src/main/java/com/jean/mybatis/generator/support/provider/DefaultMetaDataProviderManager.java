package com.jean.mybatis.generator.support.provider;

import com.jean.mybatis.generator.constant.DatabaseType;
import com.jean.mybatis.generator.support.connection.ConnectionConfig;
import com.jean.mybatis.generator.support.connection.MySQLConnectionConfig;
import com.jean.mybatis.generator.support.connection.OracleConnectionConfig;

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
    public IMetadataProvider getSupportedMetaDataProvider(DatabaseType type) {
        for (IMetadataProvider provider : metadataProviders) {
            if (provider.support(type)) {
                return provider;
            }
        }
        throw new RuntimeException("暂不支持数据库[" + type.name + "]");
    }


    @Override
    public ConnectionConfig newConnectionConfig(DatabaseType type) {
        if (type == DatabaseType.MySql) {
            return new MySQLConnectionConfig(type);
        } else if (type == DatabaseType.Oracle) {
            return new OracleConnectionConfig(type);
        }
        throw new RuntimeException("暂不支持数据库[" + type.name + "]");
    }
}
