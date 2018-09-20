package com.jean.mybatis.generator.controller;

import com.jean.mybatis.generator.constant.DatabaseType;
import com.jean.mybatis.generator.constant.EncodingEnum;
import com.jean.mybatis.generator.support.connection.ConnectionConfig;
import com.jean.mybatis.generator.support.provider.IMetaDataProviderManager;
import com.jean.mybatis.generator.support.provider.IMetadataProvider;
import com.jean.mybatis.generator.utils.DialogUtil;
import com.jean.mybatis.generator.utils.StringUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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

    private static final String DEFAULT_HOST = "10.52.2.51";
    private static final Integer DEFAULT_PORT = 3306;
    private static final String DEFAULT_USER = "dev";
    private static final String DEFAULT_PASSWORD = "F238Fjc29fhJF";

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
        this.host.setText(DEFAULT_HOST);
        this.port.setText(DEFAULT_PORT.toString());
        this.user.setText(DEFAULT_USER);
        this.password.setText(DEFAULT_PASSWORD);
        //其他数据库暂时不支持
        this.dataBaseType.getItems().addAll(DatabaseType.MySql);
        this.dataBaseType.getSelectionModel().selectFirst();
        this.encoding.getItems().addAll(EncodingEnum.values());
        this.encoding.getSelectionModel().selectFirst();

        this.testConnection.setOnAction(event -> {
            try {
                ConnectionConfig config = getConnectionConfig();
                IMetadataProvider provider = providerManager.getSupportedMetaDataProvider(config.getType());
                provider.setConnectionConfig(config);
                if (provider.testConnection()) {
                    String success = resources.getString("test.connection.success");
                    DialogUtil.information(success, null, success);
                } else {
                    String failed = resources.getString("test.connection.failed");
                    DialogUtil.information(failed, null, failed);
                }
            } catch (Exception e) {
                showExceptionDialog(resources, e);
            }
        });
    }

    public ConnectionConfig getConnectionConfig() {
        String host = DEFAULT_HOST;
        int port = DEFAULT_PORT;
        if (StringUtil.isNotBlank(this.host.getText())) {
            host = this.host.getText();
        }
        if (StringUtil.isNotBlank(this.port.getText())) {
            try {
                port = Integer.parseInt(this.port.getText());
            } catch (NumberFormatException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return new ConnectionConfig(
                this.dataBaseType.getValue(),
                host,
                port,
                this.user.getText(),
                this.password.getText(),
                null,
                null,
                this.properties.getText());
    }

}
