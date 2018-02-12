package com.jean.preloader.support;

import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author jinshubao
 */
public class PreloaderSupport extends Preloader {

    protected static final Logger logger = LoggerFactory.getLogger(PreloaderSupport.class);

    protected ProgressBar progressBar;
    protected Stage stage;

    @Override
    public void init() throws Exception {
    }

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        VBox root = new VBox();
        root.setMaxHeight(Region.USE_COMPUTED_SIZE);
        root.setMaxWidth(Region.USE_COMPUTED_SIZE);
        root.setMinHeight(Region.USE_COMPUTED_SIZE);
        root.setMinWidth(Region.USE_COMPUTED_SIZE);

        ImageView imageView = new ImageView(getBackground());
        imageView.setFitWidth(600D);
        imageView.setFitHeight(400D);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        HBox hBox = new HBox();
        VBox.setVgrow(hBox, Priority.NEVER);

        progressBar = new ProgressBar();
        progressBar.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(progressBar, Priority.ALWAYS);
        hBox.getChildren().add(progressBar);
        root.getChildren().addAll(imageView, hBox);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    @Override
    public boolean handleErrorNotification(ErrorNotification info) {
        logger.error("handleErrorNotification {}", info.getCause());
        this.stage.close();
        return false;
    }

    @Override
    public void handleStateChangeNotification(StateChangeNotification info) {
        logger.info("handleStateChangeNotification {}", info.getType().toString());
        if (info.getType() == StateChangeNotification.Type.BEFORE_START) {
            this.stage.close();
        }
    }

    @Override
    public void handleProgressNotification(ProgressNotification info) {
        progressBar.setProgress(info.getProgress());
    }

    public Image getBackground(){
        return new Image(this.getClass().getResourceAsStream("/image/background.jpg"));
    }
}
