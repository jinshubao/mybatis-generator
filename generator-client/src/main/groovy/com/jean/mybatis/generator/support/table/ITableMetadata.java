package com.jean.mybatis.generator.support.table;

import com.jean.mybatis.generator.factory.Selectable;
import javafx.beans.property.StringProperty;

public interface ITableMetadata extends Selectable {

    String getName();

    StringProperty nameProperty();

    String getComment();

    StringProperty commentProperty();

    String toString();

}