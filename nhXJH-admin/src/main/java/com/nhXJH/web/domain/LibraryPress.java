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
* 出版社信息对象 library_press
*
* @author xjh
* @date 2022-01-25
*/
@TableName("library_press")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LibraryPress extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty("ID")
    private Long id;

    /** 出版社名称 */
    @Excel(name = "出版社名称")
    @ApiModelProperty("出版社名称")
    private String name;

    /** 出版社地址 */
    @Excel(name = "出版社地址")
    @ApiModelProperty("出版社地址")
    private String addr;

    /** 出版社简介 */
    @Excel(name = "出版社简介")
    @ApiModelProperty("出版社简介")
    private String mark;

    /** 单位邮箱 */
    @Excel(name = "单位邮箱")
    @ApiModelProperty("单位邮箱")
    private String email;

    /** 联系电话 */
    @Excel(name = "联系电话")
    @ApiModelProperty("联系电话")
    private String phone;

    /** 状态，默认1存在，0不存在 */
    @Excel(name = "状态",readConverterExp="0=失效,1=有效")
    @ApiModelProperty("状态，默认1存在，0不存在")
    private String status;

    /** 创建人 */
    @Excel(name = "创建人")
    @ApiModelProperty("创建人")
    private Long createPersonal;

    /** 修改人 */
    @Excel(name = "修改人")
    @ApiModelProperty("修改人")
    private Long updatePersonal;

    /** 删除时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "删除时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("删除时间")
    private Date delTime;

    /** 删除人 */
    @Excel(name = "删除人")
    @ApiModelProperty("删除人")
    private Long delPersonal;
}
