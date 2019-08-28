package com.jwBlog.security.service;

import com.alibaba.fastjson.JSONObject;
import com.jwBlog.base.service.BaseService;
import com.jwBlog.security.entity.SecurityModuleEntity;
import com.jwBlog.security.entity.vo.MenuVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SecurityModuleService extends BaseService<SecurityModuleEntity> {
    /**
     *根据登陆用户Id,查询出所有授权菜单 按钮 登陆授权
     * @param userId
     * @return
     */
    List<SecurityModuleEntity> queryByUserId(Integer userId);

    /**
     * 根据父菜单Id查询菜单
     * @param parentId
     * @return
     */
    List<SecurityModuleEntity> queryListParentId(Integer parentId);

    /**
     * 获取用户菜单列表 主页查询用户菜单用
     * @param userId
     * @return
     */
    List<SecurityModuleEntity> queryMenuListUser(Integer userId);

    /**
     * 检查名称是否重复
     * @param id
     * @param parentId
     * @param name
     * @return
     */
    boolean checkName(@Param(value = "id") Integer id, @Param(value = "parentId") Integer parentId, @Param(value = "name") String name);
    /**
     * 检查编码是否重复
     * @param id
     * @param parentId
     * @param code
     * @return
     */
    boolean checkCode(@Param(value = "id") Integer id, @Param(value = "parentId") Integer parentId, @Param(value = "code") String code);

    /**
     * 创建菜单
     * @param menuDTOS
     * @return
     */
    List<MenuVo> buildMenus(List<SecurityModuleEntity> menuDTOS);
    /**
     * 创建菜单
     * @param menuDTOS
     * @return
     */
    Object buildTree(List<SecurityModuleEntity> menuDTOS);

    Object buildTableTree(List<SecurityModuleEntity> moduleList);
}
