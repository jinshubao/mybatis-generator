package com.jean.mybatis.generator.factory;

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
    private ConnectionConfig connectionConfig;

    public DefaultTaskFactory(Executor executor, IMetaDataProviderManager providerManager) {
        this.executor = executor;
        this.providerManager = providerManager;
    }


    @Override
    public void getCatalogs(Callback<List<CatalogMetaData>> handler, Callback<Throwable> error) {
        IMetadataProvider provider = metadataProvider();
        Task<List<CatalogMetaData>> task = new Task<List<CatalogMetaData>>() {
            @Override
            protected List<CatalogMetaData> call() throws Exception {
                return provider.getCatalogs();
            }
        };
        task.setOnSucceeded(event -> handler.call(task.getValue()));
        task.setOnFailed(event -> error.call(task.getException()));
        executor.execute(task);
    }

    @Override
    public void getSchemas(Callback<List<SchemaMetaData>> handler, Callback<Throwable> error) {
        IMetadataProvider provider = metadataProvider();
        Task<List<SchemaMetaData>> task = new Task<List<SchemaMetaData>>() {
            @Override
            protected List<SchemaMetaData> call() throws Exception {
                return provider.getSchemas();
            }
        };
        task.setOnSucceeded(event -> handler.call(task.getValue()));
        task.setOnFailed(event -> error.call(task.getException()));
        executor.execute(task);
    }


    @Override
    public void getTables(Callback<List<TableMetaData>> handler, Callback<Throwable> error) {
        IMetadataProvider provider = metadataProvider();
        Task<List<TableMetaData>> task = new Task<List<TableMetaData>>() {
            @Override
            protected List<TableMetaData> call() throws Exception {
                return provider.getTables();
            }
        };
        task.setOnSucceeded(event -> handler.call(task.getValue()));
        task.setOnFailed(event -> error.call(task.getException()));
        executor.execute(task);
    }

    @Override
    public void getColumns(String tableNamePattern, Callback<List<ColumnMetaData>> handler, Callback<Throwable> error) {
        IMetadataProvider provider = metadataProvider();
        Task<List<ColumnMetaData>> task = new Task<List<ColumnMetaData>>() {
            @Override
            protected List<ColumnMetaData> call() throws Exception {
                return provider.getColumns(tableNamePattern);
            }
        };
        task.setOnSucceeded(event -> handler.call(task.getValue()));
        task.setOnFailed(event -> error.call(task.getException()));
        executor.execute(task);
    }

    private IMetadataProvider metadataProvider() {
        IMetadataProvider provider = providerManager.getSupportedMetaDataProvider(connectionConfig.getType());
        provider.setConnectionConfig(connectionConfig);
        return provider;
    }

    @Override
    public void setConnectionConfig(ConnectionConfig connectionConfig) {
        this.connectionConfig = connectionConfig;
    }
}
