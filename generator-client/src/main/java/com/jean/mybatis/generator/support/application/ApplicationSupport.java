package com.jean.mybatis.generator.support.application;

import com.jean.mybatis.generator.utils.DialogUtils;
import com.jean.mybatis.generator.utils.StringUtils;
import javafx.application.Application;
import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.io.Closeable;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * JavaFX-Spring 适配
 *
 * @author jinshubao
 * @date 2017/4/8
 */
public abstract class ApplicationSupport extends Application {

    static final Logger logger = LoggerFactory.getLogger(ApplicationSupport.class);

    protected ApplicationContext applicationContext;

    protected Stage stage;


    public ApplicationSupport() {
    }

    /**
     * 初始化进度通知
     *
     * @param progress
     */
    protected final void notifyProgress(double progress) {
        notifyPreloader(new Preloader.ProgressNotification(progress));
    }

    /**
     * 初始化界面
     *
     * @param stage 主界面
     * @throws Exception
     */
    protected abstract void applicationStart(Stage stage);

    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;
        applicationStart(primaryStage);
        primaryStage.setOnCloseRequest(event -> DialogUtils.confirmation("退出提示", null, "确认退出？")
                .ifPresent(button -> {
                    if (button != ButtonType.OK) {
                        event.consume();
                    }
                }));
    }

    @Override
    public void stop() {
        if (applicationContext instanceof Closeable) {
            Closeable context = (Closeable) applicationContext;
            try {
                context.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * @param applicationClass
     * @param args
     */
    protected static void launchApplication(Class<? extends ApplicationSupport> applicationClass, String... args) {
        logger.info("launch with parameters {}", StringUtils.arrayToDelimitedString(args, ", "));
        launch(applicationClass, args);
    }

    protected Parent loadFxml(String name) {
        return loadFxml(name, null, null);
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

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public Stage getStage() {
        return stage;
    }
}
