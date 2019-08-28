package com.jwBlog.frame.exception;

/**
 * devFrame Created by 许林 on 2017/2/28.
 */
public class MyDataCheckException extends Exception {
    private Object exceptionObject;
    private Exception exception;
    public MyDataCheckException(Exception exception) {
        super(exception.getMessage());this.exception=exception;
    }
    public MyDataCheckException(String msg) {
        super(msg);
    }

    public MyDataCheckException(String msg, Throwable t) {
        super(msg, t);
    }
    public MyDataCheckException(String msg, Object exceptionObject) {
        super(msg);
        this.exceptionObject=exceptionObject;
    }
    public MyDataCheckException(String msg, Throwable t, Object exceptionObject) {
        super(msg, t);
        this.exceptionObject=exceptionObject;
    }
    public Object getExceptionObject(){
        return exceptionObject;
    }
    public String dealException() {
        exception.printStackTrace();
        return "系统内部错误";
    }
}
