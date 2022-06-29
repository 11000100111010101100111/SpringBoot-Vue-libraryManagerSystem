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
* 审批流程模板对象 base_purchase_template
*
* @author xjh
* @date 2022-01-25
*/
@TableName("base_purchase_template")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseTemplate extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 模板ID */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty("id")
    private Long id;
    /** 模板ID */
    @ApiModelProperty("dept")
    private Long dept;
    /** 模板编码 */
    @Excel(name = "模板编码")
    @ApiModelProperty("模板编码")
    private String code;

    /** 模板标题 */
    @Excel(name = "模板标题")
    @ApiModelProperty("模板标题")
    private String title;

    /** 模板，json串形式 */
    @Excel(name = "模板，json串形式")
    @ApiModelProperty("模板，json串形式")
    private String template;

    /** 备注 */
    @Excel(name = "备注")
    @ApiModelProperty("备注")
    private String mark;

    /** 模板状态，默认1存在，0表示不存在 */
    @Excel(name = "状态",readConverterExp="0=失效,1=有效")
    @ApiModelProperty("模板状态，默认1存在，0表示不存在")
    private Long status;

    /** 创建人 */
    @Excel(name = "创建人")
    @ApiModelProperty("创建人")
    private Long createPersonal;

    /** 更新人 */
    @Excel(name = "更新人")
    @ApiModelProperty("更新人")
    private Long updatePersonal;



}
