package com.jwBlog.utils.file;

import com.jwBlog.base.Constants;
import com.jwBlog.frame.log.BaseSystemOutLog;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * Created by 许林 on 2016/3/29.
 */
public class UpDownloadFile {
    public static String getUploadFilePath(String path){
        return Constants.param_default_upload_path+"upf/"+path;
    }
    public static String getDownloadFilePath(String path){
        return Constants.param_default_download_path+"upf/"+path;
    }
    public static String uploadFile(HttpServletRequest request,String filePath){
        String strNewFileName="";
        try {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("file");
            // 获得文件名：
            String realFileName = file.getOriginalFilename();
            if(realFileName==null||"".equals(realFileName))return "";
            filePath = getUploadFilePath(filePath);
            File dirPath = new File(filePath);
            if (!dirPath.exists()) {
                dirPath.mkdirs();
            }
            strNewFileName=filePath +"/"+ realFileName;
            File uploadFile = new File(strNewFileName);
            //另存文件
            FileCopyUtils.copy(file.getBytes(), uploadFile);
        } catch (IOException e) {
            writeErrorLog("","",e);
            strNewFileName="";
        }finally {
            return strNewFileName;
        }
    }
    public static String uploadFileAnalyze(HttpServletRequest request,String filePath,String fileName){
        try {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("input_file");
            // 获得文件名：
            String realFileName = file.getOriginalFilename();
            if(realFileName==null||"".equals(realFileName))return "";
            String prefix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            if(prefix==null||"".equals(prefix)||!".csv".equals(prefix))return "";
            File dirPath = new File(filePath);
            if (!dirPath.exists()) {
                dirPath.mkdirs();
            }
            File uploadFile = new File(fileName);
            //另存文件
            FileCopyUtils.copy(file.getBytes(), uploadFile);
        } catch (IOException e) {
            writeErrorLog("","",e);
            fileName="";
        }finally {
            return fileName;
        }
    }
    private static void writeErrorLog(String method,String content,Exception e){
        BaseSystemOutLog.writeLog(BaseSystemOutLog.LogType_Error, "UpDownloadFile", method, content, e);
    }
}
