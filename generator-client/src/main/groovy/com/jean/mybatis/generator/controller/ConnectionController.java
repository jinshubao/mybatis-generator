package com.jean.mybatis.generator.controller;

import com.jean.mybatis.generator.constant.DatabaseType;
import com.jean.mybatis.generator.constant.EncodingEnum;
import com.jean.mybatis.generator.support.connection.IConnectionConfig;
import com.jean.mybatis.generator.support.metadata.IMetadataProvider;
import com.jean.mybatis.generator.utils.DialogUtil;
import com.jean.mybatis.generator.utils.StringUtil;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by jinshubao on 2017/4/8.
 */
@Controller
public class ConnectionController extends BaseController {

    @FXML
    private ComboBox<DatabaseType> dataBaseType;
    @FXML
    private TextField host;
    @FXML
    private TextField port;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private ComboBox<EncodingEnum> encoding;
    @FXML
    private TextField properties;
    @FXML
    private Button testConnection;
    @FXML
    private CheckBox savePassword;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.dataBaseType.getItems().addAll(DatabaseType.values());
        this.dataBaseType.getSelectionModel().selectFirst();
        this.encoding.getItems().addAll(EncodingEnum.values());
        this.encoding.getSelectionModel().selectFirst();
        this.properties.setText("serverTimezone=UTC&useUnicode=true&useSSL=false");
        this.testConnection.setOnAction(event -> {
            DatabaseType typeValue = this.dataBaseType.getValue();
            IConnectionConfig config = BaseController.getConnectionConfigNewInstance(typeValue);
            if (config == null) {
                DialogUtil.error("发生错误", "不支持的数据库类型", "暂不支持据库【" + typeValue.name + "】");
                return;
            }
            config.setDatabaseType(typeValue);
            config.setHost(this.host.getText());
            config.setPort(Integer.parseInt(this.port.getText()));
            config.setUsername(this.username.getText());
            config.setPassword(this.password.getText());
            config.setCharset(this.encoding.getValue().value);
            config.setProperties(StringUtil.parseProperties(this.properties.getText()));
            try {
                IMetadataProvider service = chooseMetadataService(config.getDatabaseType());
                service.setConnectionConfig(config);
                if (service.testConnection()) {
                    DialogUtil.information("连接成功", null, "连接成功");
                } else {
                    DialogUtil.information("连接失败", null, "连接失败");
                }
            } catch (Exception e) {
                DialogUtil.exceptionDialog("连接失败", e.getMessage(), e);
            }
        });
    }

}
