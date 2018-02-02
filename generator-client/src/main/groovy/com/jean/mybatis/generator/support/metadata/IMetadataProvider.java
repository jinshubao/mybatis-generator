package com.jean.mybatis.generator.support.metadata;

import com.jean.mybatis.generator.support.connection.IConnectionConfig;
import com.jean.mybatis.generator.constant.DatabaseType;
import com.jean.mybatis.generator.support.database.IDatabaseMetadata;
import com.jean.mybatis.generator.support.table.IColumnMetadata;
import com.jean.mybatis.generator.support.table.ITableMetadata;

import java.util.List;

/**
 * Created by jinshubao on 2017/4/9.
 */
public interface IMetadataProvider {

    IConnectionConfig getConnectionConfig();

    void setConnectionConfig(IConnectionConfig config);

    boolean testConnection();

    List<IDatabaseMetadata> getDatabases();

    List<ITableMetadata> getTables(IDatabaseMetadata database);

    List<IColumnMetadata> getColumns(IDatabaseMetadata database, ITableMetadata table);

    boolean isSupport(DatabaseType databaseType);

    String getConnectionURL(IDatabaseMetadata databaseMetadata);

}
