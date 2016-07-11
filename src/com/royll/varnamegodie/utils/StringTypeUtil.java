package com.royll.varnamegodie.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Roy on 2016/7/6.
 */
public class StringTypeUtil {

    public static final int ONLY_HAS_CHINESE = 1;
    public static final int ONLY_HAS_ENGLISH = 2;
    public static final int BOTH_CH_ENG = 3;

    public static int getStringType(String str) {
        if (hasChinese(str) && hasEnglish(str))
            return BOTH_CH_ENG;
        else if (hasChinese(str) && !hasEnglish(str))
            return ONLY_HAS_CHINESE;
        else if (!hasChinese(str) && hasEnglish(str))
            return ONLY_HAS_ENGLISH;
        else
            return -1;
    }

    //判断是不是英文字母
    public static boolean hasEnglish(String charaString) {
        return charaString.matches(".*\\p{Alpha}.*");
    }

    //根据中文unicode范围判断u4e00 ~ u9fa5不全
    public static boolean hasChinese(String str) {
        String regEx1 = "[\\u4e00-\\u9fa5]+";
        String regEx2 = "[\\uFF00-\\uFFEF]+";
        String regEx3 = "[\\u2E80-\\u2EFF]+";
        String regEx4 = "[\\u3000-\\u303F]+";
        String regEx5 = "[\\u31C0-\\u31EF]+";
        Pattern p1 = Pattern.compile(regEx1);
        Pattern p2 = Pattern.compile(regEx2);
        Pattern p3 = Pattern.compile(regEx3);
        Pattern p4 = Pattern.compile(regEx4);
        Pattern p5 = Pattern.compile(regEx5);
        Matcher m1 = p1.matcher(str);
        Matcher m2 = p2.matcher(str);
        Matcher m3 = p3.matcher(str);
        Matcher m4 = p4.matcher(str);
        Matcher m5 = p5.matcher(str);
        if (m1.find() || m2.find() || m3.find() || m4.find() || m5.find())
            return true;
        else
            return false;
    }
}
