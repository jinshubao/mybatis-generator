package com.jean.mybatis.generator.support.provider;

import com.jean.mybatis.generator.constant.DatabaseType;
import org.springframework.stereotype.Component;

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
    public String getBeginningDelimiter() {
        return "`";
    }

    @Override
    public String getEndDelimiter() {
        return getBeginningDelimiter();
    }


}
