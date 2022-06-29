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
* 图书管理机构信息对象 library_mechanism
*
* @author xjh
* @date 2022-01-25
*/
@TableName("library_mechanism")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LibraryMechanism extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty("id")
    private Long id;

    /** 图书管理机构id */
    @Excel(name = "图书管理机构id")
    @ApiModelProperty("图书管理机构id")
    private Long deptId;

    /** 单位邮箱 */
    @Excel(name = "单位邮箱")
    @ApiModelProperty("单位邮箱")
    private String email;

    /** 单位联系电话 */
    @Excel(name = "单位联系电话")
    @ApiModelProperty("单位联系电话")
    private String phone;

    /** 单位地址 */
    @Excel(name = "单位地址")
    @ApiModelProperty("单位地址")
    private String addr;

    /** 状态 */
    @Excel(name = "说明")
    @ApiModelProperty("说明")
    private String mark;

    /** 状态 */
    @Excel(name = "状态",readConverterExp="0=失效,1=有效")
    @ApiModelProperty("状态")
    private String status;


}
