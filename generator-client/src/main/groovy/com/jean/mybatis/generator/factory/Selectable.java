package com.jean.mybatis.generator.factory;

import javafx.beans.property.BooleanProperty;

public interface Selectable {

    void setSelected(boolean selected);

    boolean isSelected();

    BooleanProperty selectedProperty();
}
