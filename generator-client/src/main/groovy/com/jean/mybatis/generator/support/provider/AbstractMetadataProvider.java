package com.jean.mybatis.generator.support.provider;

import com.jean.mybatis.generator.constant.TableType;
import com.jean.mybatis.generator.support.connection.IConnectionConfig;
import com.jean.mybatis.generator.support.meta.ICatalogMetaData;
import com.jean.mybatis.generator.support.meta.IColumnMetaData;
import com.jean.mybatis.generator.support.meta.ISchemaMetaData;
import com.jean.mybatis.generator.support.meta.ITableMetaData;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMetadataProvider implements IMetadataProvider {

    protected IConnectionConfig connectionConfig;

    @Override
    public void setConnectionConfig(IConnectionConfig config) {
        this.connectionConfig = config;
    }

    @Override
    public IConnectionConfig getConnectionConfig() {
        return connectionConfig;
    }

    protected DatabaseMetaData getDatabaseMetaData() throws SQLException {
        return getConnection().getMetaData();
    }

    protected Connection getConnection() throws SQLException {
        return connectionConfig.getConnection();
    }

    @Override
    public List<ICatalogMetaData> getCatalogs() throws SQLException {
        DatabaseMetaData metaData = getDatabaseMetaData();
        ResultSet rs = metaData.getCatalogs();
        List<ICatalogMetaData> catalogs = new ArrayList<>();
        while (rs.next()) {
            catalogs.add(getCatalogMetaData(rs));
        }
        return catalogs;
    }

    @Override
    public List<ISchemaMetaData> getSchemas() throws SQLException {
        DatabaseMetaData metaData = getDatabaseMetaData();
        ResultSet rs = metaData.getSchemas(connectionConfig.getTableCatalog(), null);
        List<ISchemaMetaData> schemas = new ArrayList<>();
        while (rs.next()) {
            schemas.add(getSchemaMetaData(rs));
        }
        return schemas;
    }


    @Override
    public List<ITableMetaData> getTables() throws SQLException {
        DatabaseMetaData metaData = getDatabaseMetaData();
        ResultSet rs = metaData.getTables(connectionConfig.getTableCatalog(),
                connectionConfig.getTableSchema(),
                null,
                new String[]{TableType.TABLE.getValue()});
        List<ITableMetaData> tables = new ArrayList<>();
        while (rs.next()) {
            tables.add(getTableMetaData(rs));
        }
        return tables;
    }


    @Override
    public List<IColumnMetaData> getColumns(String tableNamePattern) throws SQLException {
        DatabaseMetaData metaData = getDatabaseMetaData();
        ResultSet rs = metaData.getColumns(connectionConfig.getTableCatalog(),
                connectionConfig.getTableSchema(),
                tableNamePattern,
                null);
        List<IColumnMetaData> columns = new ArrayList<>();
        while (rs.next()) {
            columns.add(getColumnMetaData(rs));
        }
        return columns;
    }

    protected abstract ICatalogMetaData getCatalogMetaData(ResultSet resultSet) throws SQLException;

    protected abstract ISchemaMetaData getSchemaMetaData(ResultSet resultSet) throws SQLException;

    protected abstract ITableMetaData getTableMetaData(ResultSet resultSet) throws SQLException;

    protected abstract IColumnMetaData getColumnMetaData(ResultSet resultSet) throws SQLException;

}
