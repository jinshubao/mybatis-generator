package com.jean.mybatis.generator.model

import com.jean.mybatis.generator.support.connection.IConnectionConfig
import com.jean.mybatis.generator.support.database.IDatabaseMetadata

/**
 *
 * Created by jinshubao on 2017/4/9.
 */
class DatabaseItem extends ConnectionItem {

    IDatabaseMetadata databaseMetadata

    DatabaseItem(IConnectionConfig config, IDatabaseMetadata metadata) {
        super(config)
        this.databaseMetadata = metadata
    }

    @Override
    String toString() {
        return databaseMetadata.getName()
    }
}
