package com.jwBlog.frame.log;
import com.jwBlog.base.Constants;
import com.jwBlog.utils.dp.DateUtils;

import java.io.*;

/**
 * devFrame Created by 许林 on 2017/10/17.
 */
public class DefaultLog extends BaseLog{
    private File logFile;
    private OutputStreamWriter writerStreamLogFile = null;
    private BufferedWriter bufferedWriterLogFile = null;
    private File logErrorFile;
    private OutputStreamWriter writerStreamLogErrorFile = null;
    private BufferedWriter bufferedWriterLogErrorFile = null;
    private File logError2File;
    private OutputStreamWriter writerStreamLogError2File = null;
    private BufferedWriter bufferedWriterLogError2File = null;
    private File logError3File;
    private OutputStreamWriter writerStreamLogError3File = null;
    private BufferedWriter bufferedWriterLogError3File = null;
    private String logPath="";
    public DefaultLog(){
        logPath=basePath+"main/";
        File file = new File(logPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        initFile();
    }
    private static DefaultLog defaultLog;
    public static DefaultLog init(){
        if(defaultLog==null)defaultLog=new DefaultLog();
        return defaultLog;
    }
    private void initFile(){
        String time= DateUtils.toString(Constants.param_default_dateFormat);
        if(!time.equals(currentTime)||(bufferedWriterLogFile==null)) {
            clear();
            currentTime = time;
            logFile = new File(logPath + "main-" + currentTime + ".log");
            logErrorFile = new File(logPath + "error-" + currentTime + ".log");
            logError2File = new File(logPath + "error2-" + currentTime + ".log");
            logError3File = new File(logPath + "error3-" + currentTime + ".log");
            try {
                writerStreamLogFile = new OutputStreamWriter(new FileOutputStream(logFile, true), "utf-8");
                bufferedWriterLogFile = new BufferedWriter(writerStreamLogFile);
                writerStreamLogErrorFile = new OutputStreamWriter(new FileOutputStream(logErrorFile, true), "utf-8");
                bufferedWriterLogErrorFile = new BufferedWriter(writerStreamLogErrorFile);
                writerStreamLogError2File = new OutputStreamWriter(new FileOutputStream(logError2File, true), "utf-8");
                bufferedWriterLogError2File = new BufferedWriter(writerStreamLogError2File);
                writerStreamLogError3File = new OutputStreamWriter(new FileOutputStream(logError3File, true), "utf-8");
                bufferedWriterLogError3File = new BufferedWriter(writerStreamLogError3File);
            } catch (UnsupportedEncodingException e) {
                BaseSystemOutLog.writeLog(BaseSystemOutLog.LogType_ErrorIGNO, "DefaultLog", "initFile", "", e);
            } catch (FileNotFoundException e) {
                BaseSystemOutLog.writeLog(BaseSystemOutLog.LogType_ErrorIGNO, "DefaultLog", "initFile", "", e);
            }
        }
    }
    public void saveLog(String strClass,String strMethod,String strContent,Exception ex){
        initFile();
        try {
            bufferedWriterLogFile.newLine();
            bufferedWriterLogFile.append(DateUtils.toString(Constants.param_default_dateTimeFormat) + " " + strClass + "    " + strMethod + "   " + strContent+(getExceptionInfo(ex)));
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                bufferedWriterLogFile.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void saveErrorLog(String strClass,String strMethod,String strContent,Exception ex){
        initFile();
        try {
            bufferedWriterLogErrorFile.newLine();
            bufferedWriterLogErrorFile.append(DateUtils.toString(Constants.param_default_dateTimeFormat) + " " + strClass + "    " + strMethod + "   " + strContent+(getExceptionInfo(ex)));
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                bufferedWriterLogErrorFile.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void saveError2Log(String strClass,String strMethod,String strContent,Exception ex){
        initFile();
        try {
            bufferedWriterLogError2File.newLine();
            bufferedWriterLogError2File.append(DateUtils.toString(Constants.param_default_dateTimeFormat) + " " + strClass + "    " + strMethod + "   " + strContent+(getExceptionInfo(ex)));
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                bufferedWriterLogError2File.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void saveError3Log(String strClass,String strMethod,String strContent,Exception ex){
        initFile();
        try {
            bufferedWriterLogError3File.newLine();
            bufferedWriterLogError3File.append(DateUtils.toString(Constants.param_default_dateTimeFormat) + " " + strClass + "    " + strMethod + "   " + strContent+(getExceptionInfo(ex)));
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                bufferedWriterLogError3File.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    protected void clear() {
        try{
            if(bufferedWriterLogFile!=null)bufferedWriterLogFile.close();
        }catch (Exception e){}
        try{
            if(writerStreamLogFile!=null)writerStreamLogFile.close();
        }catch (Exception e){}
        bufferedWriterLogFile=null;writerStreamLogFile=null;logFile=null;
        try{
            if(bufferedWriterLogErrorFile!=null)bufferedWriterLogErrorFile.close();
        }catch (Exception e){}
        try{
            if(writerStreamLogErrorFile!=null)writerStreamLogErrorFile.close();
        }catch (Exception e){}
        bufferedWriterLogErrorFile=null;writerStreamLogErrorFile=null;logErrorFile=null;
        try{
            if(bufferedWriterLogError2File!=null)bufferedWriterLogError2File.close();
        }catch (Exception e){}
        try{
            if(writerStreamLogError2File!=null)writerStreamLogError2File.close();
        }catch (Exception e){}
        bufferedWriterLogError2File=null;writerStreamLogError2File=null;logError2File=null;
        try{
            if(bufferedWriterLogError3File!=null)bufferedWriterLogError3File.close();
        }catch (Exception e){}
        try{
            if(writerStreamLogError3File!=null)writerStreamLogError3File.close();
        }catch (Exception e){}
        bufferedWriterLogError3File=null;writerStreamLogError3File=null;logError3File=null;
    }
    public String getExceptionInfo(Exception ex){
        return ex.getMessage();
//        return ex==null?"":org.apache.commons.lang.exception.ExceptionUtils.getFullStackTrace(ex)
    }
}
