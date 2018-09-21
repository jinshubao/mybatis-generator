package com.jean.mybatis.generator.support.provider;

import com.jean.mybatis.generator.constant.DatabaseType;
import com.jean.mybatis.generator.support.connection.ConnectionConfig;
import com.jean.mybatis.generator.utils.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * @author jinshubao
 * @date 2017/4/9
 */
@Component
public class MySqlMetadataProvider extends AbstractMetadataProvider {

    @Override
    public boolean support(DatabaseType databaseType) {
        return databaseType == DatabaseType.MySql;
    }

    @Override
    protected Properties getConnectionProperties() {
        String properties = getConnectionConfig().getProperties();
        if (!StringUtils.hasText(properties)) {
            return null;
        }
        String[] props = properties.split("&");
        Properties p = new Properties();
        for (String pro : props) {
            if (StringUtils.hasText(pro)) {
                String[] split = pro.split("=");
                if (split.length == 2) {
                    p.put(split[0], split[1]);
                } else {
                    logger.warn("连接属性[{}]无效，忽略该属性", pro);
                }
            }
        }
        return p;
    }


    @Override
    public String getConnectionURL() {
        ConnectionConfig config = getConnectionConfig();
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(String.format("jdbc:mysql://%s:%d", config.getHost(), config.getPort()));
        if (StringUtils.hasText(config.getTableCatalog()) || StringUtils.hasText(config.getTableSchema())) {
            urlBuilder.append("/");
            if (StringUtils.hasText(config.getTableCatalog())) {
                urlBuilder.append(config.getTableCatalog());
                if (StringUtils.hasText(config.getTableSchema())) {
                    urlBuilder.append("/").append(config.getTableSchema());
                }
            } else {
                urlBuilder.append(config.getTableSchema());
            }
        }
        return urlBuilder.toString();
    }

    @Override
    public String getBeginningDelimiter() {
        return "`";
    }

    @Override
    public String getEndDelimiter() {
        return getBeginningDelimiter();
    }
}
