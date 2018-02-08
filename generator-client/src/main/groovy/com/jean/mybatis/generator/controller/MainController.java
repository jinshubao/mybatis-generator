package com.jean.mybatis.generator.controller;

import com.jean.mybatis.generator.constant.*;
import com.jean.mybatis.generator.core.GeneratorService;
import com.jean.mybatis.generator.support.meta.AbstractTableMetaData;
import com.jean.mybatis.generator.support.provider.IMetaDataProviderManager;
import com.jean.mybatis.generator.factory.ListViewCellFactory;
import com.jean.mybatis.generator.factory.Selectable;
import com.jean.mybatis.generator.plugins.CommentGeneratorPlugin;
import com.jean.mybatis.generator.support.connection.AbstractConnectionConfig;
import com.jean.mybatis.generator.support.connection.IConnectionConfig;
import com.jean.mybatis.generator.support.meta.ICatalogMetaData;
import com.jean.mybatis.generator.support.meta.ITableMetaData;
import com.jean.mybatis.generator.support.provider.IMetadataProvider;
import com.jean.mybatis.generator.utils.DialogUtil;
import com.jean.mybatis.generator.utils.StringUtil;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;
import javafx.util.Callback;
import org.mybatis.generator.config.*;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.mybatis.generator.plugins.EqualsHashCodePlugin;
import org.mybatis.generator.plugins.SerializablePlugin;
import org.mybatis.generator.plugins.ToStringPlugin;
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

    //---------菜单栏----------
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

    //left
    @FXML
    private ComboBox<ICatalogMetaData> tableCatalog;
    @FXML
    private ListView<ITableMetaData> tables;
    @FXML
    private TableView<ITableMetaData> table;
    @FXML
    private Hyperlink selectAll;
    @FXML
    private Hyperlink reverseSelect;

    //---------基本配置----------
    @FXML
    private TextField projectDir;
    @FXML
    private TextField sourceDir;
    @FXML
    private TextField resourcesDir;
    @FXML
    private Button explorerProject;
    @FXML
    private TextField modelPackage;
    @FXML
    private TextField sqlMapperPackage;
    @FXML
    private TextField mapperPackage;
    @FXML
    private CheckBox overwrite;
    @FXML
    private ComboBox<ModelType> defaultModelType;
    @FXML
    private ComboBox<TargetRuntime> targetRuntime;
    @FXML
    private CheckBox useCommentPlugin;
    @FXML
    private CheckBox useSerializerPlugin;
    @FXML
    private CheckBox useEqualsHashCodePlugin;
    @FXML
    private CheckBox useToStringPlugin;

    //---------Model配置----------
    @FXML
    private CheckBox camelCase;
    @FXML
    private CheckBox autoDelimitKeywords;
    @FXML
    private CheckBox forceBigDecimals;
    @FXML
    private ComboBox<EncodingEnum> javaFileEncoding;
    @FXML
    private TextField rootClass;
    @FXML
    private CheckBox constructorBased;
    @FXML
    private CheckBox enableModelSubPackages;
    @FXML
    private CheckBox immutable;
    @FXML
    private CheckBox trimStrings;


    //---------Mapper配置----------
    @FXML
    private ComboBox<JavaClientType> javaClientType;
    @FXML
    private ComboBox<MethodVisibility> exampleMethodVisibility;
    @FXML
    private ComboBox<NameCalculator> methodNameCalculator;
    @FXML
    private CheckBox enableMapperSubPackages;
    @FXML
    private TextField implementationPackage;
    @FXML
    private TextField rootInterface;
    @FXML
    private CheckBox useLegacyBuilder;

    //---------Common----------
    @FXML
    private Button generate;
    @FXML
    private Button saveConfig;

    //---------bottom----------
    @FXML
    private ProgressIndicator progressIndicator;
    @FXML
    private Label message;

    //私有变量
    @Autowired
    private GeneratorService generatorService;

    private IMetadataProvider metadataProvider;

    @Autowired
    private IMetaDataProviderManager providerManager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.newConnectionMenuItem.setOnAction(event ->
                DialogUtil.newConnectionDialog("新建数据库连接", null, CommonConstant.SCENES.get(StageType.CONNECTION.toString()))
                        .ifPresent(config -> {
                            try {
                                this.metadataProvider = this.providerManager.getMetaDataProvider(config.getType());
                                this.metadataProvider.setConnectionConfig(config);
                                this.tableCatalog.getItems().clear();
                                this.tableCatalog.getItems().addAll(metadataProvider.getCatalogs());
                            } catch (Exception e) {
                                logger.error(e.getMessage(), e);
                                DialogUtil.exceptionDialog(e);
                            }
                        }));

        this.tableCatalog.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                this.tables.getItems().clear();
                this.table.getItems().clear();
                AbstractConnectionConfig config = (AbstractConnectionConfig) this.metadataProvider.getConnectionConfig();
                if (newValue != null) {
                    config.setTableCatalog(newValue.getTableCatalog());
                } else {
                    config.setTableCatalog(null);
                }
                List<ITableMetaData> tables = this.metadataProvider.getTables();
                this.tables.getItems().addAll(tables);
                this.table.getItems().addAll(tables);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                DialogUtil.exceptionDialog(e);
            }
        });
        /*this.tableSchema.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                this.tables.getItems().clear();
                AbstractConnectionConfig config = (AbstractConnectionConfig) this.metadataProvider.getConnectionConfig();
                if (newValue != null) {
                    config.setTableSchema(newValue.getTableSchema());
                } else {
                    config.setTableSchema(null);
                }
                this.tables.getItems().addAll(this.metadataProvider.getTables());
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                DialogUtil.exceptionDialog(e);
            }
        });*/
        this.tables.setCellFactory(ListViewCellFactory.forListView());
        TableColumn<ITableMetaData, Boolean> column0 = (TableColumn<ITableMetaData, Boolean>) table.getColumns().get(0);
        TableColumn<ITableMetaData, String> column1 = (TableColumn<ITableMetaData, String>) table.getColumns().get(1);
        TableColumn<ITableMetaData, String> column2 = (TableColumn<ITableMetaData, String>) table.getColumns().get(2);
        Callback callback = CheckBoxTableCell.forTableColumn(column0);
        column0.setCellValueFactory(callback);
        column0.setCellValueFactory(param -> param.getValue().selectedProperty());
        column1.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getTableName()));
        column2.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getRemarks()));

        this.selectAll.setOnAction(event -> {
            ObservableList<ITableMetaData> items = this.tables.getItems();
            if (!items.isEmpty()) {
                final BooleanProperty selectedAll = new SimpleBooleanProperty(true);
                for (ITableMetaData item : items) {
                    if (!item.isSelected()) {
                        selectedAll.set(false);
                        break;
                    }
                }
                this.tables.getItems().forEach(tableMetadata -> tableMetadata.setSelected(!selectedAll.get()));
            }
        });

        this.reverseSelect.setOnAction(event -> {
            if (!this.tables.getItems().isEmpty()) {
                this.tables.getItems().forEach(tableMetadata -> tableMetadata.setSelected(!tableMetadata.isSelected()));
            }
        });

        this.message.textProperty().bind(this.generatorService.messageProperty());
        this.progressIndicator.visibleProperty().bind(this.generatorService.runningProperty());

        this.targetRuntime.valueProperty().addListener((observable, oldValue, newValue) -> {
            this.javaClientType.getItems().clear();
            ObservableList<JavaClientType> objects = this.javaClientType.getItems();
            if (newValue == TargetRuntime.MyBatis3) {
                objects.add(JavaClientType.XMLMAPPER);
                objects.add(JavaClientType.ANNOTATEDMAPPER);
                objects.add(JavaClientType.MIXEDMAPPER);
            } else if (newValue == TargetRuntime.MyBatis3Simple) {
                objects.add(JavaClientType.XMLMAPPER);
                objects.add(JavaClientType.ANNOTATEDMAPPER);
            } else if (newValue == TargetRuntime.Ibatis2Java2
                    || newValue == TargetRuntime.Ibatis2Java5) {
                objects.add(JavaClientType.IBATIS);
                objects.add(JavaClientType.GENERIC_CI);
                objects.add(JavaClientType.GENERIC_SI);
                objects.add(JavaClientType.SPRING);
            }
            if (!objects.isEmpty()) {
                this.javaClientType.getSelectionModel().selectFirst();
            }
        });
        this.targetRuntime.getItems().addAll(TargetRuntime.values());
        this.targetRuntime.getSelectionModel().select(TargetRuntime.MyBatis3Simple);
        this.defaultModelType.getItems().addAll(ModelType.values());
        this.defaultModelType.getSelectionModel().select(ModelType.CONDITIONAL);
        this.useSerializerPlugin.setSelected(true);
        this.useCommentPlugin.setSelected(true);
        this.useEqualsHashCodePlugin.setSelected(false);
        this.useToStringPlugin.setSelected(false);

        //绑定按钮状态
        this.generate.disableProperty().bind(
                this.generatorService.runningProperty()
                        .or(projectDir.textProperty().isEmpty())
                        .or(modelPackage.textProperty().isEmpty())
                        .or(mapperPackage.textProperty().isEmpty())
                        .or(sqlMapperPackage.textProperty().isEmpty()));


        //model

        this.explorerProject.setOnAction(event -> {
            DirectoryChooser chooser = new DirectoryChooser();
            Window window = ((Node) event.getSource()).getScene().getWindow();
            File file = chooser.showDialog(window);
            if (file != null && file.exists() && file.isDirectory()) {
                this.projectDir.setText(file.getAbsolutePath());
            }
        });
        this.camelCase.setSelected(true);
        this.autoDelimitKeywords.setSelected(false);
        this.forceBigDecimals.setSelected(false);
        this.javaFileEncoding.getItems().addAll(EncodingEnum.values());
        this.javaFileEncoding.getSelectionModel().select(EncodingEnum.UTF8);
        this.constructorBased.setSelected(false);
        this.constructorBased.disableProperty().bind(new BooleanBinding() {
            {
                super.bind(targetRuntime.valueProperty());
            }

            @Override
            protected boolean computeValue() {
                return targetRuntime.getValue() != TargetRuntime.MyBatis3
                        && targetRuntime.getValue() != TargetRuntime.MyBatis3Simple;
            }
        });

        this.enableModelSubPackages.setSelected(false);
        this.immutable.setSelected(false);
        this.trimStrings.setSelected(false);

        //mapper
        BooleanBinding mybatis3Binding = new BooleanBinding() {
            {
                super.bind(targetRuntime.valueProperty());
            }

            @Override
            protected boolean computeValue() {
                return targetRuntime.getValue() == TargetRuntime.MyBatis3;
            }
        };
        this.enableMapperSubPackages.setSelected(false);
        this.implementationPackage.disableProperty().bind(this.enableMapperSubPackages.selectedProperty().not());
        this.exampleMethodVisibility.getItems().addAll(MethodVisibility.values());
        this.exampleMethodVisibility.getSelectionModel().selectFirst();
        this.exampleMethodVisibility.disableProperty().bind(mybatis3Binding);
        this.methodNameCalculator.getItems().addAll(NameCalculator.values());
        this.methodNameCalculator.getSelectionModel().selectFirst();
        this.methodNameCalculator.disableProperty().bind(mybatis3Binding);


        this.generatorService.messageProperty().addListener((observable, oldValue, newValue) -> {
            logger.debug(newValue);
        });
        this.generatorService.setOnFailed(event -> {
            Throwable ex = this.generatorService.getException();
            logger.error(ex.getMessage(), ex);
            DialogUtil.exceptionDialog(ex);
        });

        this.generatorService.setOnSucceeded(event -> {
            //什么都不做
        });

        //生成代码
        this.generate.setOnAction((ActionEvent event) -> {
            Configuration configuration = createDefaultConfiguration(createDefaultContext());
            this.generatorService.restart(configuration, new DefaultShellCallback(overwrite.isSelected()));
        });

        //保存配置
        this.saveConfig.setOnAction((ActionEvent event) -> {
            Configuration configuration = createDefaultConfiguration(createDefaultContext());
            logger.debug(configuration.toDocument().getFormattedContent());
        });
    }

    protected Configuration createDefaultConfiguration(Context context) {
        Configuration configuration = new Configuration();
        configuration.addContext(context);
        return configuration;
    }

    protected Context createDefaultContext() {

        IConnectionConfig connectionConfig = this.metadataProvider.getConnectionConfig();
        //---------上下文环境----------
        Context context = new Context(this.defaultModelType.getValue());
        context.setId("context");
        context.setTargetRuntime(this.targetRuntime.getValue().getValue());
        context.addProperty("beginningDelimiter", "`");
        context.addProperty("endingDelimiter", "`");
        context.addProperty("javaFileEncoding", this.javaFileEncoding.getValue().value.name());
//        context.addProperty("javaFormatter", DefaultJavaFormatter.class.getName());
//        context.addProperty("xmlFormatter", DefaultXmlFormatter.class.getName());

        String projectDirText = this.projectDir.getText();
        String sourcePath = StringUtil.toPath(projectDirText, this.sourceDir.getText());
        String resourcePath = StringUtil.toPath(projectDirText, this.resourcesDir.getText());

        //---------插件----------
        if (this.useSerializerPlugin.isSelected()) {
            PluginConfiguration serializablePlugin = new PluginConfiguration();
            serializablePlugin.setConfigurationType(SerializablePlugin.class.getName());
            context.addPluginConfiguration(serializablePlugin);
        }
        if (this.useEqualsHashCodePlugin.isSelected()) {
            PluginConfiguration equalsHashCodePlugin = new PluginConfiguration();
            equalsHashCodePlugin.setConfigurationType(EqualsHashCodePlugin.class.getName());
            context.addPluginConfiguration(equalsHashCodePlugin);
        }
        if (this.useToStringPlugin.isSelected()) {
            PluginConfiguration toStringPlugin = new PluginConfiguration();
            toStringPlugin.setConfigurationType(ToStringPlugin.class.getName());
            context.addPluginConfiguration(toStringPlugin);
        }

        //---------注释----------
        CommentGeneratorConfiguration commentGenerator = new CommentGeneratorConfiguration();
        commentGenerator.addProperty("suppressAllComments", Boolean.toString(false));
        commentGenerator.addProperty("addRemarkComments", Boolean.toString(true));
        commentGenerator.addProperty("suppressDate", Boolean.toString(false));
        if (this.useCommentPlugin.isSelected()) {
            commentGenerator.setConfigurationType(CommentGeneratorPlugin.class.getName());
        }
        context.setCommentGeneratorConfiguration(commentGenerator);

        //---------类型转换----------
        JavaTypeResolverConfiguration javaTypeResolver = new JavaTypeResolverConfiguration();
        javaTypeResolver.addProperty("forceBigDecimals", Boolean.toString(this.forceBigDecimals.isSelected()));
        context.setJavaTypeResolverConfiguration(javaTypeResolver);

        //---------model----------
        JavaModelGeneratorConfiguration javaModelGenerator = new JavaModelGeneratorConfiguration();
        javaModelGenerator.setTargetProject(sourcePath);
        javaModelGenerator.setTargetPackage(this.modelPackage.getText());
        String rootClassText = this.rootClass.getText();
        if (StringUtil.isNotBlank(rootClassText)) {
            javaModelGenerator.addProperty("rootClass", rootClassText);
        }
        javaModelGenerator.addProperty("constructorBased", Boolean.toString(this.constructorBased.isSelected()));
        javaModelGenerator.addProperty("enableSubPackages", Boolean.toString(this.enableModelSubPackages.isSelected()));
        javaModelGenerator.addProperty("immutable", Boolean.toString(this.immutable.isSelected()));
        javaModelGenerator.addProperty("trimStrings", Boolean.toString(this.trimStrings.isSelected()));
        context.setJavaModelGeneratorConfiguration(javaModelGenerator);

        //---------mapper----------
        JavaClientGeneratorConfiguration javaClientGenerator = new JavaClientGeneratorConfiguration();
        javaClientGenerator.setConfigurationType(this.javaClientType.getValue().getValue());
        javaClientGenerator.setTargetProject(sourcePath);
        javaClientGenerator.setTargetPackage(this.mapperPackage.getText());
        boolean subPackagesSelected = this.enableMapperSubPackages.isSelected();
        javaClientGenerator.addProperty("exampleMethodVisibility", this.exampleMethodVisibility.getValue().getValue());
        javaClientGenerator.addProperty("methodNameCalculator", this.methodNameCalculator.getValue().getValue());
        String rootInterfaceText = this.rootInterface.getText();
        if (StringUtil.isNotBlank(rootClassText)) {
            javaClientGenerator.addProperty("rootInterface", rootInterfaceText);
        }
        javaClientGenerator.addProperty("useLegacyBuilder", Boolean.toString(this.useLegacyBuilder.isSelected()));
        javaClientGenerator.addProperty("enableSubPackages", Boolean.toString(subPackagesSelected));
        if (subPackagesSelected) {
            String packageText = this.implementationPackage.getText();
            if (StringUtil.isNotBlank(packageText)) {
                javaClientGenerator.setImplementationPackage(packageText);
            }
        }
        context.setJavaClientGeneratorConfiguration(javaClientGenerator);

        //---------sql----------
        SqlMapGeneratorConfiguration sqlMapGenerator = new SqlMapGeneratorConfiguration();
        sqlMapGenerator.setTargetProject(resourcePath);
        sqlMapGenerator.setTargetPackage(this.sqlMapperPackage.getText());
        context.setSqlMapGeneratorConfiguration(sqlMapGenerator);

        //---------jdbc----------
        JDBCConnectionConfiguration jdbcConnection = new JDBCConnectionConfiguration();
        jdbcConnection.setDriverClass(connectionConfig.getType().driverClass);
        jdbcConnection.setConnectionURL(connectionConfig.getConnectionUrl());
        jdbcConnection.setUserId(connectionConfig.getUsername());
        jdbcConnection.setPassword(connectionConfig.getPassword());
        context.setJdbcConnectionConfiguration(jdbcConnection);

        //---------tables----------
        boolean camelCaseSelected = this.camelCase.isSelected();
        String statement = this.metadataProvider.getConnectionConfig().getType().name;
        String catalog = connectionConfig.getTableCatalog();
        String schema = connectionConfig.getTableSchema();
        tables.getItems().filtered(Selectable::isSelected).forEach(tableMetadata -> {
            TableConfiguration table = new TableConfiguration(context);
            table.setCatalog(catalog);
            table.setSchema(schema);
            table.setTableName(tableMetadata.getName());
//                table.setSelectByExampleStatementEnabled(false);
//                table.setDeleteByExampleStatementEnabled(false);
//                table.setUpdateByExampleStatementEnabled(false);
            table.addProperty("useActualColumnNames", Boolean.toString(!camelCaseSelected));
            table.setGeneratedKey(new GeneratedKey("id", statement, true, ""));
            context.addTableConfiguration(table);
        });
        return context;
    }
}
