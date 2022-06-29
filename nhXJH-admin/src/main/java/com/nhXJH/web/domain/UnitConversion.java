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
* 计量单位换算信息对象 base_unit_conversion
*
* @author xjh
* @date 2022-01-25
*/
@TableName("base_unit_conversion")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UnitConversion extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty("id")
    private Long id;

    /** 编号，换算码 */
    @Excel(name = "编号，换算码")
    @ApiModelProperty("编号，换算码")
    private String code;

    /** 换算单位 */
    @Excel(name = "换算单位")
    @ApiModelProperty("换算单位")
    private String unitName;

    /** 换算后的单位 */
    @Excel(name = "换算后的单位")
    @ApiModelProperty("换算后的单位")
    private String afterUnitName;

    /** 换算值 */
    @Excel(name = "换算值")
    @ApiModelProperty("换算值")
    private Long unitValue;

    /** 备注 */
    @Excel(name = "备注")
    @ApiModelProperty("备注")
    private String mark;

    /** 状态，存在一表示，不存在零表示。默认一 */
    @Excel(name = "状态",readConverterExp="1=存在,0=失效")
    @ApiModelProperty("状态")
    private String status;

    /** 创建人 */
    @ApiModelProperty("创建人")
    private Long createPersonal;

    /** 删除人 */
    @ApiModelProperty("删除人")
    private Long updatePersonal;

}
