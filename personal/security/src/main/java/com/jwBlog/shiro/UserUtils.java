package com.jwBlog.shiro;

import com.jwBlog.jwt.config.JwtUser;
import com.jwBlog.jwt.utils.SecurityContextHolder;
import com.jwBlog.security.entity.SecurityUserEntity;
import com.jwBlog.utils.dp.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.HashMap;
import java.util.List;

public class UserUtils {
    /**
     * 获取当前登陆用户
     * @return
     */
    public static JwtUser getCurrentUser(){
        JwtUser userDetails;
        try {
            userDetails= (JwtUser) SecurityContextHolder.getUserDetails();
        }catch (Exception e){
            userDetails=new JwtUser(0,"","","","",null,false,"2019-02-21", 0,"",DateUtils.getNow());
        }
        return userDetails;
    }

    /**
     * 获取当前登陆用户 待完善缓存
     * @return
     */
    public static String getCurrentUserCode(){
        return getCurrentUser().getUsername();
    }
    public static String getCurrentUserRealName(){
        return getCurrentUser().getRealName();
    }
    public static int getCurrentUserId(){
        return getCurrentUser().getId();
    }
}
