package com.jwBlog.security.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jwBlog.base.entity.MyResponseEntity;
import com.jwBlog.security.entity.SecurityUserEntity;
import com.jwBlog.utils.HttpClientUtil;
import com.jwBlog.utils.HttpRestUtil;
import com.jwBlog.utils.dp.DateUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class UserSystemUtils {
    private static  String userSystemUrl = "/api/cas/users";
    public static SecurityUserEntity getUser(String httpPath, String code){
        HashMap<String,String> param=new HashMap<String,String>();
        String result = HttpClientUtil.doGet(httpPath + userSystemUrl+"/getUser/"+code,param);
        JSONObject jo = JSONObject.parseObject(result);
        System.out.println(jo);
        if("400".equals(jo.getString("status"))){
            JSONArray array=jo.getJSONArray("tenantId");
            if(array==null|| array.size()<1)return null;
            if(array.get(0)==null)return null;
            SecurityUserEntity entity=new SecurityUserEntity();
            entity.setActive(jo.getString("active"));
            entity.setCode(code);
            entity.setName(jo.getString("name"));
            entity.setRealName(jo.getString("realName"));
            entity.setSalt(jo.getString("salt"));
            entity.setPassword(jo.getString("password"));
            entity.setLastPassTime(DateUtils.toString("yyyy-MM-dd HH:mm:ss"));
            entity.setTenantId(Integer.parseInt(array.get(0).toString()));
            return entity;
        }else return null;
    }
    public static int checkUserPass(String httpPath,String code,String pass){
        HashMap<String,String> param=new HashMap<String,String>();
        String result = HttpClientUtil.doGet(httpPath + userSystemUrl+"/validPass/"+pass+"/"+code,param);
        System.out.println(httpPath + userSystemUrl+"/validPass/"+pass+"/"+code);
        JSONObject jo = JSONObject.parseObject(result);
        System.out.println(jo);
        if("400".equals(jo.getString("status"))){
            JSONArray array=jo.getJSONArray("tenantId");
            if(array==null|| array.size()<1)return -1;
            if(array.get(0)==null)return -1;
            return Integer.parseInt(array.get(0).toString());
        }else return -1;
    }
    public static boolean updatePass(String httpPath,String code,String pass){
        HashMap<String,String> param=new HashMap<String,String>();
        String result = HttpClientUtil.doGet(httpPath + userSystemUrl+"/updatePass/"+pass+"/"+code,param);
        System.out.println(httpPath + userSystemUrl+"/updatePass/"+pass+"/"+code);
        JSONObject jo = JSONObject.parseObject(result);
        System.out.println(jo);
        return true;
    }
    public static boolean resetPass(String httpPath,String codes){
        HashMap<String,String> param=new HashMap<String,String>();
        String result = HttpClientUtil.doGet(httpPath + userSystemUrl+"/resetPass/"+codes,param);
        System.out.println(httpPath + userSystemUrl+"/resetPass/"+codes);
        JSONObject jo = JSONObject.parseObject(result);
        System.out.println(jo);
        return true;
    }
    public static boolean resetPassTenant(String httpPath,Integer tenantId){
        HashMap<String,String> param=new HashMap<String,String>();
        String result = HttpClientUtil.doGet(httpPath + userSystemUrl+"/resetPassTenant/"+tenantId,param);
        System.out.println(httpPath + userSystemUrl+"/resetPassTenant/"+tenantId);
        JSONObject jo = JSONObject.parseObject(result);
        System.out.println(jo);
        return true;
    }
    public static boolean deleteUser(String httpPath,String code){
        HashMap<String,String> param=new HashMap<String,String>();
        String result = HttpClientUtil.doGet(httpPath + userSystemUrl+"/deleteUser/"+code,param);
        System.out.println(httpPath + userSystemUrl+"/deleteUser/"+code);
        JSONObject jo = JSONObject.parseObject(result);
        System.out.println(jo);
        return true;
    }
    public static boolean updateUser(String httpPath,HashMap<String,String> param){
        String result=HttpRestUtil.doPutEntity(httpPath + userSystemUrl,param);
        JSONObject jo = JSONObject.parseObject(result);
        System.out.println("----------------------------"+jo);
        if("400".equals(jo.getString("status"))){
            return true;
        }else return false;
    }

    /**
     * @param httpPath
     * @param param
     * @return
     */
    public static boolean saveUser(String httpPath,HashMap<String,String> param){
        String result = HttpRestUtil.doPostEntity(httpPath + userSystemUrl, param);
        JSONObject jo = JSONObject.parseObject(result);
        System.out.println(jo);
        if("400".equals(jo.getString("status"))){
            return true;
        }else return false;
    }

    public static boolean checkCode(String httpPath,String code){
        HashMap<String,String> param=new HashMap<String,String>();
        String result = HttpClientUtil.doGet(httpPath + userSystemUrl+"/checkCode/"+code,param);
        System.out.println(httpPath + userSystemUrl+"/checkCode/"+code);
        JSONObject jo = JSONObject.parseObject(result);
        System.out.println(jo);
        if("400".equals(jo.getString("status"))){
            return true;
        }else return false;
    }
}
