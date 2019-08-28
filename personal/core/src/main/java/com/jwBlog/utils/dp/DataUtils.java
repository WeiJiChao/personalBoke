package com.jwBlog.utils.dp;

/**
 */
public class DataUtils {
    /**
     * 返回不为空的字符串
     *
     * @param obj
     * @return
     */
    public static String getString(Object obj) {
        if (obj == null || obj.toString().equals("null")) {
            return "";
        }
        return obj.toString();
    }

    /**
     * 返回不为空的字符串
     *
     * @param obj
     * @param def
     *            默认值
     * @return
     */
    public static String getString(Object obj, String def) {
        if (obj == null || obj.toString().equals("null")) {
            return def;
        }
        return obj.toString().trim();
    }
    public static int parseInt(Object str) {
        return parseInt(str, 0);
    }

    public static int parseInt(Object str, int defaultValue) {
        if (str == null || str.equals("")) {
            return defaultValue;
        }
        String s = str.toString().trim();
        if (!s.matches("-?\\d+")) {
            return defaultValue;
        }
        return Integer.parseInt(s);
    }

    public static long parseLong(Object str) {
        if (str == null || str.equals("")) {
            return 0;
        }
        String s = str.toString().trim();
        if (!s.matches("-?\\d+")) {
            return 0;
        }
        return Long.parseLong(s);
    }
    public static double parseDouble(Object str) {
        if (str == null) {
            return 0;
        }
        String s = str.toString().trim();
        if (!s.matches("-?\\d+(\\.\\d+)?")) {
            return 0;
        }
        return Double.parseDouble(s);
    }

    public static float parseFloat(Object str) {
        if (str == null) {
            return 0;
        }
        String s = str.toString().trim();
        if (!s.matches("-?\\d+(\\.\\d+)?")) {
            return 0;
        }
        return Float.parseFloat(s);
    }
    /**
     * 左补字符0
     *
     * @param str
     * @param length
     * @return
     */
    public static String fill0Left(String str, int length) {
        if (str == null) {
            str = "";
        }
        int len = length - str.length();
        if (len > 0) {
            for (int i = 0; i < len; i++) {
                str = "0" + str;
            }
        }
        return str;
    }
}
