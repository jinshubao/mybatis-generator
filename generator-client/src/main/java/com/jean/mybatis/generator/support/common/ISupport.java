package com.jean.mybatis.generator.support.common;

import com.jean.mybatis.generator.constant.DatabaseType;

/**
 * 是否支持数据库
 *
 * @author jinshubao
 */
public interface ISupport {

    /**
     * 是否支持该数据库
     *
     * @param type
     * @return
     */
    boolean support(DatabaseType type);
}
