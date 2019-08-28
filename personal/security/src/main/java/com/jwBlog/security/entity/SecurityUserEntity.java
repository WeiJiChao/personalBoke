package com.jwBlog.security.entity;

import com.jwBlog.frame.exception.MyException;
import com.jwBlog.utils.DataCheck.DataCheckUtils;
import com.jwBlog.utils.encrypt.MD5;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.security.Security;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
@Getter
@Setter
public class SecurityUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	public static String active_usable="1";
	public static String active_unable="0";
	private Integer id; /****/
	private Integer departmentId; /**所属机构**/
	private String depName; /**所属机构**/
	private String code; /**用户登录名**/
	private String name; /**用户名**/
	private String realName; /**真实姓名**/
	private String password; /****/
	private String salt; /****/
	private String lastPassTime; /****/
	private String identity; /****/
	private String job; /**职务**/
	private String active; /****/
	private Integer loginFailed; /****/
	private String createTime; /****/
	private Integer createUser; /****/
	private String departmentList; /**可访问的数据范围**/
	private String departmentListName; /**可访问的数据范围名称**/
	private String lastModifyTime; /****/
	private Integer lastModifyUser; /****/
	/**
	 * 新密码
	 */
	private String newPassword;

	private Integer tenantId;

	private String tenantUrl;
	/**
	 * 角色ID列表
	 */
	private List<SecurityRoleEntity> roles;

	public String checkValue(String colName,String colValue)throws MyException{
		if("id".equals(colName)){
			String strShowColumnName=("id");
			DataCheckUtils.checkInteger(colValue,strShowColumnName,10,"NO");
			return "";
		}
		if("departmentId".equals(colName)){
			String strShowColumnName=("departmentId");
			DataCheckUtils.checkInteger(colValue,strShowColumnName,10,"YES");
			return "";
		}
		if("code".equals(colName)){
			String strShowColumnName=("code");
			DataCheckUtils.checkString(colValue,strShowColumnName,80,"YES");
			return "";
		}
		if("name".equals(colName)){
			String strShowColumnName=("name");
			DataCheckUtils.checkString(colValue,strShowColumnName,40,"YES");
			return "";
		}
		if("realName".equals(colName)){
			String strShowColumnName=("realName");
			DataCheckUtils.checkString(colValue,strShowColumnName,40,"YES");
			return "";
		}
		if("password".equals(colName)){
			String strShowColumnName=("password");
			DataCheckUtils.checkString(colValue,strShowColumnName,100,"YES");
			return "";
		}
		if("identity".equals(colName)){
			String strShowColumnName=("identity");
			DataCheckUtils.checkString(colValue,strShowColumnName,10,"YES");
			return "";
		}
		if("job".equals(colName)){
			String strShowColumnName=("job");
			DataCheckUtils.checkString(colValue,strShowColumnName,100,"YES");
			return "";
		}
		if("active".equals(colName)){
			String strShowColumnName=("active");
			DataCheckUtils.checkString(colValue,strShowColumnName,1,"YES");
			return "";
		}
		if("loginFailed".equals(colName)){
			String strShowColumnName=("loginFailed");
			DataCheckUtils.checkInteger(colValue,strShowColumnName,10,"YES");
			return "";
		}
		if("createTime".equals(colName)){
			String strShowColumnName=("createTime");
			DataCheckUtils.checkString(colValue,strShowColumnName,20,"YES");
			return "";
		}
		if("createUser".equals(colName)){
			String strShowColumnName=("createUser");
			DataCheckUtils.checkInteger(colValue,strShowColumnName,10,"YES");
			return "";
		}
		if("departmentList".equals(colName)){
			String strShowColumnName=("departmentList");
			DataCheckUtils.checkString(colValue,strShowColumnName,65535,"YES");
			return "";
		}
		if("departmentListName".equals(colName)){
			String strShowColumnName=("departmentListName");
			DataCheckUtils.checkString(colValue,strShowColumnName,65535,"YES");
			return "";
		}
		if("lastModifyTime".equals(colName)){
			String strShowColumnName=("lastModifyTime");
			DataCheckUtils.checkString(colValue,strShowColumnName,20,"YES");
			return "";
		}
		if("lastModifyUser".equals(colName)){
			String strShowColumnName=("lastModifyUser");
			DataCheckUtils.checkInteger(colValue,strShowColumnName,10,"YES");
			return "";
		}
		throw new MyException( "没有找到字段"+colName);
	}
	public static MD5 getMD5 = new MD5();

	public static String passwordSecurity(String password,String salt){
		return getMD5.GetMD5Code(getMD5.GetMD5Code(password)+salt);
	}
//	/**
//	 * 加密密码，需要usercode
//	 * @param password
//	 * @return
//	 */
//	public String passwordsecurity(String password){
//		return getMD5.GetMD5Code(getMD5.GetMD5Code(password)+this.code);
//	}

	public static void main(String[] args) {
		System.out.println(SecurityUserEntity.passwordSecurity("123456","MnHMOR4XDK4fldpt5hja"));
	}
}
