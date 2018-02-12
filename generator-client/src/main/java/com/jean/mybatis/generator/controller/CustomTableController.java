package com.jean.mybatis.generator.controller;

import com.jean.mybatis.generator.constant.CommonConstant;
import com.jean.mybatis.generator.support.meta.IColumnMetaData;
import com.jean.mybatis.generator.utils.StringUtil;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;
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
    @SuppressWarnings("unchecked")
    public void initialize(URL location, ResourceBundle resources) {

        this.selectAll.setOnAction(event -> selectAll(this.columnTableView.getItems()));
        this.invertSelect.setOnAction(event -> reverseSelect(this.columnTableView.getItems()));

        int columnIndex = 0;
        ObservableList<TableColumn<IColumnMetaData, ?>> tableColumns = this.columnTableView.getColumns();

        TableColumn<IColumnMetaData, Boolean> column0 = (TableColumn<IColumnMetaData, Boolean>) tableColumns.get(columnIndex++);
        column0.setText(resources.getString("select.text"));
        column0.setCellFactory(CheckBoxTableCell.forTableColumn(column0));
        column0.setCellValueFactory(param -> param.getValue().selectedProperty());

        TableColumn<IColumnMetaData, String> column1 = (TableColumn<IColumnMetaData, String>) tableColumns.get(columnIndex++);
        column1.setText(resources.getString("columnname.text"));
        column1.setCellValueFactory(param -> param.getValue().columnNameProperty());

        TableColumn<IColumnMetaData, String> column2 = (TableColumn<IColumnMetaData, String>) tableColumns.get(columnIndex++);
        column2.setText(resources.getString("jdbctype.text"));
        column2.setCellValueFactory(param -> param.getValue().dataTypeNameProperty());

        StringConverter<String> converter = new StringConverter<String>() {

            @Override
            public String toString(String object) {
                return StringUtil.isBlank(object) ? CommonConstant.DEFAULT : object;
            }

            @Override
            public String fromString(String string) {
                return CommonConstant.DEFAULT.equals(string) ? null : string;
            }
        };

        TableColumn<IColumnMetaData, String> column3 = (TableColumn<IColumnMetaData, String>) tableColumns.get(columnIndex++);
        column3.setText(resources.getString("javatype.text"));
        column3.setCellFactory(ComboBoxTableCell.forTableColumn(converter,
                CommonConstant.JavaType.AUTO,
                CommonConstant.JavaType.STRING,
                CommonConstant.JavaType.INTEGER,
                CommonConstant.JavaType.LONG,
                CommonConstant.JavaType.BIGDECIMAL,
                CommonConstant.JavaType.DOUBLE,
                CommonConstant.JavaType.FLOAT,
                CommonConstant.JavaType.BOOLEAN,
                CommonConstant.JavaType.SHORT,
                CommonConstant.JavaType.BYTE));
        column3.setCellValueFactory(param -> param.getValue().javaTypeProperty());

        TableColumn<IColumnMetaData, String> column4 = (TableColumn<IColumnMetaData, String>) tableColumns.get(columnIndex++);
        column4.setText(resources.getString("javaproperty.text"));
        column4.setCellFactory(TextFieldTableCell.forTableColumn(converter));
        column4.setCellValueFactory(param -> param.getValue().javaPropertyProperty());

        TableColumn<IColumnMetaData, String> column5 = (TableColumn<IColumnMetaData, String>) tableColumns.get(columnIndex);
        column5.setText(resources.getString("remarks.text"));
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
