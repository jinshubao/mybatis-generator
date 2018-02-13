package com.jean.mybatis.generator.support.common;

import com.jean.mybatis.generator.constant.DatabaseType;

/**
 * 是否支持数据库
 * @author jinshubao
 */
public interface ISupport {

    boolean support(DatabaseType type);
}
