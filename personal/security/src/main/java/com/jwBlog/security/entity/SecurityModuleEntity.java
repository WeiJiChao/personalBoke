package com.jwBlog.security.entity;
import com.jwBlog.frame.exception.MyException;
import com.jwBlog.utils.DataCheck.DataCheckUtils;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 *
 */
@Getter
@Setter
public class SecurityModuleEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public static String active_usable="1";
	public static String active_unable="0";

	public static String isOpenNew_yes="1";
	public static String isOpenNew_no="0";

	public static String isFrame_yes="1";
	public static String isFrame_no="0";
	//
	private int id;
	//
	private int pid;
	private String parentName;
	//
	private String code;
	//
	private String name;
	//
	private String showName;
	//
	private String component;
	//
	private Integer sortIndex;
	//
	private String icon;
	//权限标识
	private String path;
	//
	private String active;
	//
	private String isOpenNew;
	//外链
	private String isFrame;

	private List<SecurityModuleEntity> children;

	private List<SecurityRoleEntity> roles;

	public String checkValue(String colName, String colValue) throws MyException {
		if ("id".equals(colName)) {
			String strShowColumnName = ("Id");
			DataCheckUtils.checkInteger(colValue, strShowColumnName, 20, "NO");
			return "";
		}
		if ("pid".equals(colName)) {
			String strShowColumnName = ("父级Id");
			DataCheckUtils.checkInteger(colValue, strShowColumnName, 20, "YES");
			return "";
		}
		if ("code".equals(colName)) {
			String strShowColumnName = ("编码");
			DataCheckUtils.checkString(colValue, strShowColumnName, 20, "YES");
			return "";
		}
		if ("name".equals(colName)) {
			String strShowColumnName = ("名称");
			DataCheckUtils.checkString(colValue, strShowColumnName, 20, "YES");
			return "";
		}
		if ("showName".equals(colName)) {
			String strShowColumnName = ("显示名称");
			DataCheckUtils.checkString(colValue, strShowColumnName, 20, "YES");
			return "";
		}
		if ("component".equals(colName)) {
			String strShowColumnName = ("地址");
			DataCheckUtils.checkString(colValue, strShowColumnName, 200, "YES");
			return "";
		}
		if ("sortIndex".equals(colName)) {
			String strShowColumnName = ("排序");
			DataCheckUtils.checkInteger(colValue, strShowColumnName, 10, "YES");
			return "";
		}
		if ("icon".equals(colName)) {
			String strShowColumnName = ("图片");
			DataCheckUtils.checkString(colValue, strShowColumnName, 60, "YES");
			return "";
		}
		if ("path".equals(colName)) {
			String strShowColumnName = ("路径");
			DataCheckUtils.checkString(colValue, strShowColumnName, 150, "YES");
			return "";
		}
		if ("active".equals(colName)) {
			String strShowColumnName = ("状态");
			DataCheckUtils.checkString(colValue, strShowColumnName, 1, "YES");
			return "";
		}
		if ("isFrame".equals(colName)) {
			String strShowColumnName = ("是否IFrame");
			DataCheckUtils.checkString(colValue, strShowColumnName, 1, "YES");
			return "";
		}
		if ("isOpenNew".equals(colName)) {
			String strShowColumnName = ("是否新开界面");
			DataCheckUtils.checkString(colValue, strShowColumnName, 1, "YES");
			return "";
		}
		return "";
	}
}
