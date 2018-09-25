package com.jean.mybatis.generator.factory;

import com.jean.mybatis.generator.constant.DatabaseType;
import com.jean.mybatis.generator.support.connection.ConnectionConfig;
import com.jean.mybatis.generator.support.meta.CatalogMetaData;
import com.jean.mybatis.generator.support.meta.ColumnMetaData;
import com.jean.mybatis.generator.support.meta.SchemaMetaData;
import com.jean.mybatis.generator.support.meta.TableMetaData;
import com.jean.mybatis.generator.support.provider.IMetaDataProviderManager;
import com.jean.mybatis.generator.support.provider.IMetadataProvider;
import javafx.concurrent.Task;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * @author jinshubao
 */
public class DefaultTaskFactory implements ITaskFactory {

    private Executor executor;
    private IMetaDataProviderManager providerManager;

    public DefaultTaskFactory(Executor executor, IMetaDataProviderManager providerManager) {
        this.executor = executor;
        this.providerManager = providerManager;
    }

    @Override
    public void getCatalogs(ConnectionConfig connectionConfig, Callback<List<CatalogMetaData>> handler, Callback<Throwable> error) {
        IMetadataProvider provider = metadataProvider(connectionConfig.getType());
        Task<List<CatalogMetaData>> task = new Task<List<CatalogMetaData>>() {
            @Override
            protected List<CatalogMetaData> call() throws Exception {
                return provider.getCatalogs(connectionConfig);
            }
        };
        task.setOnSucceeded(event -> handler.call(task.getValue()));
        task.setOnFailed(event -> error.call(task.getException()));
        executor.execute(task);
    }

    @Override
    public void getSchemas(ConnectionConfig connectionConfig, Callback<List<SchemaMetaData>> handler, Callback<Throwable> error) {
        IMetadataProvider provider = metadataProvider(connectionConfig.getType());
        Task<List<SchemaMetaData>> task = new Task<List<SchemaMetaData>>() {
            @Override
            protected List<SchemaMetaData> call() throws Exception {
                return provider.getSchemas(connectionConfig);
            }
        };
        task.setOnSucceeded(event -> handler.call(task.getValue()));
        task.setOnFailed(event -> error.call(task.getException()));
        executor.execute(task);
    }


    @Override
    public void getTables(ConnectionConfig connectionConfig,Callback<List<TableMetaData>> handler, Callback<Throwable> error) {
        IMetadataProvider provider = metadataProvider(connectionConfig.getType());
        Task<List<TableMetaData>> task = new Task<List<TableMetaData>>() {
            @Override
            protected List<TableMetaData> call() throws Exception {
                return provider.getTables(connectionConfig);
            }
        };
        task.setOnSucceeded(event -> handler.call(task.getValue()));
        task.setOnFailed(event -> error.call(task.getException()));
        executor.execute(task);
    }

    @Override
    public void getColumns(ConnectionConfig connectionConfig,String tableNamePattern, Callback<List<ColumnMetaData>> handler, Callback<Throwable> error) {
        IMetadataProvider provider = metadataProvider(connectionConfig.getType());
        Task<List<ColumnMetaData>> task = new Task<List<ColumnMetaData>>() {
            @Override
            protected List<ColumnMetaData> call() throws Exception {
                return provider.getColumns(connectionConfig, tableNamePattern);
            }
        };
        task.setOnSucceeded(event -> handler.call(task.getValue()));
        task.setOnFailed(event -> error.call(task.getException()));
        executor.execute(task);
    }

    private IMetadataProvider metadataProvider(DatabaseType type) {
        return providerManager.getSupportedMetaDataProvider(type);
    }
}
