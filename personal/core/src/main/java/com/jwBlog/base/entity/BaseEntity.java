package com.jwBlog.base.entity;

import com.jwBlog.frame.exception.MyException;
import com.jwBlog.utils.DataCheck.DataCheckUtils;

/**
 */
public class BaseEntity {
    //创建日期
    private String createTime;
    //创建人
    private String createUser;
    //启用时间
    private String usableTime;
    //启用人
    private String usableUser;
    //停用时间
    private String forbiddenTime;
    //停用人
    private String forbiddenUser;
    //最后修改时间
    private String lastModifyTime;
    //最后修改人
    private String lastModifyUser;
    /**
     * 设置：创建日期
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    /**
     * 获取：创建日期
     */
    public String getCreateTime() {
        return createTime;
    }
    /**
     * 设置：创建人
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }
    /**
     * 获取：创建人
     */
    public String getCreateUser() {
        return createUser;
    }
    /**
     * 设置：启用时间
     */
    public void setUsableTime(String usableTime) {
        this.usableTime = usableTime;
    }
    /**
     * 获取：启用时间
     */
    public String getUsableTime() {
        return usableTime;
    }
    /**
     * 设置：启用人
     */
    public void setUsableUser(String usableUser) {
        this.usableUser = usableUser;
    }
    /**
     * 获取：启用人
     */
    public String getUsableUser() {
        return usableUser;
    }
    /**
     * 设置：停用时间
     */
    public void setForbiddenTime(String forbiddenTime) {
        this.forbiddenTime = forbiddenTime;
    }
    /**
     * 获取：停用时间
     */
    public String getForbiddenTime() {
        return forbiddenTime;
    }
    /**
     * 设置：停用人
     */
    public void setForbiddenUser(String forbiddenUser) {
        this.forbiddenUser = forbiddenUser;
    }
    /**
     * 获取：停用人
     */
    public String getForbiddenUser() {
        return forbiddenUser;
    }
    /**
     * 设置：最后修改时间
     */
    public void setLastModifyTime(String lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }
    /**
     * 获取：最后修改时间
     */
    public String getLastModifyTime() {
        return lastModifyTime;
    }
    /**
     * 设置：最后修改人
     */
    public void setLastModifyUser(String lastModifyUser) {
        this.lastModifyUser = lastModifyUser;
    }
    /**
     * 获取：最后修改人
     */
    public String getLastModifyUser() {
        return lastModifyUser;
    }

    public String checkValue(String colName, String colValue) throws MyException {
        if ("createtime".equals(colName)) {
            String strShowColumnName = ("创建日期");
            DataCheckUtils.checkString(colValue, strShowColumnName, 20, "YES");
            return "";
        }else if ("createuser".equals(colName)) {
            String strShowColumnName = ("创建人");
            DataCheckUtils.checkString(colValue, strShowColumnName, 50, "YES");
            return "";
        }else if ("usabletime".equals(colName)) {
            String strShowColumnName = ("启用时间");
            DataCheckUtils.checkString(colValue, strShowColumnName, 20, "YES");
            return "";
        }else if ("usableuser".equals(colName)) {
            String strShowColumnName = ("启用人");
            DataCheckUtils.checkString(colValue, strShowColumnName, 50, "YES");
            return "";
        }else  if ("forbiddentime".equals(colName)) {
            String strShowColumnName = ("停用时间");
            DataCheckUtils.checkString(colValue, strShowColumnName, 20, "YES");
            return "";
        }else  if ("forbiddenuser".equals(colName)) {
            String strShowColumnName = ("停用人");
            DataCheckUtils.checkString(colValue, strShowColumnName, 50, "YES");
            return "";
        }else if ("lastmodifytime".equals(colName)) {
            String strShowColumnName = ("最后修改时间");
            DataCheckUtils.checkString(colValue, strShowColumnName, 20, "YES");
            return "";
        }else if ("lastmodifyuser".equals(colName)) {
            String strShowColumnName = ("最后修改人");
            DataCheckUtils.checkString(colValue, strShowColumnName, 50, "YES");
            return "";
        }
        return "";
    }
}
