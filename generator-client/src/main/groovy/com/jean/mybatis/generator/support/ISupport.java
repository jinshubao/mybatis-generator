package com.jean.mybatis.generator.support;

import com.jean.mybatis.generator.constant.DatabaseType;

/**
 * 是否支持数据库
 */
public interface ISupport {

    boolean support(DatabaseType type);
}
