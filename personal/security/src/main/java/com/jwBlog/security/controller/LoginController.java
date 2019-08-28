package com.jwBlog.security.controller;

import com.jwBlog.frame.aop.Log;
import com.jwBlog.jwt.config.JwtUser;
import com.jwBlog.jwt.utils.JwtTokenUtil;
import com.jwBlog.jwt.utils.SecurityContextHolder;
import com.jwBlog.security.entity.SecurityUserEntity;
import com.jwBlog.security.entity.vo.AuthenticationToken;
import com.jwBlog.security.entity.vo.AuthorizationUser;
import com.jwBlog.utils.encrypt.AesEncryptUtil;
import com.jwBlog.utils.encrypt.DesJs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: hxy
 * @description: 登录相关Controller
 * @date: 2017/10/24 10:33
 */
@Slf4j
@RestController
@RequestMapping("/api/login")
public class LoginController {

	@Value("${jwt.header}")
	private String tokenHeader;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	@Qualifier("jwtUserDetailsService")
	private UserDetailsService userDetailsService;

	/**
	 * 登录
	 */
	@Log("用户登录")
	@PostMapping(value = "${jwt.auth.path}")
	public ResponseEntity authLogin(@Validated @RequestBody AuthorizationUser authorizationUser) {
		final JwtUser userDetails = (JwtUser)userDetailsService.loadUserByUsername(authorizationUser.getUsername());
		String password="";
		try {
			password= AesEncryptUtil.desEncrypt(authorizationUser.getPassword());
		} catch (Exception e) {
			throw new AccountExpiredException("密码错误");
		}
		if(!userDetails.getPassword().equals(SecurityUserEntity.passwordSecurity(password,userDetails.getSalt()))){
			throw new AccountExpiredException("密码错误");
		}
		if(!userDetails.isEnabled()){
			throw new AccountExpiredException("账号已停用，请联系管理员");
		}
		// 生成令牌
		final String token = jwtTokenUtil.generateToken(userDetails);
		// 返回 token
		Map map = new HashMap();
		map.put("token", new AuthenticationToken(token));
		map.put("tenantId",userDetails.getTenantId());
		map.put("tenantUrl",userDetails.getTenantUrl());
		map.put("tenantUserEnc",new DesJs().encrypt(userDetails.getUsername()));
		return ResponseEntity.ok(map);
	}
	/**
	 * 查询当前登录用户的信息
	 */
	@GetMapping(value = "${jwt.auth.account}")
	public ResponseEntity getInfo() {
		UserDetails userDetails = SecurityContextHolder.getUserDetails();
		JwtUser jwtUser = (JwtUser)userDetailsService.loadUserByUsername(userDetails.getUsername());
		return ResponseEntity.ok(jwtUser);
	}
}
