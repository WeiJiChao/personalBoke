package com.jwBlog.security.entity;

import com.jwBlog.frame.exception.MyException;
import com.jwBlog.utils.DataCheck.DataCheckUtils;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class SecurityRoleEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	public static String active_usable="1";
	public static String active_unable="0";
	//
	private Integer id;
	//
	private Integer platId;
	//
	private String name;
	//
	private String showName;
	//
	private String active;
	//
	private String remark;

	private List<SecurityModuleEntity> menus;

	private List<SecurityPermissionEntity> permissions;

	public String checkValue(String colName, String colValue) throws MyException {
		if ("id".equals(colName)) {
			String strShowColumnName = ("Id");
			DataCheckUtils.checkInteger(colValue, strShowColumnName, 10, "NO");
			return "";
		}
		if ("platId".equals(colName)) {
			String strShowColumnName = ("系统");
			DataCheckUtils.checkInteger(colValue, strShowColumnName, 10, "YES");
			return "";
		}
		if ("name".equals(colName)) {
			String strShowColumnName = ("角色名");
			DataCheckUtils.checkString(colValue, strShowColumnName, 20, "YES");
			return "";
		}
		if ("showName".equals(colName)) {
			String strShowColumnName = ("角色显示名");
			DataCheckUtils.checkString(colValue, strShowColumnName, 20, "YES");
			return "";
		}
		if ("active".equals(colName)) {
			String strShowColumnName = ("状态");
			DataCheckUtils.checkString(colValue, strShowColumnName, 1, "YES");
			return "";
		}
		if ("remark".equals(colName)) {
			String strShowColumnName = ("备注");
			DataCheckUtils.checkString(colValue, strShowColumnName, 100, "YES");
			return "";
		}
		return "";
	}
}
