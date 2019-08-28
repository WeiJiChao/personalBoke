package com.jwBlog.security.controller;

import com.jwBlog.frame.aop.Log;
import com.jwBlog.frame.exception.BadRequestException;
import com.jwBlog.security.entity.SecurityPermissionEntity;
import com.jwBlog.security.service.SecurityPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
/**
 */
@RestController
@RequestMapping("api")
public class PermissionController {

    @Autowired
    private SecurityPermissionService securityPermissionService;
    private static final String ENTITY_NAME = "permission";

    @GetMapping(value = "/permissions/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','PERMISSION_ALL','PERMISSION_SELECT')")
    public ResponseEntity getPermissions(@PathVariable int id){
        return new ResponseEntity(securityPermissionService.queryObject(id), HttpStatus.OK);
    }

    /**
     * 返回全部的权限，新增角色时下拉选择
     * @return
     */
    @GetMapping(value = "/permissions/tree")
    @PreAuthorize("hasAnyRole('ADMIN','PERMISSION_ALL','PERMISSION_SELECT','ROLES_SELECT','ROLES_ALL')")
    public ResponseEntity getRoleTree(){
        List<SecurityPermissionEntity> menuList=securityPermissionService.queryListParentId(0);
        return new ResponseEntity(securityPermissionService.buildPermissionTree(menuList), HttpStatus.OK);
    }

    @Log("查询权限")
    @GetMapping(value = "/permissions")
    @PreAuthorize("hasAnyRole('ADMIN','PERMISSION_ALL','PERMISSION_SELECT')")
    public ResponseEntity getPermissions(@RequestParam(required = false) String name){
        HashMap<String,Object> map=new HashMap<String,Object>();
        map.put("name",name==null||"null".equals(name)?"":name);
        List<SecurityPermissionEntity> menuList=securityPermissionService.queryList(map);
        return new ResponseEntity(securityPermissionService.buildTableTree(menuList), HttpStatus.OK);
    }

    @Log("新增权限")
    @PostMapping(value = "/permissions")
    @PreAuthorize("hasAnyRole('ADMIN','PERMISSION_ALL','PERMISSION_CREATE')")
    public ResponseEntity create(@Validated @RequestBody SecurityPermissionEntity resources){
        if (resources.getId() != 0) {
            throw new BadRequestException("新增权限不能包含ID");
        }
        if (!securityPermissionService.checkName(0,resources.getPid(),resources.getName())) {
            throw new BadRequestException("权限名重复");
        }
        return new ResponseEntity(securityPermissionService.save(resources), HttpStatus.CREATED);
    }

    @Log("修改权限")
    @PutMapping(value = "/permissions")
    @PreAuthorize("hasAnyRole('ADMIN','PERMISSION_ALL','PERMISSION_EDIT')")
    public ResponseEntity update(@Validated @RequestBody SecurityPermissionEntity resources){
        if (resources.getId() == 0) {
            throw new BadRequestException("权限ID不能为空");
        }
        if (!securityPermissionService.checkName(resources.getId(),resources.getPid(),resources.getName())) {
            throw new BadRequestException("权限名重复");
        }
        securityPermissionService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除权限")
    @DeleteMapping(value = "/permissions/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','PERMISSION_ALL','PERMISSION_DELETE')")
    public ResponseEntity delete(@PathVariable int id){
        securityPermissionService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
