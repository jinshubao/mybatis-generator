package com.jean.mybatis.generator.support.provider;

import com.jean.mybatis.generator.constant.DatabaseType;
import org.springframework.stereotype.Component;

/**
 * @author jinshubao
 * @date 2018/2/8
 */
@Component
public class OracleMetadataProvider extends AbstractMetadataProvider {

    @Override
    public boolean support(DatabaseType databaseType) {
        return databaseType == DatabaseType.Oracle;
    }

    @Override
    public String getBeginningDelimiter() {
        return "\"";
    }

    @Override
    public String getEndDelimiter() {
        return getBeginningDelimiter();
    }
}
