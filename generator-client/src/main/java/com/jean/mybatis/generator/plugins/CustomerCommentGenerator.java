package com.jean.mybatis.generator.plugins;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.internal.DefaultCommentGenerator;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.Properties;

import static org.mybatis.generator.internal.util.StringUtility.isTrue;

/**
 * mybatis 注释插件
 *
 * @author jinshubao
 * @date 2017/4/14
 */
public class CustomerCommentGenerator extends DefaultCommentGenerator {

    protected Properties properties = new Properties();

    protected boolean suppressDate = false;

    protected boolean suppressAllComments = false;

    protected boolean addRemarkComments = false;

    @Override
    public void addConfigurationProperties(Properties properties) {
        suppressDate = isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_DATE));
        suppressAllComments = isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_ALL_COMMENTS));
        addRemarkComments = isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_ADD_REMARK_COMMENTS));
        super.addConfigurationProperties(properties);
    }


    @Override
    public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        if (suppressAllComments || !addRemarkComments) {
            return;
        }
        topLevelClass.addJavaDocLine("/**");
        String remarks = introspectedTable.getRemarks();
        if (addRemarkComments && StringUtility.stringHasValue(remarks)) {
            String[] remarkLines = remarks.split(System.getProperty("line.separator"));
            for (String remarkLine : remarkLines) {
                topLevelClass.addJavaDocLine(" * " + remarkLine);
            }
        }
        topLevelClass.addJavaDocLine(" *");
        topLevelClass.addJavaDocLine(" * 表：" + introspectedTable.getFullyQualifiedTable());

        addJavadocTag(topLevelClass, true);

        topLevelClass.addJavaDocLine(" */");
    }

    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {
        this.addClassComment(innerClass, introspectedTable, false);
    }

    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean markAsDoNotDelete) {
        if (suppressAllComments) {
            return;
        }
        innerClass.addJavaDocLine("/**");
        innerClass.addJavaDocLine(" * 表：" + introspectedTable.getFullyQualifiedTable());

        addJavadocTag(innerClass, markAsDoNotDelete);

        innerClass.addJavaDocLine(" */");
    }

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }
        field.addJavaDocLine("/**");
        String remarks = introspectedColumn.getRemarks();
        if (addRemarkComments && StringUtility.stringHasValue(remarks)) {
            String[] remarkLines = remarks.split(System.getProperty("line.separator"));
            for (String remarkLine : remarkLines) {
                field.addJavaDocLine(" * " + remarkLine);
            }
        }
        field.addJavaDocLine(" *");
        field.addJavaDocLine(" * 列：" + introspectedColumn.getActualColumnName());

        addJavadocTag(field, false);

        field.addJavaDocLine(" */");
    }


    @Override
    public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }
        method.addJavaDocLine("/**");
        addJavadocTag(method, false);
        method.addJavaDocLine(" */");
    }

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }
        field.addJavaDocLine("/**");
        addJavadocTag(field, false);
        field.addJavaDocLine(" */");
    }

    @Override
    public void addGetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }
        method.addJavaDocLine("/**");
        method.addJavaDocLine(" * 获取列" + introspectedColumn.getActualColumnName() + "的值");
        addJavadocTag(method, false);
        method.addJavaDocLine(" */");
    }

    @Override
    public void addSetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }
        method.addJavaDocLine("/**");
        method.addJavaDocLine(" * 设置列" + introspectedColumn.getActualColumnName() + "的值");
        addJavadocTag(method, false);
        method.addJavaDocLine(" */");
    }

    @Override
    public void addJavaFileComment(CompilationUnit compilationUnit) {
        //类文件注释信息
    }

    @Override
    protected void addJavadocTag(JavaElement javaElement, boolean markAsDoNotDelete) {
        if (markAsDoNotDelete) {
            javaElement.addJavaDocLine(" * ");
            javaElement.addJavaDocLine(" * (请勿删除)");
        }
        String s = getDateString();
        if (s != null) {
            javaElement.addJavaDocLine(" * ");
            javaElement.addJavaDocLine(" * " + s);
        }
    }

    @Override
    public void addEnumComment(InnerEnum innerEnum, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }
        innerEnum.addJavaDocLine("/**");
        addJavadocTag(innerEnum, false);
        innerEnum.addJavaDocLine(" */");
    }

}