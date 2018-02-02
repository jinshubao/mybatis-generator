package com.jean.mybatis.generator

import com.jean.mybatis.generator.constant.CommonConstant
import com.jean.mybatis.generator.constant.StageTypeEnum
import com.jean.mybatis.generator.support.application.ApplicationSupport
import com.jean.mybatis.generator.utils.DialogUtil
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.ButtonType
import javafx.scene.image.Image
import javafx.stage.Stage
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

/**
 *
 * Created by jinshubao on 2017/4/8.
 */
@Configuration
@ComponentScan
class MainApplication extends ApplicationSupport {

    @Override
    void init() throws Exception {
        super.init()
        applicationContext = new AnnotationConfigApplicationContext(MainApplication.class)
        applicationContext.getAutowireCapableBeanFactory().autowireBean(this)
    }

    @Override
    void start(Stage stage) throws Exception {
        super.start(stage)
        Parent root = loadFxml("/fxml/Scene.fxml")
        CommonConstant.SCENES.put(StageTypeEnum.MAIN.toString(), root)
        Parent databaseConnection = loadFxml("/fxml/Connection.fxml")
        CommonConstant.SCENES.put(StageTypeEnum.CONNECTION.toString(), databaseConnection)
        Parent configuration = loadFxml("/fxml/Configuration.fxml")
        CommonConstant.SCENES.put(StageTypeEnum.CONFIGURATION.toString(), configuration)
//        def bounds = Screen.getPrimary().getBounds()
//        Scene scene = new Scene(root, bounds.width, bounds.height)
        Scene scene = new Scene(root)
        scene.getStylesheets().add("/styles/Styles.css")
        stage.setTitle("Mybatis Generator 1.0")
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/mybatis-logo.png")))
        stage.setScene(scene)
        stage.show()
        stage.setOnCloseRequest { event ->
            DialogUtil.confirmation("退出提示", null, "确认退出？").ifPresent() {
                if (it != ButtonType.OK) {
                    event.consume()
                }
            }
        }
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
        launchApp(MainApplication.class, args)
    }
}
