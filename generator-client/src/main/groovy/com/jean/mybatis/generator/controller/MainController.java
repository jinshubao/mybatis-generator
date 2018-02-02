package com.jean.mybatis.generator.controller;

import com.jean.mybatis.generator.constant.CommonConstant;
import com.jean.mybatis.generator.constant.StageTypeEnum;
import com.jean.mybatis.generator.core.GeneratorService;
import com.jean.mybatis.generator.factory.ListViewCellFactory;
import com.jean.mybatis.generator.factory.Selectable;
import com.jean.mybatis.generator.plugins.CommentGeneratorPlugin;
import com.jean.mybatis.generator.support.connection.IConnectionConfig;
import com.jean.mybatis.generator.support.database.IDatabaseMetadata;
import com.jean.mybatis.generator.support.metadata.IMetadataProvider;
import com.jean.mybatis.generator.support.table.ITableMetadata;
import com.jean.mybatis.generator.utils.DialogUtil;
import com.jean.mybatis.generator.utils.StringUtil;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;
import org.mybatis.generator.config.*;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.mybatis.generator.plugins.SerializablePlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by jinshubao on 2017/4/8.
 */
@Controller
public class MainController extends BaseController {

    @FXML
    private MenuBar menuBar;
    @FXML
    private Menu fileMenu;
    @FXML
    private MenuItem newConnectionMenuItem;
    @FXML
    private MenuItem exitMenuItem;
    @FXML
    private Menu editMenu;
    @FXML
    private MenuItem newConfigurationMenuItem;
    @FXML
    private MenuItem manageConfigurationMenuItem;
    @FXML
    private Menu configurationListMenu;
    @FXML
    private ToggleGroup configurationGroup;
    @FXML
    private Menu helpMenu;
    @FXML
    private MenuItem aboutMenuItem;
    @FXML
    private ProgressIndicator progressIndicator;
    @FXML
    private Label message;
    @FXML
    private TextField projectDir;
    @FXML
    private TextField sourceDir;
    @FXML
    private TextField resourcesDir;
    @FXML
    private Button explorerProject;
    @FXML
    private CheckBox camelCase;
    @FXML
    private Button generate;
    @FXML
    private Button saveConfig;
    @FXML
    private TextField modelPackage;
    @FXML
    private TextField sqlMapperPackage;
    @FXML
    private TextField mapperPackage;
    @FXML
    public ComboBox<IDatabaseMetadata> databases;
    @FXML
    public ListView<ITableMetadata> tables;

    private IMetadataProvider metadataProvider;

    @Autowired
    private GeneratorService generatorService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tables.setCellFactory(ListViewCellFactory.forListView(Pos.CENTER_LEFT));
        this.message.setText(null);
        this.progressIndicator.setVisible(false);
        this.newConnectionMenuItem.setOnAction(event ->
                DialogUtil.newConnectionDialog("新建数据库连接", null, CommonConstant.SCENES.get(StageTypeEnum.CONNECTION.toString()))
                        .ifPresent(config -> {
                            try {
                                this.metadataProvider = MainController.this.chooseMetadataService(config.getDatabaseType());
                            } catch (Exception e) {
                                DialogUtil.exceptionDialog(e.getMessage(), "", e);
                                return;
                            }
                            this.metadataProvider.setConnectionConfig(config);
                            this.databases.getSelectionModel().clearSelection();
                            this.databases.getItems().clear();
                            this.databases.getItems().addAll(metadataProvider.getDatabases());
                            this.tables.getSelectionModel().clearSelection();
                            this.tables.getItems().clear();
                        }));

        this.explorerProject.setOnAction(event -> {
            DirectoryChooser chooser = new DirectoryChooser();
            Window window = ((Node) event.getSource()).getScene().getWindow();
            File file = chooser.showDialog(window);
            if (file != null && file.exists() && file.isDirectory()) {
                this.projectDir.setText(file.getAbsolutePath());
            }
        });
        this.camelCase.setSelected(true);

        //绑定按钮状态
        this.generate.disableProperty().bind(
                this.generatorService.runningProperty()
                        .or(projectDir.textProperty().isEmpty())
                        .or(modelPackage.textProperty().isEmpty())
                        .or(mapperPackage.textProperty().isEmpty())
                        .or(sqlMapperPackage.textProperty().isEmpty()));

        this.message.textProperty().bind(this.generatorService.messageProperty());
        this.progressIndicator.visibleProperty().bind(this.generatorService.runningProperty());
        this.generatorService.setOnFailed(event -> {
            Throwable exception = this.generatorService.getException();
            DialogUtil.exceptionDialog("生成失败", exception.getMessage(), exception);
        });
        this.generatorService.setOnSucceeded(event -> {
            List<String> value = this.generatorService.getValue();
            if (!value.isEmpty()) {
                DialogUtil.warning("警告", "生成过程出现警告", StringUtil.join(value, ";\r\n"));
            }
        });

        Context context = createDefaultContext();
        Configuration configuration = createDefaultConfiguration(context);

