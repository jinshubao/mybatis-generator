package com.jean.mybatis.generator.controller;

import com.jean.mybatis.generator.constant.CommonConstant;
import com.jean.mybatis.generator.factory.TableCellFactory;
import com.jean.mybatis.generator.support.meta.ColumnMetaData;
import com.jean.mybatis.generator.utils.StringUtils;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author jinshubao
 */
@Controller
public class CustomTableController extends BaseController {

    @FXML
    private TableView<ColumnMetaData> columnTableView;
    @FXML
    private Hyperlink selectAll;
    @FXML
    private Hyperlink invertSelection;


    @Override
    @SuppressWarnings("unchecked")
    public void initialize(URL location, ResourceBundle resources) {

        this.selectAll.setText(resources.getString("selectAll.text"));
        this.selectAll.setOnAction(event -> selectAll(this.columnTableView.getItems()));
        this.invertSelection.setText(resources.getString("invertSelection.text"));
        this.invertSelection.setOnAction(event -> reverseSelect(this.columnTableView.getItems()));

        int columnIndex = 0;
        ObservableList<TableColumn<ColumnMetaData, ?>> tableColumns = this.columnTableView.getColumns();

        TableColumn<ColumnMetaData, Boolean> selectColumn = (TableColumn<ColumnMetaData, Boolean>) tableColumns.get(columnIndex++);
        selectColumn.setText(resources.getString("select.text"));
        selectColumn.setCellFactory(CheckBoxTableCell.forTableColumn(selectColumn));
        selectColumn.setCellValueFactory(param -> param.getValue().selectedProperty());

        TableColumn<ColumnMetaData, String> nameColumn = (TableColumn<ColumnMetaData, String>) tableColumns.get(columnIndex++);
        nameColumn.setText(resources.getString("column.name.text"));
        nameColumn.setCellValueFactory(param -> param.getValue().columnNameProperty());

        TableColumn<ColumnMetaData, String> jdbcTypeColumn = (TableColumn<ColumnMetaData, String>) tableColumns.get(columnIndex++);
        jdbcTypeColumn.setText(resources.getString("jdbcType.text"));
        jdbcTypeColumn.setCellValueFactory(param -> param.getValue().dataTypeNameProperty());

        StringConverter<String> converter = new StringConverter<String>() {

            @Override
            public String toString(String object) {
                return !StringUtils.hasText(object) ? CommonConstant.DEFAULT : object;
            }

            @Override
            public String fromString(String string) {
                return CommonConstant.DEFAULT.equals(string) ? null : string;
            }
        };

        TableColumn<ColumnMetaData, String> javaTypeColumn = (TableColumn<ColumnMetaData, String>) tableColumns.get(columnIndex++);
        javaTypeColumn.setText(resources.getString("javaType.text"));
        javaTypeColumn.setCellFactory(TableCellFactory.comboBoxForTableColumn(converter,
                Arrays.asList(CommonConstant.JavaType.AUTO,
                        CommonConstant.JavaType.STRING,
                        CommonConstant.JavaType.INTEGER,
                        CommonConstant.JavaType.LONG,
                        CommonConstant.JavaType.BIG_DECIMAL,
                        CommonConstant.JavaType.DOUBLE,
                        CommonConstant.JavaType.FLOAT,
                        CommonConstant.JavaType.BOOLEAN,
                        CommonConstant.JavaType.SHORT,
                        CommonConstant.JavaType.BYTE,
                        CommonConstant.JavaType.BYTE_ARRAY,
                        CommonConstant.JavaType.OBJECT), true));
        javaTypeColumn.setCellValueFactory(param -> param.getValue().javaTypeProperty());

        TableColumn<ColumnMetaData, String> javaPropertyColumn = (TableColumn<ColumnMetaData, String>) tableColumns.get(columnIndex++);
        javaPropertyColumn.setText(resources.getString("javaProperty.text"));
        javaPropertyColumn.setCellFactory(TextFieldTableCell.forTableColumn(converter));
        javaPropertyColumn.setCellValueFactory(param -> param.getValue().javaPropertyProperty());

        TableColumn<ColumnMetaData, String> remarksColumn = (TableColumn<ColumnMetaData, String>) tableColumns.get(columnIndex);
        remarksColumn.setText(resources.getString("remarks.text"));
        remarksColumn.setCellValueFactory(param -> param.getValue().remarksProperty());

    }

    public void initColumns(List<ColumnMetaData> columns) {
        this.columnTableView.getItems().clear();
        this.columnTableView.getItems().addAll(columns);
    }

    public List<ColumnMetaData> getColumns() {
        return new ArrayList<>(this.columnTableView.getItems());
    }

}
