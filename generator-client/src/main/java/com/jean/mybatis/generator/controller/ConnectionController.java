package com.jean.mybatis.generator.controller;

import com.jean.mybatis.generator.constant.DatabaseType;
import com.jean.mybatis.generator.constant.EncodingEnum;
import com.jean.mybatis.generator.support.connection.ConnectionConfig;
import com.jean.mybatis.generator.support.provider.IMetaDataProviderManager;
import com.jean.mybatis.generator.support.provider.IMetadataProvider;
import com.jean.mybatis.generator.utils.DialogUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.mybatis.generator.api.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author jinshubao
 * @date 2017/4/8
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
    private TextField user;
    @FXML
    private PasswordField password;
    @FXML
    private ComboBox<EncodingEnum> encoding;
    @FXML
    private TextField properties;
    @FXML
    private Button testConnection;

    private IMetadataProvider metadataProvider;

    @Autowired
    private IMetaDataProviderManager providerManager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.dataBaseType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                this.properties.setText(newValue.properties);
                this.properties.setPromptText(newValue.properties);
            } else {
                this.properties.setText(null);
                this.properties.setPromptText(null);
            }
        });
        this.dataBaseType.getItems().addAll(DatabaseType.values());
        this.dataBaseType.getSelectionModel().selectFirst();
        this.encoding.getItems().addAll(EncodingEnum.values());
        this.encoding.getSelectionModel().selectFirst();

        this.testConnection.setOnAction(event -> {
            try {
                ConnectionConfig config = getConnectionConfig();
                IMetadataProvider provider = providerManager.getMetaDataProvider(config.getType());
                provider.setConnectionConfig(config);
                if (provider.testConnection()) {
                    DialogUtil.information("连接成功", null, "连接成功");
                } else {
                    DialogUtil.information("连接失败", null, "连接失败");
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                DialogUtil.exceptionDialog(e);
            }
        });
    }

    public ConnectionConfig getConnectionConfig() {
        return new ConnectionConfig(
                this.dataBaseType.getValue(),
                this.host.getText(),
                Integer.parseInt(this.port.getText()),
                this.user.getText(),
                this.password.getText(),
                null,
                null,
                this.properties.getText());
    }

}
