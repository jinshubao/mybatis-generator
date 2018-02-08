package com.jean.mybatis.generator.support.provider;

import com.jean.mybatis.generator.support.ISupport;
import com.jean.mybatis.generator.support.connection.IConnectionConfig;
import com.jean.mybatis.generator.support.meta.ICatalogMetaData;
import com.jean.mybatis.generator.support.meta.IColumnMetaData;
import com.jean.mybatis.generator.support.meta.ISchemaMetaData;
import com.jean.mybatis.generator.support.meta.ITableMetaData;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by jinshubao on 2017/4/9.
 */
public interface IMetadataProvider extends ISupport {

    void setConnectionConfig(IConnectionConfig config);

    IConnectionConfig getConnectionConfig();

    List<ICatalogMetaData> getCatalogs() throws SQLException;

    List<ISchemaMetaData> getSchemas() throws SQLException;

    List<ITableMetaData> getTables() throws SQLException;

    List<IColumnMetaData> getColumns(String tableNamePattern) throws SQLException;

}
