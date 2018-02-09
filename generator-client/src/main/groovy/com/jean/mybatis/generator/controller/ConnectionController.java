package com.jean.mybatis.generator.controller;

import com.jean.mybatis.generator.constant.DatabaseType;
import com.jean.mybatis.generator.constant.EncodingEnum;
import com.jean.mybatis.generator.support.connection.AbstractConnectionConfig;
import com.jean.mybatis.generator.support.connection.ConnectionConfigFactory;
import com.jean.mybatis.generator.support.connection.IConnectionConfig;
import com.jean.mybatis.generator.utils.DialogUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

/**
 *
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
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private ComboBox<EncodingEnum> encoding;
    @FXML
    private TextField properties;
    @FXML
    private Button testConnection;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.dataBaseType.getItems().addAll(DatabaseType.values());
        this.dataBaseType.getSelectionModel().selectFirst();
        this.encoding.getItems().addAll(EncodingEnum.values());
        this.encoding.getSelectionModel().selectFirst();
        this.properties.setText("serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&useSSL=false");
        this.testConnection.setOnAction(event -> {
            try {
                IConnectionConfig config = getConnectionConfig();
                if (config.testConnection()) {
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

   public IConnectionConfig getConnectionConfig(){
       return ConnectionConfigFactory.newInstance(
               this.dataBaseType.getValue(),
               this.host.getText(),
               Integer.parseInt(this.port.getText()),
               this.username.getText(), this.password.getText(),
               this.properties.getText());
    }

}
