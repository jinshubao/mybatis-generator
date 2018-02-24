package com.jean.mybatis.generator.controller;

import com.jean.mybatis.generator.constant.*;
import com.jean.mybatis.generator.core.GeneratorService;
import com.jean.mybatis.generator.factory.TableCellFactory;
import com.jean.mybatis.generator.plugins.CommentGeneratorPlugin;
import com.jean.mybatis.generator.support.connection.ConnectionConfig;
import com.jean.mybatis.generator.support.meta.*;
import com.jean.mybatis.generator.support.provider.IMetaDataProviderManager;
import com.jean.mybatis.generator.support.provider.IMetadataProvider;
import com.jean.mybatis.generator.utils.DialogUtil;
import com.jean.mybatis.generator.utils.StringUtil;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
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
    private ComboBox<ICatalogMetaData> tableCatalog;
    @FXML
    private ComboBox<ISchemaMetaData> tableSchema;
    @FXML
    private TableView<ITableMetaData> tables;
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
    private ProgressIndicator progressIndicator;
    @FXML
    private Label message;


    //私有变量

    private ObjectProperty<IMetadataProvider> metadataProvider = new SimpleObjectProperty<>(this, "metadataProvider");

    private final FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("XML", "*.xml");

    @Autowired
    private ConnectionController connectionController;

    @Autowired
    private CustomTableController customTableController;

    @Autowired
    private GeneratorService generatorService;

    @Autowired
    private IMetaDataProviderManager providerManager;

    @Override
    @SuppressWarnings("unchecked")
    public void initialize(URL location, ResourceBundle resources) {

        this.newConnectionMenuItem.setOnAction(event ->
                DialogUtil.customizeDialog(resources.getString("dialog.newConnection.title"),
                        null,
                        CommonConstant.SCENES.get(StageType.CONNECTION),
                        param -> {
                            if (param.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                                return connectionController.getConnectionConfig();
                            }
                            return null;
                        }).ifPresent(config -> {
                    try {
                        IMetadataProvider provider = this.providerManager.getMetaDataProvider(config.getType());
                        provider.setConnectionConfig(config);
                        this.metadataProvider.set(provider);
                        refreshTableCatalog(provider);
                    } catch (Exception e) {
                        showException(resources, e);
                    }
                }));

        this.exitMenuItem.setOnAction(event -> System.exit(0));
        this.loadConfig.setOnAction(event -> {
            try {
                Configuration configuration = loadConfiguration(resources, event);
                if (configuration != null) {
                    logger.debug(configuration.toDocument().getFormattedContent());
                }
            } catch (Exception e) {
                showException(resources, e);
            }
        });
        this.helpMenu.setOnAction(event -> {
            //
        });

        this.tableCatalog.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                IMetadataProvider provider = this.metadataProvider.getValue();
                ConnectionConfig config = provider.getConnectionConfig();
                config.setTableCatalog(newValue == null ? null : newValue.getTableCatalog());
                config.setTableSchema(null);
                refreshTableSchema(provider);
                refreshTableItem(provider);
            } catch (Exception e) {
                showException(resources, e);
            }
        });

        this.tableSchema.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                IMetadataProvider provider = this.metadataProvider.getValue();
                ConnectionConfig config = provider.getConnectionConfig();
                config.setTableSchema(newValue == null ? null : newValue.getTableSchema());
                refreshTableItem(provider);
            } catch (Exception e) {
                showException(resources, e);
            }
        });

        int columnIndex = 0;
        ObservableList<TableColumn<ITableMetaData, ?>> tableColumns = tables.getColumns();


        TableColumn<ITableMetaData, Boolean> column0 = (TableColumn<ITableMetaData, Boolean>) tableColumns.get(columnIndex++);
        column0.setText(resources.getString("select.text"));
        column0.setCellFactory(CheckBoxTableCell.forTableColumn(column0));
        column0.setCellValueFactory(param -> param.getValue().selectedProperty());

        TableColumn<ITableMetaData, String> column1 = (TableColumn<ITableMetaData, String>) tableColumns.get(columnIndex++);
        column1.setText(resources.getString("tableName.text"));
        column1.setCellValueFactory(param -> {
            AbstractTableMetaData value = (AbstractTableMetaData) param.getValue();
            return value.tableNameProperty();
        });

        TableColumn<ITableMetaData, String> column2 = (TableColumn<ITableMetaData, String>) tableColumns.get(columnIndex++);
        column2.setText(resources.getString("remarks.text"));
        column2.setCellValueFactory(param -> {
            AbstractTableMetaData value = (AbstractTableMetaData) param.getValue();
            return value.remarksProperty();
        });

        TableColumn<ITableMetaData, String> column3 = (TableColumn<ITableMetaData, String>) tableColumns.get(columnIndex);
        column3.setText(resources.getString("custom.text"));
        column3.setCellFactory(TableCellFactory.hyperlinkForTableView(resources.getString("customHtperlink.text"), param -> {
            try {
                IMetadataProvider provider = this.metadataProvider.getValue();
                List<IColumnMetaData> columns = provider.getColumns(param.getTableName());
                for (IColumnMetaData columnMetaData : columns) {
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
                            param.clearColumnOverrides();
                            param.clearIgnoredColumns();
                            for (IColumnMetaData metaData : value) {
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
                                        param.addColumnOverride(override);
                                    }
                                } else {
                                    IgnoredColumn column = new IgnoredColumn(metaData.getColumnName());
                                    param.addIgnoredColumn(column);
                                }
                            }
                        });
            } catch (Exception e) {
                showException(resources, e);
            }
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

        metadataProvider.addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                this.beginningDelimiter.setText(newValue.getBeginningDelimiter());
                this.endDelimiter.setText(newValue.getEndDelimiter());
            } else {
                this.beginningDelimiter.setText(null);
                this.endDelimiter.setText(null);
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

        this.generatorService.messageProperty().addListener((observable, oldValue, newValue) -> logger.debug(newValue));
        this.generatorService.setOnFailed(event -> {
            Throwable e = this.generatorService.getException();
            showException(resources, e);
        });

        this.generatorService.setOnSucceeded(event -> {
            //什么都不做
        });

        //生成代码
        this.generate.disableProperty().bind(
                this.metadataProvider.isNull()
                        .or(this.generatorService.runningProperty())
                        .or(this.projectDir.textProperty().isEmpty())
                        .or(this.modelPackage.textProperty().isEmpty())
                        .or(this.mapperPackage.textProperty().isEmpty())
                        .or(this.sqlMapperPackage.textProperty().isEmpty()));
        this.generate.setOnAction((ActionEvent event) ->
                this.generatorService.restart(createDefaultConfiguration(createDefaultContext()),
                        new DefaultShellCallback(overwrite.isSelected())));

        //保存配置
        this.saveConfig.disableProperty().bind(metadataProvider.isNull());
        this.saveConfig.setOnAction((ActionEvent event) -> {
            try {
                saveConfiguration(resources, event);
            } catch (IOException e) {
                showException(resources, e);
            }
        });

        // bottom

        this.message.textProperty().bind(this.generatorService.messageProperty());
        this.progressIndicator.visibleProperty().bind(this.generatorService.runningProperty());
    }

    private void refreshTableCatalog(IMetadataProvider provider) throws Exception {
        this.tableCatalog.getSelectionModel().clearSelection();
        this.tableCatalog.getItems().clear();
        this.tableCatalog.getItems().addAll(provider.getCatalogs());
    }

    private void refreshTableSchema(IMetadataProvider provider) throws Exception {
        this.tableSchema.getSelectionModel().clearSelection();
        this.tableSchema.getItems().clear();
        this.tableSchema.getItems().addAll(provider.getSchemas());
    }


    private void refreshTableItem(IMetadataProvider provider) throws Exception {
        this.tables.getSelectionModel().clearSelection();
        this.tables.getItems().clear();
        this.tables.getItems().addAll(provider.getTables());
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
        File configFile = chooser.showOpenDialog(((ButtonBase) event.getSource()).getScene().getWindow());
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

        IMetadataProvider provider = this.metadataProvider.getValue();

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
        for (ITableMetaData tableMetadata : tables.getItems()) {
            if (tableMetadata.isSelected()) {
                TableConfiguration table = new TableConfiguration(context);
                table.setTableName(tableMetadata.getName());
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
