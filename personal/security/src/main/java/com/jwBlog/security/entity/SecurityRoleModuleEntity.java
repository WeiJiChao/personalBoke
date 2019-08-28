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
public class SecurityRoleModuleEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer roleId;
	//
	private Integer moduleId;
	public String checkValue(String colName, String colValue) throws MyException {
		if ("roleId".equals(colName)) {
			String strShowColumnName = ("RoleId");
			DataCheckUtils.checkInteger(colValue, strShowColumnName, 10, "NO");
			return "";
		}
		if ("moduleId".equals(colName)) {
			String strShowColumnName = ("ModuleId");
			DataCheckUtils.checkInteger(colValue, strShowColumnName, 10, "NO");
			return "";
		}
		return "";
	}
}
