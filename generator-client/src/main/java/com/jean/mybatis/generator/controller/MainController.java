package com.jean.mybatis.generator.controller;

import com.jean.mybatis.generator.constant.*;
import com.jean.mybatis.generator.core.GeneratorService;
import com.jean.mybatis.generator.factory.ITaskFactory;
import com.jean.mybatis.generator.factory.TableCellFactory;
import com.jean.mybatis.generator.plugins.CommentGeneratorPlugin;
import com.jean.mybatis.generator.support.connection.ConnectionConfig;
import com.jean.mybatis.generator.support.meta.CatalogMetaData;
import com.jean.mybatis.generator.support.meta.ColumnMetaData;
import com.jean.mybatis.generator.support.meta.SchemaMetaData;
import com.jean.mybatis.generator.support.meta.TableMetaData;
import com.jean.mybatis.generator.support.provider.IMetaDataProviderManager;
import com.jean.mybatis.generator.support.provider.IMetadataProvider;
import com.jean.mybatis.generator.utils.DialogUtil;
import com.jean.mybatis.generator.utils.StringUtil;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.mybatis.generator.config.*;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.mybatis.generator.plugins.EqualsHashCodePlugin;
import org.mybatis.generator.plugins.SerializablePlugin;
import org.mybatis.generator.plugins.ToStringPlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author jinshubao
 * @date 2017/4/8
 */
@Controller
public class MainController extends BaseController {
    //---------菜单栏----------

    @FXML
    private MenuBar menuBar;
    @FXML
    private Menu connectionMenu;
    @FXML
    private MenuItem newConnectionMenuItem;
    @FXML
    private MenuItem exitMenuItem;
    @FXML
    private Menu configurationMenu;
    @FXML
    private MenuItem loadConfig;
    @FXML
    private Menu helpMenu;
    @FXML
    private MenuItem aboutMenuItem;

    //left

    @FXML
    private ComboBox<CatalogMetaData> tableCatalog;
    @FXML
    private ComboBox<SchemaMetaData> tableSchema;
    @FXML
    private TableView<TableMetaData> tables;
    @FXML
    private Hyperlink selectAll;
    @FXML
    private Hyperlink invertSelection;

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
    public TextField beginningDelimiter;
    @FXML
    public TextField endDelimiter;
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
    @FXML
    private TextField searchString;
    @FXML
    private TextField replaceString;


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
    private Label message;
    @FXML
    private Hyperlink warning;
    @FXML
    private ProgressIndicator progressIndicator;

    //私有变量

    private final FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("XML", "*.xml");
    private final ObjectProperty<ConnectionConfig> connectionConfigProperty = new SimpleObjectProperty<>(this, "connectionConfigProperty");
    @Autowired
    private ConnectionController connectionController;

    @Autowired
    private CustomTableController customTableController;

    @Autowired
    private GeneratorService generatorService;

    @Autowired
    private IMetaDataProviderManager providerManager;

    @Autowired
    private ITaskFactory taskFactory;

    private ResourceBundle resources;

    @Override
    @SuppressWarnings("unchecked")
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;
        this.newConnectionMenuItem.setOnAction(event ->
                DialogUtil.customizeDialog(resources.getString("dialog.newConnection.title"),
                        null,
                        CommonConstant.SCENES.get(StageType.CONNECTION),
                        buttonType -> {
                            if (buttonType == ButtonType.OK) {
                                return connectionController.getConnectionConfig();
                            }
                            return null;
                        }).ifPresent(config -> {

                    connectionConfigProperty.set(config);
                    refreshTableCatalog();
                }));

        this.exitMenuItem.setOnAction(event -> System.exit(0));
        this.loadConfig.setOnAction(event -> {
            try {
                Configuration configuration = loadConfiguration(resources, event);
                if (configuration != null) {
                    logger.debug(configuration.toDocument().getFormattedContent());
                    //TODO 载入已有配置
                }
            } catch (Exception e) {
                showExceptionDialog(resources, e);
            }
        });
        this.helpMenu.setOnAction(event -> {
            //
        });

