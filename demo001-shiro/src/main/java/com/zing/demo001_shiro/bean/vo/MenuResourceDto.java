package com.zing.demo001_shiro.bean.vo;

import com.zing.demo001_shiro.bean.MenuPermission;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zing create at 2020/2/18 10:57 下午
 * @version 0.0.1
 */
@Getter
@Setter
@Builder
@Slf4j
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MenuResourceDto {
    private static final long serialVersionUID = 1283649823746918L;
    /**
     * 资源主键
     */
    private int resourceId;
    /**
     * 父资源id
     */
    private int parentId;
    /**
     * 资源名称
     */
    private String name;
    /**
     * 资源key
     */
    private String resourceKey;
    /**
     * 资源url
     */
    private String url;
    /**
     * 状态  0 待激活 1 正常 2停用
     */
    private int status;
    /**
     * 创建人
     */
    private String creator;
    /**
     * 创建日期
     */
    private Date createTime;
    /**
     * 更新人
     */
    private String updater;
    /**
     * 最后登录时间
     */
    private Date updateTime;
    /**
     * 资源备注
     */
    private String remark;
    /**
     * 展示图标
     */
    private String icon;
    /**
     * 子菜单
     */
    private List<MenuResourceDto> children = new ArrayList<>();

    public static MenuResourceDto.MenuResourceDtoBuilder buildFromMenuResourceDto(MenuPermission mp) {
        return MenuResourceDto.builder()
                .resourceId(mp.getAutoId())
                .parentId(mp.getParentId())
                .name(mp.getMenuName())
                .resourceKey(mp.getMenuKey())
                .url("")
                .status(mp.getStatus())
                .creator(mp.getCreator())
                .createTime(mp.getCreateTime())
                .updater(mp.getUpdater())
                .updateTime(mp.getUpdateTime())
                .icon(mp.getIcon());
    }

    public static MenuResourceDto makeMySelf(Map<Integer, List<MenuPermission>> allMyMenu) {
        MenuResourceDto.MenuResourceDtoBuilder root = MenuResourceDto.builder().name("Root").resourceKey("ROOT").resourceId(1);
        if (MapUtils.isEmpty(allMyMenu) || !allMyMenu.containsKey(0)) {
            log.warn("可用菜单为空,或根目录不存在");
            return root.build();
        } else {
            List<MenuResourceDto> child = buildMyChild(allMyMenu, 0);
            Optional<MenuResourceDto> dto = child.stream().findFirst();
            return dto.orElseGet(root::build);
        }
    }

    private static List<MenuResourceDto> buildMyChild(Map<Integer, List<MenuPermission>> allMyMenu, Integer pid) {
        List<MenuPermission> all = allMyMenu.get(pid);
        if (CollectionUtils.isEmpty(all)) {
            return Collections.EMPTY_LIST;
        } else {
            return all.stream()
                    .map(mp -> buildFromMenuResourceDto(mp).children(buildMyChild(allMyMenu, mp.getAutoId())).build())
                    .collect(Collectors.toList());
        }

    }
}
