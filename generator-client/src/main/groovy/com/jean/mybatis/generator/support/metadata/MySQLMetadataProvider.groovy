package com.jean.mybatis.generator.support.metadata

import com.jean.mybatis.generator.constant.DatabaseType
import com.jean.mybatis.generator.support.database.DataBaseMetadata
import com.jean.mybatis.generator.support.database.IDatabaseMetadata
import com.jean.mybatis.generator.support.table.IColumnMetadata
import com.jean.mybatis.generator.support.table.ITableMetadata
import com.jean.mybatis.generator.support.table.TableMetadata
import groovy.sql.Sql
import org.springframework.stereotype.Service

/**
 *
 * Created by jinshubao on 2017/4/9.
 */
@Service
class MySQLMetadataProvider extends AbstractMetadataProvider {

    protected Sql getSql(IDatabaseMetadata database) {
        def url = getConnectionURL(database)
        return Sql.newInstance(url, connectionConfig.username, connectionConfig.password, connectionConfig.databaseType.driverClass)
    }

    @Override
    List<ITableMetadata> getDatabases() {
        def sql = getSql(null)
        def list = []
        sql.eachRow("SELECT SCHEMA_NAME FROM `information_schema`.`SCHEMATA`") {
            list << new DataBaseMetadata(name: it.SCHEMA_NAME, comment: "")
        }
        return list
    }

    @Override
    List<IColumnMetadata> getColumns(IDatabaseMetadata database, ITableMetadata table) {
        def sql = getSql(database)
        return null
    }

    @Override
    boolean testConnection() {
        def sql = getSql(null)
        return sql != null
    }

    @Override
    List<ITableMetadata> getTables(IDatabaseMetadata database) {
        if (database) {
            def sql = getSql(null)
            def list = []
            sql.eachRow("SELECT TABLE_NAME FROM `INFORMATION_SCHEMA`.`TABLES` WHERE TABLE_SCHEMA=?", [database.name]) {
                list << new TableMetadata(name: it.TABLE_NAME, comment: "")
            }
            return list
        }
        return []
    }

    @Override
    String getConnectionURL(IDatabaseMetadata metadata) {
        return this.connectionConfig.getConnectionURL(metadata)
    }

    @Override
    boolean isSupport(DatabaseType databaseType) {
        return databaseType == DatabaseType.MySql
    }
}
