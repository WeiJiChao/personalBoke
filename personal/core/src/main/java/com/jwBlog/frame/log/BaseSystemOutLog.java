package com.jwBlog.frame.log;

/**
 * devFrame Created by 许林 on 2017/10/17.
 */
public class BaseSystemOutLog {
    public static final String LogType_Info="Info";//过程日志
    public static final String LogType_InfoBusi="InfoBusi";//业务处理过程日志
    public static final String LogType_InfoMustSHOW="InfoMust";//必须显示的过程日志
    public static final String LogType_Debug="debug";//调试日志
    public static final String LogType_Error="error";//错误
    public static final String LogType_ErrorIGNO="errorIgnorable";//可忽略的错误
    public static final String LogType_ErrorMUSTSHOW="errorMust";//必须显示的错误日志
    public static void writeLog(String type,String className,String method,String content){
        if(LogType_Info.equals(type)){
            DefaultLog.init().saveLog(className,method,content,null);
//            System.out.println(DateUtils.toString("yyyy-MM-dd HH:mm:ss")+"-"+className+method+content);
        }else if(LogType_InfoBusi.equals(type)){
            DefaultLog.init().saveLog(className,method,content,null);
//            System.out.println(DateUtils.toString("yyyy-MM-dd HH:mm:ss")+"-"+className+method+content);
        }else if(LogType_InfoMustSHOW.equals(type)){
            DefaultLog.init().saveLog(className,method,content,null);
//            System.out.println(DateUtils.toString("yyyy-MM-dd HH:mm:ss")+"-"+className+method+content);
        }else if(LogType_Debug.equals(type)){
            DefaultLog.init().saveLog(className,method,content,null);
//            System.out.println(DateUtils.toString("yyyy-MM-dd HH:mm:ss")+"-"+className+method+content);
        }else{
            DefaultLog.init().saveLog(className,method,content,null);
//            System.out.println(DateUtils.toString("yyyy-MM-dd HH:mm:ss")+"-"+className+method+content);
        }
    }
    public static void writeLog(String type,String className,String method,String content,Exception e){
        try {
            if (LogType_Error.equals(type)) {
                if (e == null || e.getMessage() == null) {
                    DefaultLog.init().saveError3Log(className, method, content + "--" + (e == null ? "null" : (e.getMessage() == null ? "getMessageNull" : "null")), e);
                }else if (e.getMessage().contains("Could not open connection")) {
                    DefaultLog.init().saveError2Log(className, method, content, e);
                } else if (e.getMessage().contains("for the right syntax to use near")) {
                    DefaultLog.init().saveError3Log(className, method, content, e);
                } else {
                    DefaultLog.init().saveErrorLog(className, method, content, e);
                }
//            System.out.println(DateUtils.toString("yyyy-MM-dd HH:mm:ss")+"-"+className+method+content+(e==null?"":org.apache.commons.lang.exception.ExceptionUtils.getFullStackTrace(e)));
            } else if (LogType_ErrorIGNO.equals(type)) {
                DefaultLog.init().saveErrorLog(className, method, content, e);
//            System.out.println(DateUtils.toString("yyyy-MM-dd HH:mm:ss")+"-"+className+method+content+(e==null?"":org.apache.commons.lang.exception.ExceptionUtils.getFullStackTrace(e)));
            } else if (LogType_ErrorMUSTSHOW.equals(type)) {
                DefaultLog.init().saveErrorLog(className, method, content, e);
//            System.out.println(DateUtils.toString("yyyy-MM-dd HH:mm:ss")+"-"+className+method+content+(e==null?"":org.apache.commons.lang.exception.ExceptionUtils.getFullStackTrace(e)));
            } else {
                DefaultLog.init().saveErrorLog(className, method, content, e);
//            System.out.println(DateUtils.toString("yyyy-MM-dd HH:mm:ss")+"-"+className+method+content+(e==null?"":org.apache.commons.lang.exception.ExceptionUtils.getFullStackTrace(e)));
            }
        }catch (Exception e1){
            DefaultLog.init().saveErrorLog(className, method, content + "--日志错误--" + e.getMessage() , e);
        }
    }
}
