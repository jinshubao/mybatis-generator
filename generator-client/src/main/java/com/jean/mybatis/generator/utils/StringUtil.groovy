package com.jean.mybatis.generator.utils;

public class StringUtil {

    public static String toCamelCase(String str, boolean toCamelCase) {
        if (str) {
            if (toCamelCase) {
                return str.split("_").collect({
                    uppercaseFirst(it, true)
                }).join("")
            } else {
                return str.collect {
                    it == it.toUpperCase() ? "_" + it.toLowerCase() : it
                }.join("").substring(1)
            }
        }
        return str
    }

    public static String uppercaseFirst(String str, boolean uppercase) {
        if (str) {
            def s = str.substring(1)
            if (uppercase) {
                return str.charAt(0).toUpperCase().toString() + s
            } else {
                str.charAt(0).toLowerCase().toString() + s
            }
        }
        return str
    }


    public static Map<String, String> parseProperties(String properties) {
        Map<String, String> map = new HashMap<>()
        if (properties != null && !properties.isEmpty()) {
            String[] props = properties.split("&")
            for (String prop : props) {
                String[] sp = prop.split("=")
                if (sp.length == 2) {
                    map.put(sp[0].trim(), sp[1].trim())
                }
            }
        }
        return map
    }

    public static String expandProperties(Map proper) {
        def props = []
        if (proper) {
            proper.each { key, value ->
                props << key + "=" + value
            }
        }
        props.join("&")
    }

    public static boolean isNotBlank(String text) {
        return text != null && !text.trim().isEmpty()
    }

    static String join(Collection collection, String sp) {
        if (collection) {
            return collection.join(sp)
        }
        return null
    }

    public static String toPath(String... dirs) {
        def list = []
        for (String dir : dirs) {
            if (isNotBlank(dir)) {
                if (dir.startsWith(File.separator)) {
                    dir = dir.substring(1)
                }
                if (dir.endsWith(File.separator)) {
                    dir.substring(0, dir.length() - 2)
                }
                list.add(dir)
            }
        }
        return list.join(File.separator)
    }
}
