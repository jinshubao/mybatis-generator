package com.jean.mybatis.generator.controller;

import com.jean.mybatis.generator.support.common.Selectable;
import com.jean.mybatis.generator.utils.DialogUtil;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ResourceBundle;


/**
 * @author jinshubao
 * @date 2017/4/8
 */
abstract class BaseController implements Initializable {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    void selectAll(ObservableList<? extends Selectable> items) {
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

    void reverseSelect(ObservableList<? extends Selectable> items) {
        if (!items.isEmpty()) {
            for (Selectable item : items) {
                item.setSelected(!item.isSelected());
            }
        }
    }


    void showExceptionDialog(ResourceBundle resources, Throwable e) {
        logger.error(e.getMessage(), e);
        DialogUtil.error(resources.getString("dialog.exception.title"), e);
    }

}
