package com.jean.mybatis.generator.support.provider;

import com.jean.mybatis.generator.constant.DatabaseType;
import com.jean.mybatis.generator.support.connection.ConnectionConfig;
import com.jean.mybatis.generator.support.meta.ICatalogMetaData;
import com.jean.mybatis.generator.support.meta.IColumnMetaData;
import com.jean.mybatis.generator.support.meta.ISchemaMetaData;
import com.jean.mybatis.generator.support.meta.ITableMetaData;
import com.jean.mybatis.generator.support.meta.oracle.OracleCatalogMetaData;
import com.jean.mybatis.generator.support.meta.oracle.OracleColumnMetaData;
import com.jean.mybatis.generator.support.meta.oracle.OracleSchemaMetaData;
import com.jean.mybatis.generator.support.meta.oracle.OracleTableMetaData;
import com.jean.mybatis.generator.utils.StringUtil;
import org.springframework.stereotype.Component;

import java.sql.Connection;

/**
 * @author jinshubao
 * @date 2018/2/8
 */
@Component
public class OracleMetadataProvider extends AbstractMetadataProvider {

    @Override
    public boolean support(DatabaseType databaseType) {
        return databaseType == DatabaseType.Oracle;
    }

    @Override
    protected Class<? extends ICatalogMetaData> getCatalogMetaDataClass() {
        return OracleCatalogMetaData.class;
    }

    @Override
    protected Class<? extends ISchemaMetaData> getSchemaMetaDataClass() {
        return OracleSchemaMetaData.class;
    }

    @Override
    protected Class<? extends ITableMetaData> getTableMetaDataClass() {
        return OracleTableMetaData.class;
    }

    @Override
    protected Class<? extends IColumnMetaData> getColumnMetaDataClass() {
        return OracleColumnMetaData.class;
    }

    @Override
    public String getConnectionURL() {
        ConnectionConfig config = getConnectionConfig();
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(String.format("jdbc:oracle:thin:@//%s:%d", config.getHost(), config.getPort()));
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
        return urlBuilder.toString();
    }
}
