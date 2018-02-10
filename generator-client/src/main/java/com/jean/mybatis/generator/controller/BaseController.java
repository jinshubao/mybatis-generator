package com.jean.mybatis.generator.controller;

import com.jean.mybatis.generator.support.meta.Selectable;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * @author jinshubao
 * @date 2017/4/8
 */
public abstract class BaseController implements Initializable {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    protected void selectAll(ObservableList<? extends Selectable> items) {
        if (!items.isEmpty()) {
            boolean selectedAll = true;
            for (Selectable item : items) {
                if (!item.isSelected()) {
                    selectedAll = false;
                    break;
                }
            }
            for (Selectable item : items) {
                item.setSelected(!selectedAll);
            }
        }
    }

    protected void reverseSelect(ObservableList<? extends Selectable> items) {
        if (!items.isEmpty()) {
            for (Selectable item : items) {
                item.setSelected(!item.isSelected());
            }
        }
    }

}
