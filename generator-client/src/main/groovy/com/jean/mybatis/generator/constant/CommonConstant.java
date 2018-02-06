package com.jean.mybatis.generator.constant;

import javafx.scene.Parent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jinshubao on 2017/4/8.
 */
public class CommonConstant {

    public final static Map<String, Parent> SCENES = new ConcurrentHashMap<>();


    public static class MethodVisibility {
        public static final String PUBLIC = "public";
        public static final String PRIVATE = "private";
        public static final String PROTECTED = "protected";
        public static final String DEFAULT = "default";
    }

}
