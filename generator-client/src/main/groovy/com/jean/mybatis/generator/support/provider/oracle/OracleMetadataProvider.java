package com.jean.mybatis.generator.support.provider.oracle;

import com.jean.mybatis.generator.constant.DatabaseType;
import com.jean.mybatis.generator.support.meta.ICatalogMetaData;
import com.jean.mybatis.generator.support.meta.IColumnMetaData;
import com.jean.mybatis.generator.support.meta.ISchemaMetaData;
import com.jean.mybatis.generator.support.meta.ITableMetaData;
import com.jean.mybatis.generator.support.provider.AbstractMetadataProvider;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by jinshubao on 2018/2/8.
 */
@Component
public class OracleMetadataProvider extends AbstractMetadataProvider {


    @Override
    protected ICatalogMetaData getCatalogMetaData(ResultSet resultSet) throws SQLException {
        return null;
    }

    @Override
    protected ISchemaMetaData getSchemaMetaData(ResultSet resultSet) throws SQLException {
        return null;
    }

    @Override
    protected ITableMetaData getTableMetaData(ResultSet resultSet) throws SQLException {
        return null;
    }

    @Override
    protected IColumnMetaData getColumnMetaData(ResultSet resultSet) throws SQLException {
        return null;
    }


    @Override
    public boolean support(DatabaseType databaseType) {
        return databaseType == DatabaseType.Oracle;
    }

}
