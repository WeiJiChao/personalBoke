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
public class SecurityPermissionEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	public static String active_usable="1";
	public static String active_unable="0";
	//
	private int id;
	//
	private int pid;
	private String parentName;
	//
	private String alias;
	//
	private String name;
	private String showName;
	private String sortIndex;
	//
	private String active;

	private List<SecurityPermissionEntity> children;

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
		if ("alias".equals(colName)) {
			String strShowColumnName = ("别名");
			DataCheckUtils.checkString(colValue, strShowColumnName, 40, "YES");
			return "";
		}
		if ("showName".equals(colName)) {
			String strShowColumnName = ("显示名称");
			DataCheckUtils.checkString(colValue, strShowColumnName, 20, "YES");
			return "";
		}
		if ("name".equals(colName)) {
			String strShowColumnName = ("名称");
			DataCheckUtils.checkString(colValue, strShowColumnName, 40, "YES");
			return "";
		}
		if ("active".equals(colName)) {
			String strShowColumnName = ("状态");
			DataCheckUtils.checkString(colValue, strShowColumnName, 1, "YES");
			return "";
		}
		return "";
	}
}
