package com.jean.mybatis.generator.plugins

import org.mybatis.generator.api.CommentGenerator
import org.mybatis.generator.api.IntrospectedColumn
import org.mybatis.generator.api.IntrospectedTable
import org.mybatis.generator.api.dom.java.*
import org.mybatis.generator.api.dom.xml.XmlElement

/**
 * Created by jinshubao on 2017/4/14.
 */
class CommentGeneratorPlugin implements CommentGenerator {

    @Override
    void addConfigurationProperties(Properties properties) {

    }

    @Override
    void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        field.getJavaDocLines().clear()
        field.addJavaDocLine("/**")
        field.addJavaDocLine(" * ${introspectedColumn.getRemarks()}")
        field.addJavaDocLine(" */")
    }

    @Override
    void addFieldComment(Field field, IntrospectedTable introspectedTable) {

    }

    @Override
    void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        topLevelClass.getJavaDocLines().clear()
        topLevelClass.addJavaDocLine("/**")
        topLevelClass.addJavaDocLine(" * ${introspectedTable.getRemarks()}")
        topLevelClass.addJavaDocLine(" */")
    }

    @Override
    void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {

    }

    @Override
    void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean markAsDoNotDelete) {

    }

    @Override
    void addEnumComment(InnerEnum innerEnum, IntrospectedTable introspectedTable) {

    }

    @Override
    void addGetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {

    }

    @Override
    void addSetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {

    }

    @Override
    void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {

    }

    @Override
    void addJavaFileComment(CompilationUnit compilationUnit) {

    }

    @Override
    void addComment(XmlElement xmlElement) {

    }

    @Override
    void addRootComment(XmlElement rootElement) {

    }
}