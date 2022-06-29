package com.nhXJH.web.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.nhXJH.common.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.nhXJH.common.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.nhXJH.common.core.domain.BaseEntity;

/**
* 国家信息对象 base_country
*
* @author xjh
* @date 2022-03-01
*/
@TableName("base_country")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BaseCountry extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty("id")
    private Long id;

    /** 国家中文名 */
    @Excel(name = "国家中文名")
    @ApiModelProperty("国家中文名")
    private String name;

    /** 英文名 */
    @Excel(name = "英文名")
    @ApiModelProperty("英文名")
    private String englishName;

    /** 地理位置 */
    @Excel(name = "地理位置")
    @ApiModelProperty("地理位置")
    private String location;

    /** 面积 */
    @Excel(name = "面积")
    @ApiModelProperty("面积")
    private String area;

    /** 人口 */
    @Excel(name = "人口")
    @ApiModelProperty("人口")
    private Integer peopleNumber;

    /** 是否热门国家 */
    @Excel(name = "是否热门国家")
    @ApiModelProperty("是否热门国家")
    private String isHot;

    /** 关键字逗号分隔 */
    @Excel(name = "关键字逗号分隔")
    @ApiModelProperty("关键字逗号分隔")
    private String keys;

    /** 国旗路径 */
    @Excel(name = "国旗路径")
    @ApiModelProperty("国旗路径")
    private String url;

//    /** 是否删除 */
//    @Excel(name = "是否删除",readConverterExp = "0=正常,1=被删除")
//    @ApiModelProperty("是否删除")
//    private String isDel;
//
//    /** 状态 */
//    @Excel(name = "状态",readConverterExp = "0=停用,1=正常")
//    @ApiModelProperty("状态")
//    private String status;

//    /** 创建人 */
//    @Excel(name = "创建人")
//    @ApiModelProperty("创建人")
//    private Long createPersonal;
//
//    /** 更新人 */
//    @Excel(name = "更新人")
//    @ApiModelProperty("更新人")
//    private Long updatePersonal;



}
