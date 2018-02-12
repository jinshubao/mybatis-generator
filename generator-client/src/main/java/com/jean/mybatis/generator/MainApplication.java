package com.jean.mybatis.generator;

import com.jean.mybatis.generator.constant.CommonConstant;
import com.jean.mybatis.generator.constant.StageType;
import com.jean.mybatis.generator.support.application.ApplicationSupport;
import com.jean.mybatis.generator.utils.StringUtil;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;
import java.util.Locale;

/**
 * @author jinshubao
 * @date 2017/4/8
 */
@ComponentScan
public class MainApplication extends ApplicationSupport {

    @Override
    protected void applicationInit() {
        ApplicationContext context = new AnnotationConfigApplicationContext(MainApplication.class);
        context.getAutowireCapableBeanFactory().autowireBean(this);
        setApplicationContext(context);
    }

    @Override
    public void applicationStart(Stage stage) {
        Locale locale = Locale.SIMPLIFIED_CHINESE;
        Parent root = loadFxml("/fxml/Scene.fxml", "message.scene", locale);
        CommonConstant.SCENES.put(StageType.MAIN, root);
        notifyProgress(50d);
        sleep();
        Parent databaseConnection = loadFxml("/fxml/Connection.fxml", "message.connection", locale);
        CommonConstant.SCENES.put(StageType.CONNECTION, databaseConnection);
        notifyProgress(70d);
        sleep();
        Parent customTable = loadFxml("/fxml/CustomTable.fxml", "message.customTable", locale);
        CommonConstant.SCENES.put(StageType.CUSTOM_TABLE, customTable);
        sleep();
//        Parent configuration = loadFxml("/fxml/Configuration.fxml");
//        CommonConstant.SCENES.put(StageType.CONFIGURATION.toString(), configuration);
        //Rectangle2D bounds = Screen.getPrimary().getBounds();
        //Scene scene = new Scene(root, bounds.getWidth(), bounds.getHeight());
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        String name = "mybatis-generator-client";
        String version = "0.1-snapshot";
        String author = "jinshubao";
        String title = StringUtil.join(Arrays.asList(name, version, author), " --");
        stage.setTitle(title);
        stage.getIcons().add(new Image(getClass().getResourceAsStream(CommonConstant.LOGO_IMAGE)));
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    static void main(String[] args) {
        launchApplication(MainApplication.class, args);
    }

    private void sleep() {
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
