package com.jwBlog.security.entity;
import com.jwBlog.frame.exception.MyException;
import com.jwBlog.utils.DataCheck.DataCheckUtils;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
public class SecurityUserRoleEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer userId;
	//
	private Integer roleId;
	public String checkValue(String colName, String colValue) throws MyException {
		if ("userId".equals(colName)) {
			String strShowColumnName = ("UserId");
			DataCheckUtils.checkInteger(colValue, strShowColumnName, 10, "NO");
			return "";
		}
		if ("roleId".equals(colName)) {
			String strShowColumnName = ("RoleId");
			DataCheckUtils.checkInteger(colValue, strShowColumnName, 10, "NO");
			return "";
		}
		return "";
	}
}
