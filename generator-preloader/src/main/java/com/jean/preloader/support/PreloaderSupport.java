package com.jean.preloader.support;

import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 *
 */
public class PreloaderSupport extends Preloader {

    protected static final Logger logger = LoggerFactory.getLogger(PreloaderSupport.class);

    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void start(Stage stage) {

    }

    @Override
    public boolean handleErrorNotification(ErrorNotification info) {
        logger.error("handleErrorNotification {}", info.getCause());
        return false;
    }

    @Override
    public void handleStateChangeNotification(StateChangeNotification info) {
        logger.info("handleStateChangeNotification {}", info.getType().toString());
    }

    @Override
    public void handleProgressNotification(ProgressNotification info) {
        logger.info("handleProgressNotification {}", info.getProgress());
    }

    protected Parent loadFxml(String name) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        return loader.load(getClass().getResourceAsStream(name));
    }
}
