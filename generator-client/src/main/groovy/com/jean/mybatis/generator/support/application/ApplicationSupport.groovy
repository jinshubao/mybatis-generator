package com.jean.mybatis.generator.support.application

import javafx.application.Application
import javafx.application.Preloader
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.stage.Stage
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.ConfigurableApplicationContext

/**
 * JavaFX-Spring 适配
 * Created by jinshubao on 2017/4/8.
 */
abstract class ApplicationSupport extends Application {
    protected static final Logger logger = LoggerFactory.getLogger(Application.class)
    protected static String[] args
    protected ConfigurableApplicationContext applicationContext

    @Override
    void init() throws Exception {
        logger.info("application init...")
        notifyPreloader(new Preloader.StateChangeNotification(Preloader.StateChangeNotification.Type.BEFORE_INIT, this))

    }

    @Override
    void start(Stage primaryStage) throws Exception {
        logger.info("application start...")
        notifyPreloader(new Preloader.StateChangeNotification(Preloader.StateChangeNotification.Type.BEFORE_START, this))
    }

    @Override
    void stop() throws Exception {
        logger.info("application stop...")
        applicationContext?.close()
    }

    protected static void launchApp(Class<? extends ApplicationSupport> applicationClass, String[] args) {
        logger.info("launch with args {}", args)
        ApplicationSupport.args = args
        launch(applicationClass, args)
    }

    Parent loadFxml(String name) {
        logger.info("loadFxml {}", name)
        FXMLLoader loader = new FXMLLoader()
        loader.setControllerFactory {
            applicationContext?.getBean(it)
        }
        loader.load(getClass().getResourceAsStream(name))
    }
}
