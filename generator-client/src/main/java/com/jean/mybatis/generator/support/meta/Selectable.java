package com.jean.mybatis.generator.support.meta;

import javafx.beans.property.BooleanProperty;

/**
 * 可选中
 *
 * @author jinshubao
 */
public interface Selectable {

    /**
     * 设置选中状态
     *
     * @param selected
     */
    void setSelected(boolean selected);

    /**
     * 是否选中
     *
     * @return
     */
    boolean isSelected();

    /**
     * property
     *
     * @return
     */
    BooleanProperty selectedProperty();
}
