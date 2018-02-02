package com.jean.mybatis.generator.support.table

import com.jean.mybatis.generator.factory.Selectable

interface ITableMetadata extends Selectable {

    String getName()

    String getComment()

}