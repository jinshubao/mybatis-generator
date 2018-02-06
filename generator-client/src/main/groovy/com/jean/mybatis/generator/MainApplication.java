package com.jean.mybatis.generator;

import com.jean.mybatis.generator.constant.CommonConstant;
import com.jean.mybatis.generator.constant.StageType;
import com.jean.mybatis.generator.support.application.ApplicationSupport;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by jinshubao on 2017/4/8.
 */
@Configuration
@ComponentScan
public class MainApplication extends ApplicationSupport {

    @Override
    protected ApplicationContext initApplicationContext() {
        ApplicationContext context = new AnnotationConfigApplicationContext(MainApplication.class);
        context.getAutowireCapableBeanFactory().autowireBean(this);
        return context;
    }


    @Override
    public void start(Stage stage) {
        super.start(stage);
        Parent root = loadFxml("/fxml/Scene.fxml");
        CommonConstant.SCENES.put(StageType.MAIN.toString(), root);
        Parent databaseConnection = loadFxml("/fxml/Connection.fxml");
        CommonConstant.SCENES.put(StageType.CONNECTION.toString(), databaseConnection);
        Parent configuration = loadFxml("/fxml/Configuration.fxml");
        CommonConstant.SCENES.put(StageType.CONFIGURATION.toString(), configuration);
//        def bounds = Screen.getPrimary().getBounds()
//        Scene scene = new Scene(root, bounds.width, bounds.height)
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        stage.setTitle("Mybatis Generator 1.0");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/mybatis-logo.png")));
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
}
