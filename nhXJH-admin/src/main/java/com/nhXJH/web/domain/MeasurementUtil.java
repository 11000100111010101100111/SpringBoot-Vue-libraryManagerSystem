package com.nhXJH.web.domain;

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
* 物品单位信息对象 base_util
*
* @author xjh
* @date 2022-01-25
*/
@TableName("base_util")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MeasurementUtil extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 物品单位id */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty("id")
    private Long id;

    /** 物品单位编码 */
    @Excel(name = "物品单位编码")
    @ApiModelProperty("物品单位编码")
    private String code;

    /** 物品单位名称 */
    @Excel(name = "物品单位名称")
    @ApiModelProperty("物品单位名称")
    private String name;

    /** 备注说明 */
    @Excel(name = "备注说明")
    @ApiModelProperty("备注说明")
    private String mark;

    /** 状态，一存在，零不存在，默认一 */
    @Excel(name = "状态",readConverterExp="0=失效,1=有效")
    @ApiModelProperty("状态，一存在，零不存在，默认一")
    private String status;

    /** 创建人 */
    @Excel(name = "创建人")
    @ApiModelProperty("创建人")
    private Long createPersonal;

    /** 删除人 */
    @Excel(name = "删除人")
    @ApiModelProperty("删除人")
    private Long updatePersonal;



}
