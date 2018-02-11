package com.jean.mybatis.generator.controller;

import com.jean.mybatis.generator.support.meta.IColumnMetaData;
import com.jean.mybatis.generator.support.provider.IMetadataProvider;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import org.mybatis.generator.config.ColumnOverride;
import org.mybatis.generator.config.IgnoredColumn;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author jinshubao
 */
@Controller
public class CustomTableController extends BaseController {

    @FXML
    private TableView<IColumnMetaData> columnTableView;
    @FXML
    private Hyperlink selectAll;
    @FXML
    private Hyperlink invertSelect;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.selectAll.setOnAction(event -> selectAll(this.columnTableView.getItems()));
        this.invertSelect.setOnAction(event -> reverseSelect(this.columnTableView.getItems()));
        TableColumn<IColumnMetaData, Boolean> column0 = (TableColumn<IColumnMetaData, Boolean>) this.columnTableView.getColumns().get(0);
        column0.setCellFactory(CheckBoxTableCell.forTableColumn(column0));
        column0.setCellValueFactory(param -> param.getValue().selectedProperty());

        TableColumn<IColumnMetaData, String> column1 = (TableColumn<IColumnMetaData, String>) this.columnTableView.getColumns().get(1);
        column1.setCellValueFactory(param -> param.getValue().columnNameProperty());

        TableColumn<IColumnMetaData, String> column2 = (TableColumn<IColumnMetaData, String>) this.columnTableView.getColumns().get(2);
        column2.setCellValueFactory(param -> param.getValue().dataTypeNameProperty());

        TableColumn<IColumnMetaData, String> column3 = (TableColumn<IColumnMetaData, String>) this.columnTableView.getColumns().get(3);
        column3.setCellFactory(ComboBoxTableCell.forTableColumn("Integer", "Long", "Double"));
        column3.setCellValueFactory(param -> param.getValue().javaTypeProperty());

        TableColumn<IColumnMetaData, String> column4 = (TableColumn<IColumnMetaData, String>) this.columnTableView.getColumns().get(4);
        column4.setCellFactory(TextFieldTableCell.forTableColumn());
        column4.setCellValueFactory(param -> param.getValue().javaPropertyProperty());

        TableColumn<IColumnMetaData, String> column5 = (TableColumn<IColumnMetaData, String>) this.columnTableView.getColumns().get(5);
        column5.setCellValueFactory(param -> param.getValue().remarksProperty());

    }

    public void initColumns(List<IColumnMetaData> columns) {
        this.columnTableView.getItems().clear();
        this.columnTableView.getItems().addAll(columns);
    }

    public List<IColumnMetaData> getColumns() {
        return new ArrayList<>(this.columnTableView.getItems());
    }

}
