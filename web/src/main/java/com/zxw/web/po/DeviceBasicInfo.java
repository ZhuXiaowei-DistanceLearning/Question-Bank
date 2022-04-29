package com.zxw.web.po;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 设备信息表(core.device_basic_info)
 * </p>
 *
 * @author zxw
 * @since 2022-04-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DeviceBasicInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键自增
     */
    private Integer recordId;

    /**
     * 设备ID(系统自动生成，全局唯一，D开头，字母数字组合)
     */
    private String deviceId;

    /**
     * 归属产品ID
     */
    private String prodId;

    /**
     * 设备密钥
     */
    private String deviceSecret;

    /**
     * 设备标识码
     */
    private String deviceCode;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 设备别名
     */
    private String deviceAlias;

    /**
     * 归属公司
     */
    private String companyId;

    /**
     * 归属网关
     */
    private String gwDeviceId;

    /**
     * 身份认证方式(继承自产品)
     */
    private String verifyType;

    /**
     * 身份认证数据(继承自产品，或以设备为单位进行分配。)
     */
    private String verifyData;

    /**
     * 设备状态(1:已注册，2:已激活，3:已注销)
     */
    private Integer deviceState;

    /**
     * 注册来源(0=人工录入，1=设备自主注册)
     */
    private Integer regBy;

    /**
     * 注册时间
     */
    private LocalDateTime regTime;

    /**
     * 最近激活时间
     */
    private LocalDateTime lastActivateTime;

    /**
     * 最近注销时间
     */
    private LocalDateTime lastDeactivateTime;

    /**
     * 创建人
     */
    private Integer creatorId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新人
     */
    private Integer updatorId;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 是否删除(0:否;1:是;)
     */
    private Integer isDel;

    /**
     * 备注描述
     */
    private String remark;

    /**
     * 1-启用 0-禁用
     */
    private Integer openStatus;


}
