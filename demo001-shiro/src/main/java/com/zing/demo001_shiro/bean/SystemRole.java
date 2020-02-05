package com.zing.demo001_shiro.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 后台系统用户
 *
 * @author wrh
 * @since 2017.11.29 13:54
 */
@Data
public class SystemRole implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 角色主键
     */
    private int roleId;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色key
     */
    private String roleKey;

    /**
     * 状态  0 待激活 1 正常 2停用
     */
    private int status;

    /**
     * 创建日期
     */
    private Date createTime;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 更新人
     */
    private String updater;

    /**
     * 最后登录时间
     */
    private Date updateTime;

}