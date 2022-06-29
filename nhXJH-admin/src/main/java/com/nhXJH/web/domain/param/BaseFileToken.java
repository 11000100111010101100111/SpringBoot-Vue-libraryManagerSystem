package com.nhXJH.web.domain.param;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.io.Serializable;

/**
 * (BaseFileToken)实体类
 *
 * @author xjh
 * @since 2022-01-18 11:07:09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("base_file_token")
public class BaseFileToken implements Serializable {
    private static final long serialVersionUID = 100570012788478868L;
    /**
     * id
     */
    @TableId("id")
    @ApiModelProperty("id")
    private Long id;
    /**
     * 服务账号
     */
    @ApiModelProperty(name = "服务账号",required = true)
    private String account;
    /**
     * 服务秘钥
     */
    @ApiModelProperty(name = "服务秘钥",required = true)
    private String key;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String mark;
    /**
     * 有效期至
     */
    @ApiModelProperty("有效期至")
    private Date usefulLife;
    /**
     * 创建日期
     */
    @ApiModelProperty("创建日期")
    private Date createTime;
    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    private Long createPeraon;
    /**
     * 状态，默认，表示失效
     */
    @ApiModelProperty("状态，默认1，0表示失效")
    private Integer status;
}

