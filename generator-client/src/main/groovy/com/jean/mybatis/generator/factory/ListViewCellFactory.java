package com.jean.mybatis.generator.factory;

import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class ListViewCellFactory {

    private static class CheckBoxListCell<P extends Selectable> extends ListCell<P> {
        private CheckBox checkBox;
        private P ref;

        public CheckBoxListCell(Pos pos) {
            this.checkBox = new CheckBox();
            if (pos != null) {
                setAlignment(pos);
            } else {
                setAlignment(Pos.CENTER_LEFT);
            }
            setContentDisplay(ContentDisplay.LEFT);
        }

        @Override
        protected void updateItem(P item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
                setText(null);
            } else {
                if (item != null) {
                    item.selectedProperty().unbind();
                    if (this.ref != null) {
                        checkBox.selectedProperty().unbindBidirectional(this.ref.selectedProperty());
                    }
                    checkBox.selectedProperty().bindBidirectional(item.selectedProperty());
                    this.ref = item;
                    setGraphic(checkBox);
                    setText(item.toString());
                } else {
                    setText("");
                }
            }
        }
    }

    public static <T extends Selectable> Callback<ListView<T>, ListCell<T>> forListView() {
        return param -> new CheckBoxListCell<>(Pos.CENTER_LEFT);
    }
}
