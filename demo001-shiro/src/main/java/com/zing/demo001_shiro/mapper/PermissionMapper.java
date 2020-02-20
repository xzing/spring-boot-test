package com.zing.demo001_shiro.mapper;

import com.zing.demo001_shiro.bean.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zing create at 2020/2/18 9:47 上午
 * @version 0.0.1
 */
public interface PermissionMapper {
    /**
     * 所有可用菜单
     *
     * @return
     */
    List<MenuPermission> allMenuPermission();

    /**
     * 所有可用Url
     *
     * @return
     */
    List<UrlPermission> allUrlPermission();

    /**
     * 所有可用权限组
     *
     * @return
     */
    List<PermissionGroup> allPermissionGroup();

    /**
     * 权限组与权限数据
     *
     * @return
     */
    List<PermissionRelation> allPermissionRelation();

    /**
     * 角色与权限组关系
     *
     * @return
     */
    List<RolesPermissionGroup> allRolesPermissionGroup();


    /**
     * 查询Role Id下的权限组
     *
     * @param roleId
     * @return
     */
    List<PermissionGroup> getRolesPermissionGroupByRoleId(@Param("roleId") Integer roleId);

    /**
     * 批量查询权限组
     *
     * @param groupIdList
     * @return
     */
    List<PermissionRelation> findPermissionByPermissionGroupList(List<Integer> groupIdList);

    /**
     * 批量查询Url权限
     *
     * @param urlIdList
     * @return
     */
    List<UrlPermission> findUrlPermissionWitchIdIn(List<Integer> urlIdList);

    /**
     * @param menuIdList
     */
    List<MenuPermission> findMenuPermissionWitchIdIn(List<Integer> menuIdList);
}
