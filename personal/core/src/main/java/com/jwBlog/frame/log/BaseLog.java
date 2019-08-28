package com.jwBlog.frame.log;


import com.jwBlog.base.Constants;
import com.jwBlog.utils.dp.DateUtils;

import java.io.File;

/**
 * devFrame Created by 许林 on 2017/10/17.
 */
public abstract class BaseLog {
    protected String currentTime="";
    protected static String basePath="";
    public BaseLog(){
        initParentPath();
        currentTime= DateUtils.toString("yyyy-MM-dd");
    }
    private static void initParentPath(){
        basePath= Constants.param_default_log_path+"Logs/";
        File file = new File(basePath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }
    protected abstract void clear();
}
