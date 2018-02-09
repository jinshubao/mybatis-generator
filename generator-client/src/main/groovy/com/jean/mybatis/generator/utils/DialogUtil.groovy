package com.jean.mybatis.generator.utils

import com.jean.mybatis.generator.constant.DatabaseType
import com.jean.mybatis.generator.support.connection.ConnectionConfigFactory
import com.jean.mybatis.generator.support.connection.IConnectionConfig
import javafx.scene.Node
import javafx.scene.control.*
import javafx.scene.image.Image
import javafx.scene.layout.Pane
import javafx.stage.Stage
import javafx.util.Callback

/**
 * 弹框工具类
 * Created by jinshubao on 2017/4/8.
 */
class DialogUtil {

    static final String LOGO_IMAGE = "/image/mybatis-logo.png"

    static Optional<ButtonType> warning(String title, String headerText, String contentText) {
        alert(Alert.AlertType.WARNING, title, headerText, contentText, [ButtonType.OK] as ButtonType[])
    }

    static Optional<ButtonType> confirmation(String title, String headerText, String contentText) {
        alert(Alert.AlertType.CONFIRMATION, title, headerText, contentText, [ButtonType.CANCEL, ButtonType.OK] as ButtonType[])
    }

    static Optional<ButtonType> error(String title, String headerText, String contentText) {
        alert(Alert.AlertType.ERROR, title, headerText, contentText, [ButtonType.OK] as ButtonType[])
    }

    static Optional<ButtonType> information(String title, String headerText, String text) {
        alert(Alert.AlertType.INFORMATION, title, headerText, text, [ButtonType.CLOSE] as ButtonType[])
    }


    static Optional<ButtonType> alert(Alert.AlertType alertType, String title, String headerText, String contentText, ButtonType[] buttonTypes) {
        def alert = new Alert(alertType, contentText, buttonTypes)
        alert.setTitle(title)
        alert.setHeaderText(headerText)
        def stage = alert.getDialogPane().getScene().getWindow() as Stage
        stage.getIcons().add(new Image(this.getClass().getResourceAsStream(LOGO_IMAGE)))
        return alert.showAndWait()
    }

    /**
     * 通用对话框
     * @param title
     * @param headerText
     * @param contentText
     * @param buttonTypes
     * @param okEventHandler
     * @param cancelEvenHandler
     */
    static Optional<ButtonType> dialog(String title, String headerText, String contentText, ButtonType[] buttonTypes) {
        def dialog = new Dialog<>()
        dialog.setTitle(title)
        dialog.setHeaderText(headerText)
        dialog.setContentText(contentText)
        dialog.getDialogPane().getButtonTypes().addAll(buttonTypes)
        def stage = dialog.getDialogPane().getScene().getWindow() as Stage
        stage.getIcons().add(new Image(this.getClass().getResourceAsStream(LOGO_IMAGE)))
        return dialog.showAndWait()
    }

    /**
     * 选择对话框
     * @param title
     * @param headerText
     * @param contentText
     * @param defValue
     * @param items
     * @param eventHandler
     * @return
     */
    static <T> Optional<T> choiceDialog(String title, String headerText, String contentText, T defValue, T[] items) {
        def dialog = new ChoiceDialog(defValue, items)
        dialog.setTitle(title)
        dialog.setHeaderText(headerText)
        dialog.setContentText(contentText)
        def stage = dialog.getDialogPane().getScene().getWindow() as Stage
        stage.getIcons().add(new Image(this.getClass().getResourceAsStream(LOGO_IMAGE)))
        return dialog.showAndWait()
    }

    /**
     * 文本输入对话框
     * @param title
     * @param headerText
     * @param contentText
     * @param defValue
     * @param eventHandler
     * @return
     */
    static Optional<String> textInputDialog(String title, String headerText, String contentText, String defValue) {
        def dialog = new TextInputDialog(defValue)
        dialog.setTitle(title)
        dialog.setHeaderText(headerText)
        dialog.setContentText(contentText)
        def stage = dialog.getDialogPane().getScene().getWindow() as Stage
        stage.getIcons().add(new Image(this.getClass().getResourceAsStream(LOGO_IMAGE)))
        return dialog.showAndWait()
    }

    /**
     * 数据库连接对话框
     * @param title
     * @param headerText
     * @param contentText
     * @param pane
     * @param eventHandler
     * @return
     */
    static Optional<IConnectionConfig> newConnectionDialog(String title, String headerText, Node node) {
        return customizeDialog(title, headerText, node) { buttonType ->
            if (buttonType.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                def values = [:]
                def pane = node as Pane
                pane.getChildren().each { it ->
                    if (it instanceof TextField) {
                        values.put(it.getId(), it.getText())
                    } else if (it instanceof ComboBox) {
                        values.put(it.id, it.getSelectionModel().getSelectedItem())
                    } else if (it instanceof CheckBox) {
                        values.put(it.id, it.isSelected())
                    }
                }
                DatabaseType type = values?.dataBaseType as DatabaseType
                String host = values?.host as String
                Integer port = values?.port as Integer
                String username = values?.username as String
                String password = values?.password as String
                String properties = values?.properties as String
                return ConnectionConfigFactory.newInstance(type, host, port, username, password, properties)
            }
            return null
        }
    }

    /**
     * 自定义对话框
     * @param title
     * @param headerText
     * @param node
     * @param resultConverter
     * @return
     */
    static <T> Optional<T> customizeDialog(String title, String headerText, Node node, Callback<ButtonType, T> resultConverter) {
        def dialog = new Dialog<>()
        dialog.setTitle(title)
        dialog.setHeaderText(headerText)
        dialog.dialogPane.setContent(node)
        dialog.dialogPane.buttonTypes.addAll(ButtonType.OK, ButtonType.CANCEL)
        def stage = dialog.getDialogPane().getScene().getWindow() as Stage
        stage.getIcons().add(new Image(this.getClass().getResourceAsStream(LOGO_IMAGE)))
        if (resultConverter) {
            dialog.setResultConverter(resultConverter)
        }
        return dialog.showAndWait()
    }

    static void exceptionDialog(Throwable ex) {
        exceptionDialog("出错了", ex.message, ex)
    }

    static void exceptionDialog(String title, String headerText, Throwable ex) {
        Alert alert = new Alert(Alert.AlertType.ERROR)
        alert.setTitle(title)
        alert.setHeaderText(headerText)
        alert.setContentText(ex.message)
        def stage = alert.getDialogPane().getScene().getWindow() as Stage
        stage.getIcons().add(new Image(this.getClass().getResourceAsStream(LOGO_IMAGE)))
        StringWriter sw = new StringWriter()
        ex.printStackTrace(new PrintWriter(sw))
        String exceptionText = sw.toString()
        TextArea textArea = new TextArea(exceptionText)
        textArea.setEditable(false)
        textArea.setWrapText(true)
        textArea.setMaxWidth(Double.MAX_VALUE)
        textArea.setMaxHeight(Double.MAX_VALUE)
        alert.getDialogPane().setExpandableContent(textArea)
        alert.showAndWait()
    }
}
