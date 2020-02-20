package com.zing.demo001_shiro.inner;

import com.zing.demo001_shiro.bean.*;
import com.zing.demo001_shiro.mapper.PermissionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zing create at 2020/2/18 9:31 上午
 * @version 0.0.1
 */

@Slf4j
@Component
public class PermissionLoaderCommander implements CommandLineRunner {
    @Autowired
    private PermissionMapper permissionMapper;

    public static Map<Integer, List<MenuPermission>> menuPermissionByGroupId;
    public static Map<Integer, List<UrlPermission>> urlPermissionByGroupId;
    public static Map<Integer, List<PermissionGroup>> rolePermissionGroup;

    @Override
    public void run(String... args) throws Exception {
        /**
         * 启动刷新缓存
         */
        refreshCatch();
    }

    public void refreshCatch() {
        // 查询所有有效菜单
        Map<Integer, MenuPermission> menuMap = getMenuMap();


        Map<Integer, UrlPermission> urlMap = getUrlMap();
        // 查询所有有效URL
        Map<Integer, PermissionGroup> permissionGroupMap = getPermissionGroupMap();

        //查询所有权限组对应的权限映射关系
        List<PermissionRelation> permissionRelations = permissionMapper.allPermissionRelation();
        List<RolesPermissionGroup> allRolesPermissionGroup = permissionMapper.allRolesPermissionGroup();

        // 缓存数据
        menuPermissionByGroupId = connectGroupPermissionMenu(permissionRelations, menuMap);
        urlPermissionByGroupId = connectGroupPermissionUrl(permissionRelations, urlMap);
        rolePermissionGroup = connectRoleAndGroup(allRolesPermissionGroup, permissionGroupMap);
    }

    /**
     * 权限组与
     *
     * @param permissionRelations
     * @param urlMap
     * @return
     */
    private Map<Integer, List<UrlPermission>> connectGroupPermissionUrl(List<PermissionRelation> permissionRelations, Map<Integer, UrlPermission> urlMap) {
        Map<Integer, List<PermissionRelation>> menuGroupPermissionMap = permissionRelations.stream()
                .filter(p -> "URL".equals(p.getPermissionType()))
                .collect(Collectors.groupingBy(PermissionRelation::getGroupId));

        Map<Integer, List<UrlPermission>> menuPermission = new HashMap<>(menuGroupPermissionMap.size());

        menuGroupPermissionMap.forEach((groupId, menuRelationList) -> {
            List<UrlPermission> menuPermissionList = menuRelationList.stream()
                    .map(r -> urlMap.get(r.getPermissionId()))
                    .collect(Collectors.toList());
            menuPermission.put(groupId, menuPermissionList);
        });
        return menuPermission;
    }


    /**
     * 权限组与菜单关系
     */
    private Map<Integer, List<MenuPermission>> connectGroupPermissionMenu(List<PermissionRelation> permissionRelations, Map<Integer, MenuPermission> menuMap) {
        Map<Integer, List<PermissionRelation>> menuGroupPermissionMap = permissionRelations.stream()
                .filter(p -> "MENU".equals(p.getPermissionType()))
                .collect(Collectors.groupingBy(PermissionRelation::getGroupId));

        Map<Integer, List<MenuPermission>> menuPermission = new HashMap<>(menuGroupPermissionMap.size());

        menuGroupPermissionMap.forEach((groupId, menuRelationList) -> {
            List<MenuPermission> menuPermissionList = menuRelationList.stream()
                    .map(r -> menuMap.get(r.getPermissionId()))
                    .collect(Collectors.toList());
            menuPermission.put(groupId, menuPermissionList);
        });
        return menuPermission;
    }


    private Map<Integer, List<PermissionGroup>> connectRoleAndGroup(List<RolesPermissionGroup> allRolesPermissionGroup, Map<Integer, PermissionGroup> permissionGroupMap) {
        Map<Integer, List<RolesPermissionGroup>> rolePermissionMap = allRolesPermissionGroup.stream()
                .collect(Collectors.groupingBy(RolesPermissionGroup::getRoleId));
        Map<Integer, List<PermissionGroup>> rolePermissionGroupMap = new HashMap<>(rolePermissionMap.size());
        rolePermissionMap.forEach((roleId, rolesPermissionGroupList) -> {
            List<PermissionGroup> permissionGroupList = rolesPermissionGroupList.stream()
                    .map(pg -> permissionGroupMap.get(pg.getAutoId()))
                    .collect(Collectors.toList());
            rolePermissionGroupMap.put(roleId, permissionGroupList);
        });
        return rolePermissionGroupMap;
    }

    private Map<Integer, UrlPermission> getUrlMap() {
        List<UrlPermission> urlPermissions = permissionMapper.allUrlPermission();
        Map<Integer, UrlPermission> urlMap = new HashMap<>();
        urlPermissions.forEach(u -> urlMap.put(u.getAutoId(), u));
        log.debug("获取有效URL：{}", urlMap);
        return urlMap;
    }

    private Map<Integer, MenuPermission> getMenuMap() {
        List<MenuPermission> allMenu = permissionMapper.allMenuPermission();
        Map<Integer, MenuPermission> menuMap = new HashMap<>();
        allMenu.forEach(m -> menuMap.put(m.getAutoId(), m));
        log.debug("获取有效菜单：{}", allMenu);
        return menuMap;
    }

    private Map<Integer, PermissionGroup> getPermissionGroupMap() {
        // 查询所有权限组
        List<PermissionGroup> allPermissionGroup = permissionMapper.allPermissionGroup();
        Map<Integer, PermissionGroup> permissionGroupMap = new HashMap<>();
        allPermissionGroup.forEach(m -> permissionGroupMap.put(m.getAutoId(), m));
        log.debug("获取有效权限组：{}", allPermissionGroup);
        return permissionGroupMap;
    }
}
