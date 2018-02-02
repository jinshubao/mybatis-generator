package com.jean.mybatis.generator.model

import com.jean.mybatis.generator.support.connection.IConnectionConfig

/**
 *
 * Created by jinshubao on 2017/4/9.
 */
class ConnectionItem {

    protected final IConnectionConfig connectionConfig

    ConnectionItem(IConnectionConfig connectionConfig) {
        this.connectionConfig = connectionConfig
    }


    @Override
    String toString() {
        return connectionConfig.host + ":" + connectionConfig.port
    }
}
