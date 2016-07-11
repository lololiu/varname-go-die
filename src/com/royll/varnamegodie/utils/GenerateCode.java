package com.royll.varnamegodie.utils;


import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Roy on 2016/7/7.
 */
public class GenerateCode {
    public static String[] settingArray;
    private static boolean isCheckBoxSelected;


    public static Vector getGenerateCode(String str) {
        settingArray = PropertiesUtil.getProperties();
        isCheckBoxSelected = PropertiesUtil.getCheckBoxSelectState();
        Vector vector = new Vector();
        if (isCheckBoxSelected) {
            vector.add(genrateUpperCaseStr(str));
        }
        for (int i = 0; i < settingArray.length; i++) {
            if (settingArray[i].contains("hello")) {
                vector.add(settingArray[i].replace("hello", str.toLowerCase()));
            } else if (settingArray[i].contains("Hello")) {
                vector.add(settingArray[i].replace("Hello", genrateFirstUpperCaseStr(str)));
            }
        }
        return vector;

    }

    public static String genrateUpperCaseStr(String str) {
        return replaceSpace(str).toUpperCase();
    }

    public static String genrateFirstUpperCaseStr(String str) {
        StringBuilder sb = new StringBuilder();
        String[] strings = replaceSpace(str).split("_");
        for (String s : strings) {
            sb.append(upperCaseFirstWord(s));
        }
        return sb.toString();
    }

    /**
     * 将字符串中空格替换成下划线
     *
     * @param str
     * @return
     */
    public static String replaceSpace(String str) {
        String regEx = "[' ']+"; // 一个或多个空格
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str.trim());
        String newStr = m.replaceAll("_");
        return newStr;
    }

    public static String upperCaseFirstWord(String str) {
        StringBuilder sb = new StringBuilder(str.toLowerCase());
        sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        return sb.toString();
    }
}
