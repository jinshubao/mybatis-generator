package com.jean.mybatis.generator.support.connection;

import com.jean.mybatis.generator.utils.StringUtil;

/**
 * @author jinshubao
 */
public class MySQLConnectionConfig extends AbstractConnectionConfig {

    @Override
    public String getConnectionUrl() {
        String url = "jdbc:mysql://" + host + ":" + port + "";
        if (StringUtil.isNotBlank(tableCatalog)) {
            url += ("/" + tableCatalog);
        }
        return url + "?" + getProperties();
    }

    @Override
    public boolean supportCatalog() {
        return true;
    }

    @Override
    public boolean supportSchema() {
        return false;
    }
}
