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

    void setConnectionConfig(ConnectionConfig connectionConfig);

     void getCatalogs(Callback<List<CatalogMetaData>> handler, Callback<Throwable> error);

    void getSchemas(Callback<List<SchemaMetaData>> handler, Callback<Throwable> error);

    void getTables(Callback<List<TableMetaData>> handler, Callback<Throwable> error);

    void getColumns(String tableNamePattern, Callback<List<ColumnMetaData>> handler, Callback<Throwable> error);

    public static interface Callback<T> {
        void call(T value);
    }
}
