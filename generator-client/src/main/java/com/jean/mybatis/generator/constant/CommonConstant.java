package com.jean.mybatis.generator.constant;

import javafx.scene.Parent;
import org.springframework.scheduling.concurrent.DefaultManagedAwareThreadFactory;

import java.io.File;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author jinshubao
 * @date 2017/4/8
 */
public class CommonConstant {

    public final static Map<StageType, Parent> SCENES = new ConcurrentHashMap<>();

    public static final String LOGO_IMAGE = "/image/mybatis-logo.png";

    public static final String DEFAULT_RESOURCE_DIR = "src" + File.separator + "main" + File.separator + "resources";
    public static final String DEFAULT_SOURCE_DIR = "src" + File.separator + "main" + File.separator + "java";


    public static final String DEFAULT = "自动生成";

    public static final String USER_HOME = "user.home";

    public static final String CONFIGURATION_NAME = "mybatis-configuration";

    public static String NEW_LINE = System.getProperty("line.separator");

    /**
     * java type
     */
    public static class JavaType {
        public static final String AUTO = "";
        public static final String STRING = "String";
        public static final String INTEGER = "Integer";
        public static final String LONG = "Long";
        public static final String BIG_DECIMAL = "BigDecimal";
        public static final String DOUBLE = "Double";
        public static final String FLOAT = "Float";
        public static final String BOOLEAN = "Boolean";
        public static final String SHORT = "Short";
        public static final String BYTE = "Byte";
    }

    /**
     * meta data type
     */
    public static class MetaDataType {
        public static final String TABLE_NAME = "TABLE_NAME";
        public static final String TABLE_SCHEM = "TABLE_SCHEM";
        public static final String TABLE_TYPE = "TABLE_TYPE";
        public static final String REMARKS = "REMARKS";
        public static final String DATA_TYPE = "DATA_TYPE";
        public static final String TYPE_NAME = "TYPE_NAME";
        public static final String COLUMN_SIZE = "COLUMN_SIZE";
        public static final String DECIMAL_DIGITS = "DECIMAL_DIGITS";
        public static final String NUM_PREC_RADIX = "NUM_PREC_RADIX";
        public static final String NULLABLE = "NULLABLE";
        public static final String COLUMN_DEF = "COLUMN_DEF";
        public static final String CHAR_OCTET_LENGTH = "CHAR_OCTET_LENGTH";
        public static final String IS_NULLABLE = "IS_NULLABLE";
        public static final String TABLE_CAT = "TABLE_CAT";
        public static final String COLUMN_NAME = "COLUMN_NAME";
    }
}
