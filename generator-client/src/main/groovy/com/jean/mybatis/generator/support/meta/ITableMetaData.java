package com.jean.mybatis.generator.support.meta;

import com.jean.mybatis.generator.factory.Selectable;
import com.jean.mybatis.generator.support.common.INaming;

public interface ITableMetaData extends Selectable, INaming {

    String getTableSchema();

    String getTableCatalog();

    String getTableName();

    String getRemarks();

    String getTableType();

}