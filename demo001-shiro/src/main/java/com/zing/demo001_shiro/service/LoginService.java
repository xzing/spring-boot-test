package com.zing.demo001_shiro.service;

import com.alibaba.fastjson.JSON;
import com.zing.demo001_shiro.bean.MenuPermission;
import com.zing.demo001_shiro.bean.PermissionGroup;
import com.zing.demo001_shiro.bean.SystemUser;
import com.zing.demo001_shiro.bean.vo.MenuResourceDto;
import com.zing.demo001_shiro.inner.JsonWebTokenUtil;
import com.zing.demo001_shiro.inner.PermissionLoaderCommander;
import com.zing.demo001_shiro.mapper.PermissionMapper;
import com.zing.demo001_shiro.mapper.SystemUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * create at     2020/2/11 3:33 下午
 * 为了方便就不写接口了
 *
 * @author zing
 * @version 0.0.1
 */
@Slf4j
@Service
public class LoginService {

    /**
     * 登录并获取jwt token
     *
     * @param user
     * @return
     */
    @Autowired
    private SystemUserMapper systemUserMapper;
    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private JsonWebTokenUtil jwtUtil;

    public String login(SystemUser user) {
        log.info("login service working!");
        Map<String, Object> testData = new HashMap<>();
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getAccount(), user.getPassword());
        try {
            subject.login(token);
            SystemUser data = systemUserMapper.selectByAccount(user.getAccount());
            String auth = jwtUtil.createToken(data, testData);
            testData.put("token", auth);
            return JSON.toJSONString(testData);
        } catch (UnknownAccountException a) {
            log.warn("用户不存在", a);
        } catch (IncorrectCredentialsException e) {
            log.warn("授权异常", e);
        }
        return "{\"msg\":\"登录失败\"}";
    }

    public String getMyMenu(String token) {
        SystemUser user = jwtUtil.getUserByToken(token);
        return JSON.toJSONString(getMenuResourceByRoleId(user.getRoleId()));
    }


    public MenuResourceDto getMenuResourceByRoleId(Integer roleId) {
        Map<Integer, List<PermissionGroup>> permissionGroup = PermissionLoaderCommander.rolePermissionGroup;

        if (permissionGroup.isEmpty() || !permissionGroup.containsKey(roleId)) {
            log.warn("角色未分配权限组");
            return new MenuResourceDto();
        }

        List<PermissionGroup> thisRolePermissionGroups = permissionGroup.get(roleId);

        Map<Integer, List<MenuPermission>> menuPermission = PermissionLoaderCommander.menuPermissionByGroupId;

        return makeMenuPermissionDto(thisRolePermissionGroups, menuPermission);
    }

    private MenuResourceDto makeMenuPermissionDto(List<PermissionGroup> thisRolePermissionGroups, Map<Integer, List<MenuPermission>> menuPermission) {
        Map<Integer, List<MenuPermission>> allMyMenu = thisRolePermissionGroups.stream()
                .map(p -> menuPermission.get(p.getAutoId()))
                .flatMap(List::stream)
                .distinct()
                .collect(Collectors.groupingBy(MenuPermission::getParentId));
        MenuResourceDto dto = MenuResourceDto.makeMySelf(allMyMenu);
        return dto;
    }


    private Map<Integer, List<PermissionGroup>> getPermissionGroup(Integer roleId) {
        List<PermissionGroup> permissionGroups = permissionMapper.getRolesPermissionGroupByRoleId(roleId);
        Map<Integer, List<PermissionGroup>> permissionGroupMap = new HashMap<>();
        permissionGroupMap.put(roleId, permissionGroups);
        return permissionGroupMap;
    }
}
