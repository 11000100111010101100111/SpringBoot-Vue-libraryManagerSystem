package com.nhXJH.web.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.nhXJH.common.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import io.swagger.annotations.ApiModelProperty;
import com.nhXJH.common.core.domain.BaseEntity;

/**
* 附件访问秘钥信息对象 base_file_token
*
* @author xjh
* @date 2022-01-25
*/
@TableName("base_file_token")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FileToken extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty("id")
    private Long id;

    /** 服务账号 */
    @Excel(name = "服务账号")
    @ApiModelProperty("服务账号")
    private String account;

    /** 服务秘钥 */
    @Excel(name = "服务秘钥")
    @ApiModelProperty("服务秘钥")
    private String key;

    /** 备注 */
    @Excel(name = "备注")
    @ApiModelProperty("备注")
    private String mark;

    /** 有效期至 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "有效期至", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("有效期至")
    private Date usefulLife;

    /** 创建人 */
    @Excel(name = "创建人")
    @ApiModelProperty("创建人")
    private Long createPeraon;

    /** 状态，默认，表示失效 */
    @Excel(name = "状态",readConverterExp="0=失效,1=有效")
    @ApiModelProperty("状态，默认，表示失效")
    private Long status;



}
