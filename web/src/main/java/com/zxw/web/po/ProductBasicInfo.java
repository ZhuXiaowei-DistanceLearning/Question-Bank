package com.zxw.web.po;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author zxw
 * @since 2022-04-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ProductBasicInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String recordId;

    private String prodId;

    private String companyId;

    private String prodSecret;

    private String prodName;

    private String prodType;

    private String protoType;

    private String protoDatagram;

    private String prodModel;

    private String prodImages;

    private String prodMemo;

    private String prodState;

    private String prodConfig;

    private String verifyType;

    private String verifyData;

    private String releaseTime;

    private String closeTime;

    private String creatorId;

    private String createTime;

    private String updatorId;

    private String updateTime;

    private String isDel;

    private String prodCategory;

    private String validTime;


}
