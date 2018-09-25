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

    /**
     * 获取连接
     *
     * @param connectionConfig
     * @return
     * @throws Exception
     */
    Connection getConnection(ConnectionConfig connectionConfig) throws Exception;

    /**
     * 测试连接
     *
     * @param connectionConfig
     * @return
     * @throws Exception
     */
    boolean testConnection(ConnectionConfig connectionConfig) throws Exception;

    /**
     * 获取Catalog
     *
     * @param connectionConfig
     * @return
     * @throws Exception
     */
    List<CatalogMetaData> getCatalogs(ConnectionConfig connectionConfig) throws Exception;

    /**
     * 获取Schema
     *
     * @param connectionConfig
     * @return
     * @throws Exception
     */
    List<SchemaMetaData> getSchemas(ConnectionConfig connectionConfig) throws Exception;

    /**
     * 获取table
     *
     * @param connectionConfig
     * @return
     * @throws Exception
     */
    List<TableMetaData> getTables(ConnectionConfig connectionConfig) throws Exception;

    /**
     * 获取column
     *
     * @param connectionConfig
     * @param tableNamePattern
     * @return
     * @throws Exception
     */
    List<ColumnMetaData> getColumns(ConnectionConfig connectionConfig, String tableNamePattern) throws Exception;

    /**
     * 分隔符
     *
     * @return
     */
    String getBeginningDelimiter();

    /**
     * 分隔符
     *
     * @return
     */
    String getEndDelimiter();

}
