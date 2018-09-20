package com.jean.mybatis.generator.utils;

import java.io.File;
import java.util.*;

/**
 * @author jinshubao
 */
public abstract class StringUtil {


    public static boolean isNotBlank(String text) {
        return !isBlank(text);
    }

    public static boolean isBlank(String text) {
        return text == null || text.length() == 0;
    }

    public static String join(Object[] params, String sp) {
        return join(Arrays.asList(params), sp);
    }

    public static String join(Iterable iterable, String sp) {
        if (iterable == null) {
            return "";
        }
        StringBuilder buffer = new StringBuilder();
        boolean spNotBlank = isNotBlank(sp);
        boolean first = true;
        for (Object item : iterable) {
            if (!first && spNotBlank) {
                buffer.append(sp);
            }
            buffer.append(item);
            first = false;
        }
        return buffer.toString();
    }

    public static String toPath(String... dirs) {
        List<String> list = new ArrayList<>();
        for (String dir : dirs) {
            if (isNotBlank(dir)) {
                if (dir.startsWith(File.separator)) {
                    dir = dir.substring(1);
                }
                if (dir.endsWith(File.separator)) {
                    dir = dir.substring(0, dir.length() - 2);
                }
                list.add(dir);
            }
        }
        return join(list, File.separator);
    }
}
