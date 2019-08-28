package com.jwBlog.security.entity;
import com.jwBlog.frame.exception.MyException;
import com.jwBlog.utils.DataCheck.DataCheckUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
@AllArgsConstructor
public class SecurityRolePermissionEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	//
	private Integer roleId;
	//
	private Integer permissionId;

	public String checkValue(String colName, String colValue) throws MyException {
		if ("roleId".equals(colName)) {
			String strShowColumnName = ("RoleId");
			DataCheckUtils.checkInteger(colValue, strShowColumnName, 10, "NO");
			return "";
		}
		if ("permissionId".equals(colName)) {
			String strShowColumnName = ("permissionId");
			DataCheckUtils.checkInteger(colValue, strShowColumnName, 10, "NO");
			return "";
		}
		return "";
	}
}
