package com.jwBlog.utils.service;

import com.alibaba.fastjson.JSONObject;
import com.jwBlog.base.entity.BaseEntity;
import com.jwBlog.frame.exception.MyDataCheckException;
import com.jwBlog.utils.encrypt.DesJs;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class getSaveSqlByColumn {
    public static String getSql(String sqlType, JSONObject obj, String paramName, String columnName, String defaultValue, StringBuffer sql, StringBuffer sqlValue, List<Object> paramList, boolean isEnc, BaseEntity checkObj) throws MyDataCheckException {
        String paramValue = obj.get(paramName) == null ? defaultValue: obj.get(paramName).toString();
        if(isEnc){
            if (paramValue != null && !"".equals(paramValue.trim())) {
                paramValue = new DesJs().decrypt(paramValue);
            }
        }
        paramValue = paramValue.trim();
        if(checkObj!=null)checkObj.checkValue(columnName,paramValue);
        if (paramValue != null && !"".equals(paramValue.trim())) {
            if("add".equals(sqlType)) {
                if (sql.length() > 0) {
                    sql.append(",").append(columnName);
                    sqlValue.append(",?");
                } else {
                    sql.append(columnName);
                    sqlValue.append("?");
                }
            }else{
                if (sql.length() > 0) {
                    sql.append(",").append(columnName).append("=?");
                } else {
                    sql.append(columnName).append("=?");
                }
            }
            paramList.add(paramValue);
        }
        return paramValue;
    }
    public static String getSql(String sqlType, JSONObject obj, String paramName, String columnName, String defaultValue, StringBuffer sql, StringBuffer sqlValue, boolean isEnc, BaseEntity checkObj) throws MyDataCheckException {
        String paramValue = obj.get(paramName) == null ? defaultValue: obj.get(paramName).toString();
        if(isEnc){
            if (paramValue != null && !"".equals(paramValue.trim())) {
                paramValue = new DesJs().decrypt(paramValue);
            }
        }
        paramValue = paramValue.trim();
        if(checkObj!=null)checkObj.checkValue(columnName,paramValue);
        if (paramValue != null && !"".equals(paramValue.trim())) {
            if("add".equals(sqlType)) {
                if (sql.length() > 0) {
                    sql.append(",").append(columnName);
                    sqlValue.append(",'").append(paramValue).append("'");
                } else {
                    sql.append(columnName);
                    sqlValue.append("'").append(paramValue).append("'");
                }
            }else{
                if (sql.length() > 0) {
                    sql.append(",").append(columnName).append("='").append(paramValue).append("'");
                } else {
                    sql.append(columnName).append("='").append(paramValue).append("'");
                }
            }
        }
        return paramValue;
    }
    public static String getSql(String sqlType, HttpServletRequest request, String paramName, String columnName, String defaultValue, StringBuffer sql, StringBuffer sqlValue, List<Object> paramList, boolean isEnc, BaseEntity checkObj) throws MyDataCheckException {
        String paramValue = request.getParameter(paramName)== null ? defaultValue: request.getParameter(paramName);
        if(isEnc){
            if (paramValue != null && !"".equals(paramValue.trim())) {
                paramValue = new DesJs().decrypt(paramValue);
            }
        }
        paramValue = paramValue.trim();
        if(checkObj!=null)checkObj.checkValue(columnName,paramValue);
        if (paramValue != null && !"".equals(paramValue.trim())) {
            if("add".equals(sqlType)) {
                if (sql.length() > 0) {
                    sql.append(",").append(columnName);
                    sqlValue.append(",?");
                } else {
                    sql.append(columnName);
                    sqlValue.append("?");
                }
            }else{
                if (sql.length() > 0) {
                    sql.append(",").append(columnName).append("=?");
                } else {
                    sql.append(columnName).append("=?");
                }
            }
            paramList.add(paramValue);
        }
        return paramValue;
    }
    public static String getSql(String sqlType, HttpServletRequest request, String paramName, String columnName, String defaultValue, StringBuffer sql, StringBuffer sqlValue, boolean isEnc, BaseEntity checkObj) throws MyDataCheckException {
        String paramValue = request.getParameter(paramName)== null ? defaultValue: request.getParameter(paramName);
        if(isEnc){
            if (paramValue != null && !"".equals(paramValue.trim())) {
                paramValue = new DesJs().decrypt(paramValue);
            }
        }
        paramValue = paramValue.trim();
        if(checkObj!=null)checkObj.checkValue(columnName,paramValue);
        if (paramValue != null && !"".equals(paramValue.trim())) {
            if("add".equals(sqlType)) {
                if (sql.length() > 0) {
                    sql.append(",").append(columnName);
                    sqlValue.append(",'").append(paramValue).append("'");
                } else {
                    sql.append(columnName);
                    sqlValue.append("'").append(paramValue).append("'");
                }
            }else{
                if (sql.length() > 0) {
                    sql.append(",").append(columnName).append("='").append(paramValue).append("'");
                } else {
                    sql.append(columnName).append("='").append(paramValue).append("'");
                }
            }
        }
        return paramValue;
    }
}
