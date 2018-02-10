package com.jean.mybatis.generator.utils

import com.jean.mybatis.generator.constant.CommonConstant
import javafx.concurrent.Task
import javafx.scene.Node
import javafx.scene.control.*
import javafx.scene.image.Image
import javafx.scene.text.Text
import javafx.stage.Stage
import javafx.util.Callback

/**
 * 弹框工具类
 * Created by jinshubao on 2017/4/8.
 */
class DialogUtil {

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
        stage.getIcons().add(new Image(this.getClass().getResourceAsStream(CommonConstant.LOGO_IMAGE)))
        return alert.showAndWait()
    }

    static void exceptionDialog(String title, String headerText, Throwable ex) {
        Alert alert = new Alert(Alert.AlertType.ERROR)
        alert.setTitle(title)
        alert.setHeaderText(headerText)
        alert.setContentText(ex.message)
        def stage = alert.getDialogPane().getScene().getWindow() as Stage
        stage.getIcons().add(new Image(this.getClass().getResourceAsStream(CommonConstant.LOGO_IMAGE)))
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
        stage.getIcons().add(new Image(this.getClass().getResourceAsStream(CommonConstant.LOGO_IMAGE)))
        return dialog.showAndWait()
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
        return customizeDialog(title, headerText, node, [ButtonType.OK, ButtonType.CANCEL], resultConverter)
    }

    /**
     * 自定义对话框
     * @param title
     * @param headerText
     * @param node
     * @param buttonTypes
     * @param resultConverter
     * @return
     */
    static <T> Optional<T> customizeDialog(String title, String headerText, Node node, List<ButtonType> buttonTypes, Callback<ButtonType, T> resultConverter) {
        def dialog = new Dialog<>()
        dialog.setTitle(title)
        dialog.setHeaderText(headerText)
        dialog.dialogPane.setContent(node)
        dialog.dialogPane.buttonTypes.addAll(buttonTypes)
        def stage = dialog.getDialogPane().getScene().getWindow() as Stage
        stage.getIcons().add(new Image(this.getClass().getResourceAsStream(CommonConstant.LOGO_IMAGE)))
        if (resultConverter) {
            dialog.setResultConverter(resultConverter)
        }
        return dialog.showAndWait()
    }

    static void exceptionDialog(Throwable ex) {
        exceptionDialog("", ex.message, ex)
    }

    static void exceptionDialog(String title, Throwable ex) {
        exceptionDialog(title, ex.message, ex)
    }


    static void progressDialog(String title, String headerText, Task task) {
        def progressBar = new ProgressBar()
        def message = new Text()
        progressBar.progressProperty().bind(task.progressProperty())
        message.textProperty().bind(task.messageProperty())
        def cancel = ButtonType.CANCEL
        customizeDialog(title, headerText, progressBar, [cancel]) {
            progressBar.progressProperty().unbind()
            message.textProperty().unbind()
            if (it == ButtonType.CANCEL) {
                task.cancel()
            }
        }
    }
}
