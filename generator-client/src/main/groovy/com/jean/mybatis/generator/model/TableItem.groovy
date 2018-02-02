package com.jean.mybatis.generator.model

import com.jean.mybatis.generator.support.connection.IConnectionConfig
import com.jean.mybatis.generator.support.database.IDatabaseMetadata
import com.jean.mybatis.generator.support.table.ITableMetadata

/**
 *
 * Created by jinshubao on 2017/4/9.
 */
class TableItem extends DatabaseItem {

    ITableMetadata tableMetadata

    TableItem(IConnectionConfig connectionConfig, IDatabaseMetadata databaseMetadata, ITableMetadata tableMetadata) {
        super(connectionConfig, databaseMetadata)
        this.tableMetadata = tableMetadata
    }

    @Override
    String toString() {
        return tableMetadata.getName()
    }
}
