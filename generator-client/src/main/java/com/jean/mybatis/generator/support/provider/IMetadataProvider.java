package com.jean.mybatis.generator.support.provider;

import com.jean.mybatis.generator.support.common.ISupport;
import com.jean.mybatis.generator.support.connection.ConnectionConfig;
import com.jean.mybatis.generator.support.meta.CatalogMetaData;
import com.jean.mybatis.generator.support.meta.ColumnMetaData;
import com.jean.mybatis.generator.support.meta.SchemaMetaData;
import com.jean.mybatis.generator.support.meta.TableMetaData;

import java.sql.Connection;
import java.util.List;

/**
 * @author jinshubao
 * @date 2017/4/9
 */
public interface IMetadataProvider extends ISupport {

    String getConnectionURL();

    Connection getConnection() throws Exception;

    boolean testConnection() throws Exception;

    void setConnectionConfig(ConnectionConfig config);

    ConnectionConfig getConnectionConfig();

    List<CatalogMetaData> getCatalogs() throws Exception;

    List<SchemaMetaData> getSchemas() throws Exception;

    List<TableMetaData> getTables() throws Exception;

    List<ColumnMetaData> getColumns(String tableNamePattern) throws Exception;

    String getBeginningDelimiter();

    String getEndDelimiter();

}
