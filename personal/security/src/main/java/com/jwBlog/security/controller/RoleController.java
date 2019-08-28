package com.jwBlog.security.controller;

import com.jwBlog.frame.aop.Log;
import com.jwBlog.frame.exception.BadRequestException;
import com.jwBlog.security.entity.SecurityRoleEntity;
import com.jwBlog.security.service.SecurityRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 */
@RestController
@RequestMapping("api")
public class RoleController {

    @Autowired
    private SecurityRoleService securityRoleService;
    private static final String ENTITY_NAME = "role";

    @GetMapping(value = "/roles/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ROLES_ALL','ROLES_SELECT')")
    public ResponseEntity getRoles(@PathVariable int id){
        return new ResponseEntity(securityRoleService.queryObject(id), HttpStatus.OK);
    }

    /**
     * 返回全部的角色，新增用户时下拉选择
     * @return
     */
    @GetMapping(value = "/roles/tree")
    @PreAuthorize("hasAnyRole('ADMIN','MENU_ALL','MENU_SELECT','ROLES_ALL','USER_ALL','USER_SELECT')")
    public ResponseEntity getRoleTree(){
        return new ResponseEntity(securityRoleService.getRoleTree(), HttpStatus.OK);
    }

    @Log("查询角色")
    @GetMapping(value = "/roles")
    @PreAuthorize("hasAnyRole('ADMIN','ROLES_ALL','ROLES_SELECT')")
    public ResponseEntity getRoles(@RequestParam(required = false) String name, Pageable pageable){
        HashMap<String,Object> map=new HashMap<String,Object>();
        map.put("name",name==null||"null".equals(name)?"":name);
        return new ResponseEntity(securityRoleService.queryListByPage(map,pageable), HttpStatus.OK);
    }

    @Log("新增角色")
    @PostMapping(value = "/roles")
    @PreAuthorize("hasAnyRole('ADMIN','ROLES_ALL','ROLES_CREATE')")
    public ResponseEntity create(@Validated @RequestBody SecurityRoleEntity resources){
        if (resources.getId() != null) {
            throw new BadRequestException("新增角色不能包含ID");
        }
        if (!securityRoleService.checkName(0,resources.getName())) {
            throw new BadRequestException("角色名重复");
        }
        return new ResponseEntity(securityRoleService.save(resources), HttpStatus.CREATED);
    }

    @Log("修改角色")
    @PutMapping(value = "/roles")
    @PreAuthorize("hasAnyRole('ADMIN','ROLES_ALL','ROLES_EDIT')")
    public ResponseEntity update(@Validated @RequestBody SecurityRoleEntity resources){
        if (resources.getId() == null) {
            throw new BadRequestException("角色ID不能为空");
        }
        if (!securityRoleService.checkName(resources.getId(),resources.getName())) {
            throw new BadRequestException("角色名重复");
        }
        securityRoleService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除角色")
    @DeleteMapping(value = "/roles/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ROLES_ALL','ROLES_DELETE')")
    public ResponseEntity delete(@PathVariable int id){
        securityRoleService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
