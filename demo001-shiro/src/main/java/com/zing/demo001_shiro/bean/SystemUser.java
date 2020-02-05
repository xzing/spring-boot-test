package com.zing.demo001_shiro.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 后台系统用户
 *
 * @author lance
 * @since 2017.11.20 13:54
 */
@Data
public class SystemUser implements Serializable {
    private static final long serialVersionUID = 832967926L;

    /**
     * 主键
     */
    private int userId;

    /**
     * 企业ID
     */
    private int companyId;

    /**
     * 企业名称
     */
    private String companyName;

    /**
     * 登录账号
     */
    private String account;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 加解密ccksid
     */
    private String encryptCcksId;

    /**
     * 身份证
     */
    private String idCard;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 地址
     */
    private String address;

    /**
     * 用户keyID
     */
    private String keyId;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * 角色key
     */
    private String roleKey;
    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色父key
     */
    private String roleParentKey;

    /**
     * 状态  0 待激活 1 正常 2停用
     */
    private Integer status;

    /**
     * 登录次数
     */
    private int loginNum;

    /**
     * 更新人
     */
    private String updater;

    /**
     * 更新日期
     */
    private Date updateTime;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 创建日期
     */
    private Date createTime;

    /**
     * 最后登录时间
     */
    private Date lastLoginTime;

    /**
     * 最后登录IP地址
     */
    private String lastLoginIp;


}