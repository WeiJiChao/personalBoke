package com.jwBlog.security.entity.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PassVo {
    private String pass;
    private String newPass;
}
