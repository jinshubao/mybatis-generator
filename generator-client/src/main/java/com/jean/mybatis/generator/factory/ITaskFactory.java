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
     * getCatalogs
     *
     * @param connectionConfig
     * @param handler
     * @param error
     */
    void getCatalogs(ConnectionConfig connectionConfig, Callback<List<CatalogMetaData>> handler, Callback<Throwable> error);

    /**
     * getSchemas
     *
     * @param connectionConfig
     * @param handler
     * @param error
     */
    void getSchemas(ConnectionConfig connectionConfig, Callback<List<SchemaMetaData>> handler, Callback<Throwable> error);

    /**
     * getTables
     *
     * @param connectionConfig
     * @param handler
     * @param error
     */
    void getTables(ConnectionConfig connectionConfig, Callback<List<TableMetaData>> handler, Callback<Throwable> error);

    /**
     * getColumns
     *
     * @param connectionConfig
     * @param tableNamePattern
     * @param handler
     * @param error
     */
    void getColumns(ConnectionConfig connectionConfig, String tableNamePattern, Callback<List<ColumnMetaData>> handler, Callback<Throwable> error);

    interface Callback<T> {

        /**
         * 回调
         *
         * @param value
         */
        void call(T value);
    }
}
