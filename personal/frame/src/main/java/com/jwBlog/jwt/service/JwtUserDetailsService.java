package com.jwBlog.jwt.service;

import com.jwBlog.frame.exception.BadRequestException;
import com.jwBlog.frame.exception.EntityNotFoundException;
import com.jwBlog.jwt.config.JwtUser;
import com.jwBlog.security.entity.SecurityPermissionEntity;
import com.jwBlog.security.entity.SecurityUserEntity;
import com.jwBlog.security.service.SecurityPermissionService;
import com.jwBlog.security.service.SecurityUserService;
import com.jwBlog.security.utils.UserSystemUtils;
import com.jwBlog.utils.dp.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jie
 * @date 2018-11-22
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class JwtUserDetailsService implements UserDetailsService {
    @Value("${server.url}")
    private String host;

    @Autowired
    private SecurityUserService securityUserService;
    @Autowired
    private SecurityPermissionService securityPermissionService;

    @Override
    public UserDetails loadUserByUsername(String username){
        SecurityUserEntity user = null;
        user= UserSystemUtils.getUser(host,username);
        if (user == null) {
            throw new BadRequestException("用户"+username+"不存在");
        }
        if (user == null) {
            throw new EntityNotFoundException(SecurityUserEntity.class, "name", username);
        } else {
            List<SecurityPermissionEntity> permissionEntities=null;
            if(user.getTenantId()==0) {
                permissionEntities = securityPermissionService.queryMenuListUser(user.getId());
                if (permissionEntities == null) {
                    throw new EntityNotFoundException(SecurityPermissionEntity.class, "permission", username);
                }
            }else{
                permissionEntities=new ArrayList<SecurityPermissionEntity>();
            }
            return create(user,permissionEntities);
        }
    }

    public UserDetails create(SecurityUserEntity user,List<SecurityPermissionEntity> permissionEntities) {
        return new JwtUser(
                user.getId(),
                user.getRealName(),
                user.getCode(),
                user.getPassword(),
                user.getSalt(),
                mapToGrantedAuthorities(permissionEntities),
                user.getActive().equals(SecurityUserEntity.active_usable),
                user.getCreateTime(),
                user.getTenantId(),
                user.getTenantUrl(),
                DateUtils.convertToDate(user.getLastPassTime(),"yyyy-MM-dd HH:mm:ss")
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<SecurityPermissionEntity> permissions) {
        return permissions.stream()
                .map(permission -> new SimpleGrantedAuthority("ROLE_"+permission.getName()))
                .collect(Collectors.toList());
    }
}
