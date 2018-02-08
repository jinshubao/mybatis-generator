package com.jean.mybatis.generator.support.connection;

import com.jean.mybatis.generator.utils.StringUtil;

public class MySQLConnectionConfig extends AbstractConnectionConfig {

    @Override
    public String getConnectionUrl() {
        String url = "jdbc:mysql://" + host + ":" + port + "";
        if (StringUtil.isNotBlank(tableCatalog)) {
            url += ("/" + tableCatalog);
        }
        String props = StringUtil.expandProperties(properties);
        return url + "?" + props;
    }

    @Override
    public void setProperties(String properties) {
        setProperties(StringUtil.parseProperties(properties));
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
