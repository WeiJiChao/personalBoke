package com.jwBlog.base;

/**
 * Created by 许林 on 2016/2/15.
 */
public class Constants {
    public static final String BUNDLE_KEY = "ApplicationResources";
    public static final String CONFIG = "xlylConfig";
    public static final String ASSETS_VERSION = "assetsVersion";
    /**
     * 缓存前缀
     */
    public static final String Redis_Key_Default = "basic:";
    /**
     * 用户缓存
     */
    public static final String USER_CACHE=Redis_Key_Default+"userCache";

    /**
     * 参数缓存
     */
    public static final String PARAM_CACHE=Redis_Key_Default+"paramCache";
    /**
     * UI版本号
     */
    public static String UIVersion = "default";
    public static String currentMasterDatabase="";
    /**
     * 基础配置数据
     */
    public static String param_default_dateFormat="yyyy-MM-dd";
    public static String param_default_dateTimeFormat="yyyy-MM-dd HH:mm:ss";
    /**
     *
     */
    public static int param_default_pageSize=20;
    /**
     * 系统默认密码
     */
    public static final String param_default_password="111111";
    /**
     * 系统默认密码
     */
    public static final Integer param_admin_user=1;
    //系统配置参数
    public static String param_default_application_path="";
    public static String param_default_upload_path="";
    public static String param_default_download_path="";
    public static String  param_default_log_path="";
}
