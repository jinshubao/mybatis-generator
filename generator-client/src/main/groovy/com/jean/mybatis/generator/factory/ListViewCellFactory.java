package com.jean.mybatis.generator.factory;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class ListViewCellFactory {

    private static class CheckBoxListCell<P extends Selectable> extends ListCell<P> {
        private final CheckBox checkBox;

        public CheckBoxListCell(Pos pos) {
            this.checkBox = new CheckBox();
//            this.checkBox.selectedProperty().bind(selectedProperty());
            if (pos != null) {
                setAlignment(pos);
            } else {
                setAlignment(Pos.CENTER_LEFT);
            }
            setContentDisplay(ContentDisplay.LEFT);
            setGraphic(null);
            this.checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    getItem().setSelected(newValue);
                }
            });
        }

        @Override
        protected void updateItem(P item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
                setText(null);
            } else {
                if (item != null) {
                    checkBox.setSelected(item.isSelected());
                    setText(item.toString());
                } else {
                    checkBox.setSelected(false);
                    setText("");
                }
                setGraphic(checkBox);
            }
        }
    }

    public static <T extends Selectable> Callback<ListView<T>, ListCell<T>> forListView(Pos pos) {
        return param -> new CheckBoxListCell<>(pos);
    }
}
