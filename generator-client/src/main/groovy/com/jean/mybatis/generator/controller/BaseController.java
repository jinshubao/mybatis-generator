package com.jean.mybatis.generator.controller;

import com.jean.mybatis.generator.constant.DatabaseType;
import com.jean.mybatis.generator.support.connection.IConnectionConfig;
import com.jean.mybatis.generator.support.connection.MySQLConnectionConfig;
import com.jean.mybatis.generator.support.metadata.IMetadataProvider;
import javafx.fxml.Initializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Map;


/**
 * Created by jinshubao on 2017/4/8.
 */
public abstract class BaseController implements Initializable {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected Collection<IMetadataProvider> metadataProviders;

    public IMetadataProvider chooseMetadataService(DatabaseType databaseType) throws Exception {
        if (metadataProviders != null) {
            for (IMetadataProvider service : metadataProviders) {
                if (service.isSupport(databaseType)) {
                    return service;
                }
            }
        }
        throw new Exception("不支持的数据库类型");
    }


    public static IConnectionConfig getConnectionConfigNewInstance(DatabaseType databaseType) {
        if (databaseType == DatabaseType.MySql) {
            return new MySQLConnectionConfig();
        } else if (databaseType == DatabaseType.Oracle) {

        }
        return null;
    }
}
