package com.jean.mybatis.generator.support.provider;

import com.jean.mybatis.generator.support.common.ISupport;
import com.jean.mybatis.generator.support.connection.IConnectionConfig;
import com.jean.mybatis.generator.support.meta.ICatalogMetaData;
import com.jean.mybatis.generator.support.meta.IColumnMetaData;
import com.jean.mybatis.generator.support.meta.ISchemaMetaData;
import com.jean.mybatis.generator.support.meta.ITableMetaData;

import java.util.List;

/**
 * @author jinshubao
 * @date 2017/4/9
 */
public interface IMetadataProvider extends ISupport {

    void setConnectionConfig(IConnectionConfig config);

    IConnectionConfig getConnectionConfig();

    List<ICatalogMetaData> getCatalogs() throws Exception;

    List<ISchemaMetaData> getSchemas() throws Exception;

    List<ITableMetaData> getTables() throws Exception;

    List<IColumnMetaData> getColumns(String tableNamePattern) throws Exception;

}
