package com.jean.mybatis.generator.utils;

import com.jean.mybatis.generator.constant.CommonConstant;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

/**
 * 弹框工具类
 *
 * @author jinshubao
 * @date 2017/4/8
 */
public class DialogUtil {

    public static Optional<ButtonType> warning(String title, String headerText, String contentText) {
        return alert(Alert.AlertType.WARNING, title, headerText, contentText, ButtonType.OK);
    }

    public static Optional<ButtonType> confirmation(String title, String headerText, String contentText) {
        return alert(Alert.AlertType.CONFIRMATION, title, headerText, contentText, ButtonType.CANCEL, ButtonType.OK);
    }

    public static Optional<ButtonType> error(String title, String headerText, String contentText) {
        return alert(Alert.AlertType.ERROR, title, headerText, contentText, ButtonType.OK);
    }

    public static Optional<ButtonType> information(String title, String headerText, String text) {
        return alert(Alert.AlertType.INFORMATION, title, headerText, text, ButtonType.CLOSE);
    }

    public static Image getLogImage() {
        return new Image(DialogUtil.class.getResourceAsStream(CommonConstant.LOGO_IMAGE));
    }


    public static Optional<ButtonType> alert(Alert.AlertType alertType, String title, String headerText, String contentText, ButtonType... buttonTypes) {
        Alert alert = new Alert(alertType, contentText, buttonTypes);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(getLogImage());
        return alert.showAndWait();
    }

    public static void exceptionDialog(String title, String headerText, Throwable ex) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(ex.getMessage());
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(getLogImage());
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
        String exceptionText = sw.toString();
        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        alert.getDialogPane().setExpandableContent(textArea);
        alert.showAndWait();
    }

    /**
     * 通用对话框
     *
     * @param title
     * @param headerText
     * @param contentText
     * @param buttonTypes
     */
    public static Optional<ButtonType> dialog(String title, String headerText, String contentText, ButtonType... buttonTypes) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle(title);
        dialog.setHeaderText(headerText);
        dialog.setContentText(contentText);
        dialog.getDialogPane().getButtonTypes().addAll(buttonTypes);
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(getLogImage());
        return dialog.showAndWait();
    }

    /**
     * 自定义对话框
     *
     * @param title
     * @param headerText
     * @param node
     * @param resultConverter
     * @return
     */
    public static <T> Optional<T> customizeDialog(String title, String headerText, Node node, Callback<ButtonType, T> resultConverter) {
        return customizeDialog(title, headerText, node, resultConverter, ButtonType.OK, ButtonType.CANCEL);
    }

    /**
     * 自定义对话框
     *
     * @param title
     * @param headerText
     * @param node
     * @param resultConverter
     * @param buttonTypes
     * @param <T>
     * @return
     */
    public static <T> Optional<T> customizeDialog(String title, String headerText, Node node,
                                                  Callback<ButtonType, T> resultConverter,
                                                  ButtonType... buttonTypes) {
        Dialog<T> dialog = new Dialog<>();
        dialog.setTitle(title);
        dialog.setHeaderText(headerText);
        dialog.getDialogPane().setContent(node);
        dialog.getDialogPane().getButtonTypes().addAll(buttonTypes);
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(getLogImage());
        if (resultConverter != null) {
            dialog.setResultConverter(resultConverter);
        }
        return dialog.showAndWait();
    }

    public static void exceptionDialog(Throwable ex) {
        exceptionDialog("", ex.getMessage(), ex);
    }

    public static void exceptionDialog(String title, Throwable ex) {
        exceptionDialog(title, ex.getMessage(), ex);
    }
}
