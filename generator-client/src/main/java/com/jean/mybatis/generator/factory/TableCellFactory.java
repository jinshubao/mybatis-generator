package com.jean.mybatis.generator.factory;

import com.jean.mybatis.generator.support.common.Selectable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.util.List;

/**
 * @author jinshubao
 */
public class TableCellFactory {


    /**
     * 链接标签
     *
     * @param <S>
     * @param <T>
     */
    private static class HyperlinkTableCell<S extends Selectable, T> extends TableCell<S, T> {

        private final Hyperlink hyperlink;
        private final Callback<S, Object> callback;
        private EventHandler<ActionEvent> eventHandler;
        private S ref;

        private HyperlinkTableCell(String label, Callback<S, Object> callback) {
            this.hyperlink = new Hyperlink(label);
            this.hyperlink.setVisible(false);
            this.callback = callback;
            setAlignment(Pos.CENTER);
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
                hyperlink.setVisited(false);
                ref = getTableView().getItems().get(getTableRow().getIndex());
                eventHandler = event -> {
                    if (callback != null) {
                        callback.call(ref);
                    }
                };
                this.hyperlink.setOnAction(eventHandler);
                this.hyperlink.visibleProperty().bind(ref.selectedProperty());
                setGraphic(hyperlink);
            }
        }
    }

    /**
     * 表格中的连接标签
     *
     * @param label
     * @param callback
     * @param <S>
     * @param <T>
     * @return
     */
    public static <S extends Selectable, T> Callback<TableColumn<S, T>, TableCell<S, T>>
    hyperlinkForTableView(String label, Callback<S, Object> callback) {
        return param -> new TableCellFactory.HyperlinkTableCell<>(label, callback);
    }

    /**
     * 表格中的下拉框
     *
     * @param converter
     * @param items
     * @param editable
     * @param <S>
     * @param <T>
     * @return
     */
    public static <S, T> Callback<TableColumn<S, T>, TableCell<S, T>> comboBoxForTableColumn(
            final StringConverter<T> converter, List<T> items, boolean editable) {
        return list -> {
            ObservableList<T> objects = FXCollections.observableArrayList();
            if (items != null) {
                objects.addAll(items);
            }
            ComboBoxTableCell<S, T> tableCell = new ComboBoxTableCell<>(converter, objects);
            tableCell.setComboBoxEditable(editable);
            return tableCell;
        };
    }
}