        this.aboutMenuItem.setOnAction(event -> {

        });

        this.tableCatalog.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                ConnectionConfig config = connectionConfigProperty.get();
                config.setTableCatalog(newValue == null ? null : newValue.getTableCatalog());
                config.setTableSchema(null);
                refreshTableSchema();
                refreshTableItem();
            } catch (Exception e) {
                showExceptionDialog(resources, e);
            }
        });

        this.tableSchema.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                ConnectionConfig config = connectionConfigProperty.get();
                config.setTableSchema(newValue == null ? null : newValue.getTableSchema());
                refreshTableItem();
            } catch (Exception e) {
                showExceptionDialog(resources, e);
            }
        });

        int columnIndex = 0;
        ObservableList<TableColumn<TableMetaData, ?>> tableColumns = tables.getColumns();

        TableColumn<TableMetaData, Boolean> selectColumn = (TableColumn<TableMetaData, Boolean>) tableColumns.get(columnIndex++);
        selectColumn.setText(resources.getString("select.text"));
        selectColumn.setCellFactory(CheckBoxTableCell.forTableColumn(selectColumn));
        selectColumn.setCellValueFactory(param -> param.getValue().selectedProperty());

        TableColumn<TableMetaData, String> tableNameColumn = (TableColumn<TableMetaData, String>) tableColumns.get(columnIndex++);
        tableNameColumn.setText(resources.getString("tableName.text"));
        tableNameColumn.setCellValueFactory(param -> param.getValue().tableNameProperty());

        TableColumn<TableMetaData, String> remarksColumn = (TableColumn<TableMetaData, String>) tableColumns.get(columnIndex++);
        remarksColumn.setText(resources.getString("remarks.text"));
        remarksColumn.setCellValueFactory(param -> param.getValue().remarksProperty());

        TableColumn<TableMetaData, String> customColumn = (TableColumn<TableMetaData, String>) tableColumns.get(columnIndex);
        customColumn.setText(resources.getString("custom.text"));
        customColumn.setCellFactory(TableCellFactory.hyperlinkForTableView(resources.getString("customHtperlink.text"),
                param -> {
                    taskFactory.getColumns(param.getTableName(),
                            columns -> customColumns(param, columns),
                            ex -> showExceptionDialog(resources, ex));
                    return null;
                }));

        this.selectAll.setText(resources.getString("selectAll.text"));
        this.selectAll.setOnAction(event -> selectAll(this.tables.getItems()));
        this.invertSelection.setText(resources.getString("invertSelection.text"));
        this.invertSelection.setOnAction(event -> reverseSelect(this.tables.getItems()));

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

        this.connectionConfigProperty.addListener((observable, oldValue, newValue) -> {
            this.beginningDelimiter.setText(null);
            this.endDelimiter.setText(null);
            if (newValue != null) {
                IMetadataProvider provider = providerManager.getSupportedMetaDataProvider(newValue.getType());
                if (provider != null) {
                    this.beginningDelimiter.setText(provider.getBeginningDelimiter());
                    this.endDelimiter.setText(provider.getEndDelimiter());
                }
            }
        });

        //model

        this.explorerProject.setOnAction(event -> {
            DirectoryChooser chooser = new DirectoryChooser();
            Window window = ((Node) event.getSource()).getScene().getWindow();
            File file = chooser.showDialog(window);
            if (file != null && file.exists() && file.isDirectory()) {
                this.projectDir.setText(file.getAbsolutePath());
            }
        });
        this.sourceDir.setText(CommonConstant.DEFAULT_SOURCE_DIR);
        this.resourcesDir.setText(CommonConstant.DEFAULT_RESOURCE_DIR);
        this.camelCase.setSelected(true);
        this.autoDelimitKeywords.setSelected(false);
        this.forceBigDecimals.setSelected(false);
        this.javaFileEncoding.getItems().addAll(EncodingEnum.values());
        this.javaFileEncoding.getSelectionModel().select(EncodingEnum.UTF8);
        this.constructorBased.setSelected(false);
        final ObjectProperty<TargetRuntime> targetRuntimeProperty = targetRuntime.valueProperty();
        this.constructorBased.disableProperty().bind(Bindings.createBooleanBinding(() ->
                        targetRuntimeProperty.get() != TargetRuntime.MyBatis3 &&
                                targetRuntimeProperty.get() != TargetRuntime.MyBatis3Simple,
                targetRuntimeProperty));

        this.enableModelSubPackages.setSelected(false);
        this.immutable.setSelected(false);
        this.trimStrings.setSelected(false);

        //mapper
        BooleanBinding mybatis3Binding = Bindings.createBooleanBinding(() ->
                targetRuntimeProperty.get() == TargetRuntime.MyBatis3, targetRuntimeProperty);
        this.enableMapperSubPackages.setSelected(false);
        this.implementationPackage.disableProperty().bind(this.enableMapperSubPackages.selectedProperty().not());
        this.exampleMethodVisibility.getItems().addAll(MethodVisibility.values());
        this.exampleMethodVisibility.getSelectionModel().selectFirst();
        this.exampleMethodVisibility.disableProperty().bind(mybatis3Binding);
        this.methodNameCalculator.getItems().addAll(NameCalculator.values());
        this.methodNameCalculator.getSelectionModel().selectFirst();
        this.methodNameCalculator.disableProperty().bind(mybatis3Binding);

        this.generatorService.messageProperty().addListener((observable, oldValue, newValue) -> logger.debug(newValue));
        this.generatorService.setOnFailed(event -> {
            Throwable e = this.generatorService.getException();
            showExceptionDialog(resources, e);
        });

        this.generatorService.setOnSucceeded(event -> {
            //什么都不做
        });

        //生成代码
        this.generate.disableProperty().bind(
                this.connectionConfigProperty.isNull()
                        .or(this.generatorService.runningProperty())
                        .or(this.projectDir.textProperty().isEmpty())
                        .or(this.modelPackage.textProperty().isEmpty())
                        .or(this.mapperPackage.textProperty().isEmpty())
                        .or(this.sqlMapperPackage.textProperty().isEmpty()));
        this.generate.setOnAction((ActionEvent event) ->
                this.generatorService.restart(createDefaultConfiguration(createDefaultContext()),
                        new DefaultShellCallback(overwrite.isSelected())));

        //保存配置
        this.saveConfig.disableProperty().bind(connectionConfigProperty.isNull());
        this.saveConfig.setOnAction((ActionEvent event) -> {
            try {
                saveConfiguration(resources, event);
            } catch (IOException e) {
                showExceptionDialog(resources, e);
            }
        });

        // bottom
        final ReadOnlyObjectProperty<List<String>> valueProperty = generatorService.valueProperty();
        this.message.textProperty().bind(this.generatorService.messageProperty());
        //警告数量
        this.warning.textFillProperty().bind(Bindings.createObjectBinding(() -> CollectionUtils.isEmpty(valueProperty.get()) ? null : Color.RED, valueProperty));
        this.warning.textProperty().bind(Bindings.createStringBinding(() -> "WARNING(" + (CollectionUtils.isEmpty(valueProperty.get()) ? 0 : valueProperty.get().size()) + ")", valueProperty));
        //没有警告隐藏标签
        this.warning.visibleProperty().bind(Bindings.createBooleanBinding(() -> !CollectionUtils.isEmpty(valueProperty.get()), valueProperty));
        this.warning.setOnAction(event -> DialogUtil.warning("WARNING", "", StringUtil.join(valueProperty.get(), CommonConstant.NEW_LINE)));
        this.progressIndicator.visibleProperty().bind(this.generatorService.runningProperty());
    }

    private void customColumns(TableMetaData param, List<ColumnMetaData> columns) {
        for (ColumnMetaData columnMetaData : columns) {
            columnMetaData.setSelected(true);
            columnMetaData.setJavaType("");
            columnMetaData.setJavaType("");
            for (ColumnOverride override : param.getColumnOverrides()) {
                if (override.getColumnName().equals(columnMetaData.getColumnName())) {
                    columnMetaData.setJavaType(override.getJavaType());
                    columnMetaData.setJavaProperty(override.getJavaProperty());
                }
            }
            for (IgnoredColumn ignoredColumn : param.getIgnoredColumns()) {
                if (ignoredColumn.getColumnName().equals(columnMetaData.getColumnName())) {
                    columnMetaData.setSelected(false);
                }
            }
        }
        customTableController.initColumns(columns);
        DialogUtil.customizeDialog(resources.getString("customHtperlink.text"),
                resources.getString("dialog.customTable.header"),
                CommonConstant.SCENES.get(StageType.CUSTOM_TABLE),
                buttonType -> {
                    if (buttonType == ButtonType.OK) {
                        return customTableController.getColumns();
                    }
                    return null;
                })
                .ifPresent(value -> {
                    param.getColumnOverrides().clear();
                    param.getIgnoredColumns().clear();
                    for (ColumnMetaData metaData : value) {
                        if (metaData.isSelected()) {
                            boolean hasValue = false;
                            ColumnOverride override = new ColumnOverride(metaData.getColumnName());
                            if (StringUtil.isNotBlank(metaData.getJavaType())
                                    && !CommonConstant.DEFAULT.equals(metaData.getJavaType())) {
                                override.setJavaType(metaData.getJavaType());
                                hasValue = true;
                            }
                            if (StringUtil.isNotBlank(metaData.getJavaProperty()) &&
                                    !CommonConstant.DEFAULT.equals(metaData.getJavaProperty())) {
                                override.setJavaProperty(metaData.getJavaProperty());
                                hasValue = true;
                            }
                            if (hasValue) {
                                param.getColumnOverrides().add(override);
                            }
                        } else {
                            IgnoredColumn column = new IgnoredColumn(metaData.getColumnName());
                            param.getIgnoredColumns().add(column);
                        }
                    }
                });
    }

    private void refreshTableCatalog() {
        this.tableCatalog.getSelectionModel().clearSelection();
        this.tableCatalog.getItems().clear();
        taskFactory.setConnectionConfig(connectionConfigProperty.get());
        taskFactory.getCatalogs(value -> tableCatalog.getItems().addAll(value), ex -> showExceptionDialog(resources, ex));

    }

    private void refreshTableSchema() {
        this.tableSchema.getSelectionModel().clearSelection();
        this.tableSchema.getItems().clear();
        taskFactory.setConnectionConfig(connectionConfigProperty.get());
        taskFactory.getSchemas(value -> tableSchema.getItems().addAll(value), ex -> showExceptionDialog(resources, ex));
    }


    private void refreshTableItem() {
        this.tables.getSelectionModel().clearSelection();
        this.tables.getItems().clear();
        taskFactory.setConnectionConfig(connectionConfigProperty.get());
        taskFactory.getTables(value -> tables.getItems().addAll(value), ex -> showExceptionDialog(resources, ex));
    }

    /**
     * 加载配置文件
     *
     * @param resources 资源
     * @param event     事件
     * @return 配置
     * @throws IOException        IOException
     * @throws XMLParserException XMLParserException
     */
    private Configuration loadConfiguration(ResourceBundle resources, ActionEvent event) throws IOException, XMLParserException {
        List<String> warnings = new ArrayList<>();
        FileChooser chooser = new FileChooser();
        chooser.setTitle(resources.getString("loadConfiguration.chooser.title"));
        chooser.setInitialDirectory(new File(System.getProperty(CommonConstant.USER_HOME)));
        chooser.getExtensionFilters().add(extensionFilter);
        Object source = event.getSource();
        Window window = null;
        if (source instanceof MenuItem) {
            window = ((MenuItem) source).getParentPopup().getScene().getWindow();
        } else if (source instanceof Node) {
            window = ((Node) source).getScene().getWindow();
        }
        File configFile = chooser.showOpenDialog(window);
        if (configFile != null && configFile.exists() && configFile.isFile()) {
            ConfigurationParser cp = new ConfigurationParser(warnings);
            Configuration configuration = cp.parseConfiguration(configFile);
            if (!warnings.isEmpty()) {
                logger.warn(StringUtil.join(warnings, "; "));
            }
            return configuration;
        }
        return null;
    }

    private void saveConfiguration(ResourceBundle resources, ActionEvent event) throws IOException {
        FileChooser chooser = new FileChooser();
        chooser.setTitle(resources.getString("saveConfiguration.chooser.title"));
        chooser.setInitialFileName(CommonConstant.CONFIGURATION_NAME);
        chooser.setInitialDirectory(new File(System.getProperty(CommonConstant.USER_HOME)));
        chooser.getExtensionFilters().addAll(extensionFilter);
        File dir = chooser.showSaveDialog(((ButtonBase) event.getSource()).getScene().getWindow());
        if (dir != null) {
            File config = new File(dir.getAbsolutePath());
            FileOutputStream fs = null;
            BufferedOutputStream bos = null;
            try {
                fs = new FileOutputStream(config);
                bos = new BufferedOutputStream(fs);
                Configuration configuration = createDefaultConfiguration(createDefaultContext());
                String content = configuration.toDocument().getFormattedContent();
                bos.write(content.getBytes());
                bos.flush();
                logger.debug(content);
            } finally {
                if (fs != null) {
                    try {
                        fs.close();
                    } catch (IOException e) {
                        logger.error(e.getMessage(), e);
                    }
                }
                if (bos != null) {
                    try {
                        bos.close();
                    } catch (IOException e) {
                        logger.error(e.getMessage(), e);
                    }
                }
            }
        }
    }

    private Configuration createDefaultConfiguration(Context context) {
        Configuration configuration = new Configuration();
        configuration.addContext(context);
        return configuration;
    }

    private Context createDefaultContext() {

        IMetadataProvider provider = providerManager.getSupportedMetaDataProvider(connectionConfigProperty.get().getType());

        //---------上下文环境----------
        Context context = new Context(this.defaultModelType.getValue());
        context.setId("context");
        context.setTargetRuntime(this.targetRuntime.getValue().getValue());
        context.addProperty(PropertyRegistry.CONTEXT_BEGINNING_DELIMITER, this.beginningDelimiter.getText());
        context.addProperty(PropertyRegistry.CONTEXT_ENDING_DELIMITER, this.endDelimiter.getText());
        context.addProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING, this.javaFileEncoding.getValue().value.name());

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
        commentGenerator.addProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_ALL_COMMENTS, Boolean.toString(false));
        commentGenerator.addProperty(PropertyRegistry.COMMENT_GENERATOR_ADD_REMARK_COMMENTS, Boolean.toString(true));
        commentGenerator.addProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_DATE, Boolean.toString(false));
        if (this.useCommentPlugin.isSelected()) {
            commentGenerator.setConfigurationType(CommentGeneratorPlugin.class.getName());
        }
        context.setCommentGeneratorConfiguration(commentGenerator);

        //---------类型转换----------
        JavaTypeResolverConfiguration javaTypeResolver = new JavaTypeResolverConfiguration();
        javaTypeResolver.addProperty(PropertyRegistry.TYPE_RESOLVER_FORCE_BIG_DECIMALS, Boolean.toString(this.forceBigDecimals.isSelected()));
        context.setJavaTypeResolverConfiguration(javaTypeResolver);

        //---------model----------
        JavaModelGeneratorConfiguration javaModelGenerator = new JavaModelGeneratorConfiguration();
        javaModelGenerator.setTargetProject(sourcePath);
        javaModelGenerator.setTargetPackage(this.modelPackage.getText());
        String rootClassText = this.rootClass.getText();
        if (StringUtil.isNotBlank(rootClassText)) {
            javaModelGenerator.addProperty(PropertyRegistry.ANY_ROOT_CLASS, rootClassText);
        }
        javaModelGenerator.addProperty(PropertyRegistry.ANY_CONSTRUCTOR_BASED, Boolean.toString(this.constructorBased.isSelected()));
        javaModelGenerator.addProperty(PropertyRegistry.ANY_ENABLE_SUB_PACKAGES, Boolean.toString(this.enableModelSubPackages.isSelected()));
        javaModelGenerator.addProperty(PropertyRegistry.ANY_IMMUTABLE, Boolean.toString(this.immutable.isSelected()));
        javaModelGenerator.addProperty(PropertyRegistry.MODEL_GENERATOR_TRIM_STRINGS, Boolean.toString(this.trimStrings.isSelected()));
        context.setJavaModelGeneratorConfiguration(javaModelGenerator);

        //---------mapper----------
        JavaClientGeneratorConfiguration javaClientGenerator = new JavaClientGeneratorConfiguration();
        javaClientGenerator.setConfigurationType(this.javaClientType.getValue().getValue());
        javaClientGenerator.setTargetProject(sourcePath);
        javaClientGenerator.setTargetPackage(this.mapperPackage.getText());
        boolean subPackagesSelected = this.enableMapperSubPackages.isSelected();
        javaClientGenerator.addProperty(PropertyRegistry.DAO_EXAMPLE_METHOD_VISIBILITY, this.exampleMethodVisibility.getValue().getValue());
        javaClientGenerator.addProperty(PropertyRegistry.DAO_METHOD_NAME_CALCULATOR, this.methodNameCalculator.getValue().getValue());
        String rootInterfaceText = this.rootInterface.getText();
        if (StringUtil.isNotBlank(rootClassText)) {
            javaClientGenerator.addProperty(PropertyRegistry.ANY_ROOT_INTERFACE, rootInterfaceText);
        }
        javaClientGenerator.addProperty(PropertyRegistry.CLIENT_USE_LEGACY_BUILDER, Boolean.toString(this.useLegacyBuilder.isSelected()));
        javaClientGenerator.addProperty(PropertyRegistry.ANY_ENABLE_SUB_PACKAGES, Boolean.toString(subPackagesSelected));
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
        ConnectionConfig connectionConfig = provider.getConnectionConfig();

        JDBCConnectionConfiguration jdbcConnection = new JDBCConnectionConfiguration();
        jdbcConnection.setDriverClass(connectionConfig.getType().driverClass);
        jdbcConnection.setConnectionURL(provider.getConnectionURL());
        jdbcConnection.setUserId(connectionConfig.getUser());
        jdbcConnection.setPassword(connectionConfig.getPassword());
        context.setJdbcConnectionConfiguration(jdbcConnection);

        //---------tables----------

        ColumnRenamingRule columnRenamingRule = null;
        String searchStringText = this.searchString.getText();
        if (StringUtil.isNotBlank(searchStringText)) {
            columnRenamingRule = new ColumnRenamingRule();
            columnRenamingRule.setSearchString(searchStringText);
            columnRenamingRule.setReplaceString(this.replaceString.getText());
        }

        boolean camelCaseSelected = this.camelCase.isSelected();
        String statement = connectionConfig.getType().name;
        for (TableMetaData tableMetadata : tables.getItems()) {
            if (tableMetadata.isSelected()) {
                TableConfiguration table = new TableConfiguration(context);
                table.setTableName(tableMetadata.getTableName());
                table.addProperty(PropertyRegistry.TABLE_USE_ACTUAL_COLUMN_NAMES, Boolean.toString(!camelCaseSelected));
                if (StringUtil.isNotBlank(tableMetadata.getPrimaryKeyColumn())) {
                    table.setGeneratedKey(new GeneratedKey(tableMetadata.getPrimaryKeyColumn(), statement, true, "post"));
                }
                tableMetadata.getIgnoredColumns().forEach(table::addIgnoredColumn);
                tableMetadata.getColumnOverrides().forEach(table::addColumnOverride);
                if (columnRenamingRule != null) {
                    table.setColumnRenamingRule(columnRenamingRule);
                }
                context.addTableConfiguration(table);
            }
        }
        return context;
    }

}
