package com.jean.mybatis.generator.support.connection

import com.jean.mybatis.generator.constant.DatabaseType
import com.jean.mybatis.generator.support.database.IDatabaseMetadata

class MySQLConnectionConfig extends AbstractConnectionConfig {

    @Override
    String getConnectionURL(IDatabaseMetadata metadata) {
        def url = "jdbc:mysql://${host}:${port}"
        if (metadata) {
            url += "/${metadata.getName()}"
        }
        def props = expandProperties()
        return url + "?" + props
    }

    @Override
    boolean isSupport(DatabaseType databaseType) {
        return DatabaseType.MySql == databaseType
    }
}
