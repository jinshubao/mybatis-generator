package com.jean.mybatis.generator.support.application;

import com.jean.mybatis.generator.utils.DialogUtil;
import javafx.application.Application;
import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * JavaFX-Spring 适配
 *
 * @author jinshubao
 * @date 2017/4/8
 */
public abstract class ApplicationSupport extends Application {
    protected static final Logger logger = LoggerFactory.getLogger(ApplicationSupport.class);
    protected ApplicationContext applicationContext;
    protected Stage primaryStage;


    public ApplicationSupport() {
        notifyPreloader(new Preloader.StateChangeNotification(Preloader.StateChangeNotification.Type.BEFORE_LOAD, this));
    }

    @Override
    public void init() {
        notifyProgress(0d);
        applicationInit();
        if (this.applicationContext == null) {
            RuntimeException exception = new RuntimeException("applicationContext 不能为空");
            notifyPreloader(new Preloader.ErrorNotification(null, "初始化上下文出错", exception));
            throw exception;
        }
    }

    protected abstract void applicationInit();

    protected abstract void applicationStart(Stage stage);

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setOnCloseRequest(event -> DialogUtil.confirmation("退出提示", null, "确认退出？")
                .ifPresent(button -> {
                    if (button != ButtonType.OK) {
                        event.consume();
                    }
                }));
        applicationStart(primaryStage);
        notifyProgress(1d);
    }

    @Override
    public void stop() {
        if (applicationContext instanceof ConfigurableApplicationContext) {
            ConfigurableApplicationContext context = (ConfigurableApplicationContext) applicationContext;
            context.close();
        }
    }

    protected static void launchApplication(Class<? extends ApplicationSupport> applicationClass, String... args) {
        logger.info("launch with parameters {}", Arrays.toString(args));
        launch(applicationClass, args);
    }

    protected Parent loadFxml(String name, String resource, Locale locale) {
        logger.info("loading fxml {}", name);
        FXMLLoader loader = new FXMLLoader();
        if (resource != null) {
            loader.setResources(ResourceBundle.getBundle(resource, locale));
        }
        loader.setControllerFactory(param -> applicationContext.getBean(param));
        try {
            return loader.load(getClass().getResourceAsStream(name));
        } catch (IOException e) {
            logger.error("加载fxml文件{}出错", e);
            throw new RuntimeException(e);
        }
    }

    protected void notifyProgress(double progress) {
        notifyPreloader(new Preloader.ProgressNotification(progress));
    }

    protected Parent loadFxml(String name) {
        return loadFxml(name, null, null);
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
