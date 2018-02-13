package com.jean.mybatis.generator.support.provider;

import com.jean.mybatis.generator.constant.DatabaseType;
import com.jean.mybatis.generator.support.meta.ICatalogMetaData;
import com.jean.mybatis.generator.support.meta.IColumnMetaData;
import com.jean.mybatis.generator.support.meta.ISchemaMetaData;
import com.jean.mybatis.generator.support.meta.ITableMetaData;
import com.jean.mybatis.generator.support.meta.mysql.MySqlColumnMetaData;
import com.jean.mybatis.generator.support.meta.mysql.MySqlCatalogMetaData;
import com.jean.mybatis.generator.support.meta.mysql.MySqlSchemaMetaData;
import com.jean.mybatis.generator.support.meta.mysql.MySqlTableMetaData;
import com.jean.mybatis.generator.support.provider.AbstractMetadataProvider;
import org.springframework.stereotype.Component;

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

}
