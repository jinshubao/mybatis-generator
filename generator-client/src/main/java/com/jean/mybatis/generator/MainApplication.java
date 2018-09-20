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

    private Locale locale = Locale.SIMPLIFIED_CHINESE;

    private Parent root = null;
    Parent databaseConnection = null;
    Parent customTable = null;

    @Override
    public void init() throws Exception {
        notifyProgress(0D);

        ApplicationContext context = new AnnotationConfigApplicationContext(MainApplication.class);
        context.getAutowireCapableBeanFactory().autowireBean(this);
        setApplicationContext(context);
        notifyProgress(0.2);

        root = loadFxml("/fxml/Scene.fxml", "message.scene", locale);
        notifyProgress(0.4);

        databaseConnection = loadFxml("/fxml/Connection.fxml", "message.connection", locale);
        notifyProgress(0.6);

        customTable = loadFxml("/fxml/CustomTable.fxml", "message.customTable", locale);
        notifyProgress(0.8);

        CommonConstant.SCENES.put(StageType.MAIN, root);
        CommonConstant.SCENES.put(StageType.CONNECTION, databaseConnection);
        CommonConstant.SCENES.put(StageType.CUSTOM_TABLE, customTable);

        notifyProgress(1D);
    }

    @Override
    public void applicationStart(Stage stage) {
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        String name = "mybatis-generator-client";
        String version = "0.1-snapshot";
        String author = "jinshubao";
        String title = StringUtil.join(Arrays.asList(name, version, author), " --");
        stage.setTitle(title);
        stage.getIcons().add(getIcon());
        stage.setScene(scene);
        stage.show();
    }

    /**
     * 应用图标
     *
     * @return
     */
    private Image getIcon() {
        return new Image(getClass().getResourceAsStream(CommonConstant.LOGO_IMAGE));
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launchApplication(MainApplication.class, args);
    }
}
