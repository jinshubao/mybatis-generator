package com.jean.mybatis.generator.support.meta;

import javafx.beans.property.BooleanProperty;

/**
 * @author jinshubao
 */
public interface Selectable {

    void setSelected(boolean selected);

    boolean isSelected();

    BooleanProperty selectedProperty();
}
