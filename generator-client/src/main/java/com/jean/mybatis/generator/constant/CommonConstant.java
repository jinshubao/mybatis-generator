package com.jean.mybatis.generator.constant;

import javafx.scene.Parent;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jinshubao on 2017/4/8.
 */
public class CommonConstant {

    public final static Map<StageType, Parent> SCENES = new ConcurrentHashMap<>();

    public static final String LOGO_IMAGE = "/image/mybatis-logo.png";

    public static final String DEFAULT_RESOURCE_DIR = "src" + File.separator + "main" + File.separator + "resources";
    public static final String DEFAULT_SOURCE_DIR = "src" + File.separator + "main" + File.separator + "java";


    public static final String DEFAULT = "自动生成";

    public static class JavaType {
        public static final String AUTO = "";
        public static final String STRING = "String";
        public static final String INTEGER = "Integer";
        public static final String LONG = "Long";
        public static final String BIGDECIMAL = "BigDecimal";
        public static final String DOUBLE = "Double";
        public static final String FLOAT = "Float";
        public static final String BOOLEAN = "Boolean";
        public static final String SHORT = "Short";
        public static final String BYTE = "Byte";
    }
}
