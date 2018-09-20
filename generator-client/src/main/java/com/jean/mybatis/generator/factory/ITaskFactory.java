package com.jean.mybatis.generator.factory;

import com.jean.mybatis.generator.support.connection.ConnectionConfig;
import com.jean.mybatis.generator.support.meta.CatalogMetaData;
import com.jean.mybatis.generator.support.meta.ColumnMetaData;
import com.jean.mybatis.generator.support.meta.SchemaMetaData;
import com.jean.mybatis.generator.support.meta.TableMetaData;

import java.util.List;

/**
 * @author jinshubao
 */
public interface ITaskFactory {

    /**
     * 设置连接
     *
     * @param connectionConfig
     */
    void setConnectionConfig(ConnectionConfig connectionConfig);

    /**
     * getCatalogs
     *
     * @param handler
     * @param error
     */
    void getCatalogs(Callback<List<CatalogMetaData>> handler, Callback<Throwable> error);

    /**
     * getSchemas
     *
     * @param handler
     * @param error
     */
    void getSchemas(Callback<List<SchemaMetaData>> handler, Callback<Throwable> error);

    /**
     * getTables
     *
     * @param handler
     * @param error
     */
    void getTables(Callback<List<TableMetaData>> handler, Callback<Throwable> error);

    /**
     * getColumns
     *
     * @param tableNamePattern
     * @param handler
     * @param error
     */
    void getColumns(String tableNamePattern, Callback<List<ColumnMetaData>> handler, Callback<Throwable> error);

    interface Callback<T> {

        /**
         * 回调
         *
         * @param value
         */
        void call(T value);
    }
}
