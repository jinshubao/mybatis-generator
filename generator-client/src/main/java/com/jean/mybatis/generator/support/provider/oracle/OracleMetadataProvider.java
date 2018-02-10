package com.jean.mybatis.generator.support.provider.oracle;

import com.jean.mybatis.generator.constant.DatabaseType;
import com.jean.mybatis.generator.support.meta.ICatalogMetaData;
import com.jean.mybatis.generator.support.meta.IColumnMetaData;
import com.jean.mybatis.generator.support.meta.ISchemaMetaData;
import com.jean.mybatis.generator.support.meta.ITableMetaData;
import com.jean.mybatis.generator.support.provider.AbstractMetadataProvider;
import org.springframework.stereotype.Component;

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
        return null;
    }

    @Override
    protected Class<? extends ISchemaMetaData> getSchemaMetaDataClass() {
        return null;
    }

    @Override
    protected Class<? extends ITableMetaData> getTableMetaDataClass() {
        return null;
    }

    @Override
    protected Class<? extends IColumnMetaData> getColumnMetaDataClass() {
        return null;
    }
}
