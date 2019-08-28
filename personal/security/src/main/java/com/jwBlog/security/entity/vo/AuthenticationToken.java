package com.jwBlog.security.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * 返回token
 */
@Getter
@AllArgsConstructor
public class AuthenticationToken implements Serializable {

    private final String token;
}
