package com.royll.varnamegodie.utils;

import com.intellij.ide.util.PropertiesComponent;

/**
 * Created by Roy on 2016/7/8.
 */
public class PropertiesUtil {

    private static final String PROPERYIES_NAME_LIST = "varnamesettings_list";
    private static final String PROPERYIES_NAME_CHECKBOX = "varnamesettings_check";

    /**
     * 获取默认参数列表
     *
     * @return
     */
    public static String[] getDefaultProperties() {
        return new String[]{"hello", "mHello", "mHelloTextView", "mHelloImageView", "mHelloListView", "mHelloStr", "mHelloBtn", "mHelloView"};
    }

    /**
     * 获取设置参数列表
     *
     * @return
     */
    public static String[] getProperties() {
        if (PropertiesComponent.getInstance().isValueSet(PROPERYIES_NAME_LIST)) {
            return PropertiesComponent.getInstance().getValues(PROPERYIES_NAME_LIST);
        } else {
            return getDefaultProperties();
        }
    }

    /**
     * 保存参数列表
     *
     * @param array
     */
    public static void saveProperties(String[] array) {
        PropertiesComponent.getInstance().setValues(PROPERYIES_NAME_LIST, array);
    }

    /**
     * 保存复选框状态
     *
     * @param selected
     */
    public static void saveCheckBoxSelectState(boolean selected) {
        PropertiesComponent.getInstance().setValue(PROPERYIES_NAME_CHECKBOX, selected);
    }

    /**
     * 获取复选框状态
     *
     * @return
     */
    public static boolean getCheckBoxSelectState() {
        if (PropertiesComponent.getInstance().isValueSet(PROPERYIES_NAME_CHECKBOX)) {
            return Boolean.valueOf(PropertiesComponent.getInstance().getValue(PROPERYIES_NAME_CHECKBOX));
        } else {
            return false;
        }
    }

}
