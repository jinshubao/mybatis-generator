package com.jean.mybatis.generator.factory;

import com.jean.mybatis.generator.support.meta.Selectable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

/**
 * @author jinshubao
 */
public class HyperlinkTableCellFactory {


    private static class HyperlinkTableCell<S extends Selectable, T> extends TableCell<S, T> {

        private final Hyperlink hyperlink;
        private final Callback<S, Object> callback;

        private EventHandler<ActionEvent> eventHandler;
        private S ref;

        private HyperlinkTableCell(String label, Callback<S, Object> callback) {
            this.hyperlink = new Hyperlink(label);
            this.hyperlink.setVisible(false);
            this.callback = callback;
        }

        @Override
        protected void updateItem(T item, boolean empty) {
            super.updateItem(item, empty);
            if (eventHandler != null) {
                this.hyperlink.removeEventHandler(ActionEvent.ACTION, eventHandler);
            }
            hyperlink.visibleProperty().unbind();
            if (empty) {
                setGraphic(null);
                setText(null);
            } else {
                ref = getTableView().getItems().get(getTableRow().getIndex());
                eventHandler = event -> {
                    if (callback != null){
                        callback.call(ref);
                    }
                };
                this.hyperlink.setOnAction(eventHandler);
                this.hyperlink.visibleProperty().bind(ref.selectedProperty());
                setGraphic(hyperlink);
            }
        }
    }

    public static <S extends Selectable, T> Callback<TableColumn<S, T>, TableCell<S, T>> forTableView(String label, Callback<S, Object> callback) {
        return param -> new HyperlinkTableCellFactory.HyperlinkTableCell<S, T>(label, callback);
    }
}
