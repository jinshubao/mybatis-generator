package com.jean.mybatis.generator.support.provider;

import com.jean.mybatis.generator.constant.DatabaseType;
import com.jean.mybatis.generator.support.connection.ConnectionConfig;
import com.jean.mybatis.generator.support.meta.ICatalogMetaData;
import com.jean.mybatis.generator.support.meta.IColumnMetaData;
import com.jean.mybatis.generator.support.meta.ISchemaMetaData;
import com.jean.mybatis.generator.support.meta.ITableMetaData;
import com.jean.mybatis.generator.support.meta.mysql.MySqlColumnMetaData;
import com.jean.mybatis.generator.support.meta.mysql.MySqlCatalogMetaData;
import com.jean.mybatis.generator.support.meta.mysql.MySqlSchemaMetaData;
import com.jean.mybatis.generator.support.meta.mysql.MySqlTableMetaData;
import com.jean.mybatis.generator.support.provider.AbstractMetadataProvider;
import com.jean.mybatis.generator.utils.StringUtil;
import org.springframework.stereotype.Component;

import java.sql.Connection;

/**
 * @author jinshubao
 * @date 2017/4/9
 */
@Component
public class MySqlMetadataProvider extends AbstractMetadataProvider {

    @Override
    protected Class<? extends ICatalogMetaData> getCatalogMetaDataClass() {
        return MySqlCatalogMetaData.class;
    }

    @Override
    protected Class<? extends ISchemaMetaData> getSchemaMetaDataClass() {
        return MySqlSchemaMetaData.class;
    }

    @Override
    protected Class<? extends ITableMetaData> getTableMetaDataClass() {
        return MySqlTableMetaData.class;
    }

    @Override
    protected Class<? extends IColumnMetaData> getColumnMetaDataClass() {
        return MySqlColumnMetaData.class;
    }

    @Override
    public boolean support(DatabaseType databaseType) {
        return databaseType == DatabaseType.MySql;
    }

    @Override
    protected Connection getConnection() throws Exception {
        return super.getConnection();
    }

    @Override
    protected String getConnectionURL() {
        ConnectionConfig config = getConnectionConfig();
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(String.format("jdbc:mysql://%s:%d", config.getHost(), config.getPort()));
        if (StringUtil.isNotBlank(config.getTableCatalog()) || StringUtil.isNotBlank(config.getTableSchema())) {
            urlBuilder.append("/");
            if (StringUtil.isNotBlank(config.getTableCatalog())){
                urlBuilder.append(config.getTableCatalog());
                if (StringUtil.isNotBlank(config.getTableSchema())){
                    urlBuilder.append("/").append(config.getTableSchema());
                }
            }else{
                urlBuilder.append(config.getTableSchema());
            }
        }
        if (StringUtil.isNotBlank(config.getProperties())) {
            urlBuilder.append("?").append(getProperties());
        }
        return urlBuilder.toString();
    }
}