        ChangeListener<String> changeListener = (observable, oldValue, newValue) -> {
            String projectDirText = this.projectDir.getText();
            String sourceDirText = this.sourceDir.getText();
            String resourcesDirText = this.resourcesDir.getText();
            String modelPackageText = this.modelPackage.getText();
            String mapperPackageText = this.mapperPackage.getText();
            String sqlMapperPackageText = this.sqlMapperPackage.getText();
            String sourcePath = StringUtil.toPath(projectDirText, sourceDirText);
            String resourcePath = StringUtil.toPath(projectDirText, resourcesDirText);
            context.getJavaModelGeneratorConfiguration().setTargetProject(sourcePath);
            context.getJavaModelGeneratorConfiguration().setTargetPackage(modelPackageText);
            context.getJavaClientGeneratorConfiguration().setTargetProject(sourcePath);
            context.getJavaClientGeneratorConfiguration().setTargetPackage(mapperPackageText);
            context.getSqlMapGeneratorConfiguration().setTargetProject(resourcePath);
            context.getSqlMapGeneratorConfiguration().setTargetPackage(sqlMapperPackageText);
        };


        this.projectDir.textProperty().addListener(changeListener);
        this.sourceDir.textProperty().addListener(changeListener);
        this.resourcesDir.textProperty().addListener(changeListener);
        this.modelPackage.textProperty().addListener(changeListener);
        this.mapperPackage.textProperty().addListener(changeListener);
        this.sqlMapperPackage.textProperty().addListener(changeListener);

        this.databases.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.tables.getItems().clear();
            this.tables.getSelectionModel().clearSelection();
            this.tables.getItems().addAll(metadataProvider.getTables(newValue));
            IConnectionConfig connectionConfig = this.metadataProvider.getConnectionConfig();
            context.getJdbcConnectionConfiguration().setDriverClass(connectionConfig.getDatabaseType().driverClass);
            context.getJdbcConnectionConfiguration().setConnectionURL(connectionConfig.getConnectionURL(newValue));
            context.getJdbcConnectionConfiguration().setUserId(connectionConfig.getUsername());
            context.getJdbcConnectionConfiguration().setPassword(connectionConfig.getPassword());
        });


        //生成代码
        this.generate.setOnAction((ActionEvent event) -> {
            context.getTableConfigurations().clear();
            tables.getItems().filtered(Selectable::isSelected).forEach(iTableMetadata -> {
                TableConfiguration table = new TableConfiguration(context);
                table.setTableName(iTableMetadata.getName());
                table.setSelectByExampleStatementEnabled(false);
                table.setDeleteByExampleStatementEnabled(false);
                table.setUpdateByExampleStatementEnabled(false);
                //使用表字段名
                table.addProperty("useActualColumnNames", Boolean.toString(camelCase.isSelected()));
                GeneratedKey generatedKey = new GeneratedKey("id", metadataProvider.getConnectionConfig().getDatabaseType().name,
                        true, "");
                table.setGeneratedKey(generatedKey);
                context.addTableConfiguration(table);
            });
            this.generatorService.restart(configuration, new DefaultShellCallback(true));
        });

        //保存配置
        this.saveConfig.setOnAction((ActionEvent event) -> {
            logger.debug(configuration.toDocument().getFormattedContent());
        });
    }

    protected Configuration createDefaultConfiguration(Context context) {

        Configuration configuration = new Configuration();
        configuration.addContext(context);
        return configuration;
    }

    protected Context createDefaultContext() {
        //---------上下文环境----------
        Context context = new Context(ModelType.HIERARCHICAL);
        context.setId("context");
        context.setTargetRuntime("MyBatis3Simple");
        context.addProperty("beginningDelimiter", "`");
        context.addProperty("endingDelimiter", "`");

        //---------插件----------
        PluginConfiguration serializablePlugin = new PluginConfiguration();
        serializablePlugin.setConfigurationType(SerializablePlugin.class.getName());
        context.addPluginConfiguration(serializablePlugin);

        //---------注释----------
        CommentGeneratorConfiguration commentGenerator = new CommentGeneratorConfiguration();
        commentGenerator.addProperty("suppressAllComments", Boolean.toString(false));
        commentGenerator.addProperty("addRemarkComments", Boolean.toString(false));
        commentGenerator.addProperty("suppressDate", Boolean.toString(false));
        commentGenerator.setConfigurationType(CommentGeneratorPlugin.class.getName());
        context.setCommentGeneratorConfiguration(commentGenerator);

        //---------类型转换----------
        JavaTypeResolverConfiguration javaTypeResolver = new JavaTypeResolverConfiguration();
        javaTypeResolver.addProperty("forceBigDecimals", Boolean.toString(false));
        context.setJavaTypeResolverConfiguration(javaTypeResolver);

        //---------model----------
        JavaModelGeneratorConfiguration javaModelGenerator = new JavaModelGeneratorConfiguration();
        context.setJavaModelGeneratorConfiguration(javaModelGenerator);

        //---------mapper----------
        JavaClientGeneratorConfiguration javaClientGenerator = new JavaClientGeneratorConfiguration();
        javaClientGenerator.setConfigurationType("XMLMAPPER");
        javaClientGenerator.addProperty("enableSubPackages", Boolean.toString(false));
        context.setJavaClientGeneratorConfiguration(javaClientGenerator);

        //---------sql----------
        SqlMapGeneratorConfiguration sqlMapGenerator = new SqlMapGeneratorConfiguration();
        context.setSqlMapGeneratorConfiguration(sqlMapGenerator);

        //---------jdbc----------
        JDBCConnectionConfiguration jdbcConnection = new JDBCConnectionConfiguration();
        context.setJdbcConnectionConfiguration(jdbcConnection);
        return context;
    }
}
