package com.nhXJH.web.domain;

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

import java.util.Date;

/**
* 作信息者对象 library_auth
*
* @author xjh
* @date 2022-01-25
*/
@TableName("library_auth")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LibraryAuth extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty("id")
    private Long id;

    /** 作者名称 */
    @Excel(name = "作者名称")
    @ApiModelProperty("作者名称")
    private String name;

    /** 性别，0保密，1男，2女 */
    @Excel(name = "性别",readConverterExp = "0=保密,1=男,2=女")
    @ApiModelProperty("性别，0保密，1男，2女")
    private Long sex;

    /** 国籍 */
    @Excel(name = "国籍")
    @ApiModelProperty("国籍")
    private String country;

    /** 作者别名 */
    @Excel(name = "作者别名")
    @ApiModelProperty("作者别名")
    private String nickName;

    /** 英文名称 */
    @Excel(name = "英文名称")
    @ApiModelProperty("英文名称")
    private String englishName;

    /** 简介 */
    @Excel(name = "简介")
    @ApiModelProperty("简介")
    private String synopsis;

    /** 社会身份，多个身份逗号分隔 */
    @Excel(name = "社会身份，多个身份逗号分隔")
    @ApiModelProperty("社会身份，多个身份逗号分隔")
    private String identity;

    /** 状态，默认1存在，零表示不存在 */
    @Excel(name = "状态",readConverterExp="0=失效,1=有效")
    @ApiModelProperty("状态，默认1存在，零表示不存在")
    private String status="1";

    /** 创建人 */
    @Excel(name = "创建人")
    @ApiModelProperty("创建人")
    private Long createPersonal;

    /** 更新人 */
    @Excel(name = "更新人")
    @ApiModelProperty("更新人")
    private Long updatePersonal;

}
