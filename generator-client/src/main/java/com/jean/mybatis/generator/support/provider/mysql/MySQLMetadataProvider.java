package com.jean.mybatis.generator.support.provider.mysql;

import com.jean.mybatis.generator.constant.DatabaseType;
import com.jean.mybatis.generator.support.meta.ICatalogMetaData;
import com.jean.mybatis.generator.support.meta.IColumnMetaData;
import com.jean.mybatis.generator.support.meta.ISchemaMetaData;
import com.jean.mybatis.generator.support.meta.ITableMetaData;
import com.jean.mybatis.generator.support.meta.mysql.MySQColumnMetaData;
import com.jean.mybatis.generator.support.meta.mysql.MySQLCatalogMetaData;
import com.jean.mybatis.generator.support.meta.mysql.MySQLSchemaMetaData;
import com.jean.mybatis.generator.support.meta.mysql.MySQLTableMetaData;
import com.jean.mybatis.generator.support.provider.AbstractMetadataProvider;
import org.springframework.stereotype.Component;

/**
 * @author jinshubao
 * @date 2017/4/9
 */
@Component
public class MySQLMetadataProvider extends AbstractMetadataProvider {

    @Override
    protected Class<? extends ICatalogMetaData> getCatalogMetaDataClass() {
        return MySQLCatalogMetaData.class;
    }

    @Override
    protected Class<? extends ISchemaMetaData> getSchemaMetaDataClass() {
        return MySQLSchemaMetaData.class;
    }

    @Override
    protected Class<? extends ITableMetaData> getTableMetaDataClass() {
        return MySQLTableMetaData.class;
    }

    @Override
    protected Class<? extends IColumnMetaData> getColumnMetaDataClass() {
        return MySQColumnMetaData.class;
    }

    @Override
    public boolean support(DatabaseType databaseType) {
        return databaseType == DatabaseType.MySql;
    }

}
