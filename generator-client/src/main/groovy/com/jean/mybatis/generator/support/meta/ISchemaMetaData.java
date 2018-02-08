package com.jean.mybatis.generator.support.meta;

import com.jean.mybatis.generator.support.common.INaming;

/**
 * Schema信息
 */
public interface ISchemaMetaData extends INaming {

    String getTableSchema();
}
