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
* 审批流程结点信息对象 base_purchase_node
*
* @author xjh
* @date 2022-03-03
*/
@TableName("base_purchase_node")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BasePurchaseNode extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty("id")
    private Long id;

    /** 结点所属审批流程模板id */
    @Excel(name = "结点所属审批流程模板id")
    @ApiModelProperty("结点所属审批流程模板id")
    private Long purchaseTemplate;

    /** 结点名称 */
    @Excel(name = "结点")
    @ApiModelProperty("结点")
    private String nodeCode;

    /** 结点名称 */
    @Excel(name = "结点名称")
    @ApiModelProperty("结点名称")
    private String name;

    /** 结点类型(0=开始结点,1=过程结点,2=终止结点,4=分支结点) */
    @Excel(name = "结点类型(0=开始结点,1=过程结点,2=终止结点,4=分支结点)")
    @ApiModelProperty("结点类型(0=开始结点,1=过程结点,2=终止结点,4=分支结点)")
    private String type;

    /** 备注信息 */
    @Excel(name = "备注信息")
    @ApiModelProperty("备注信息")
    private String mark;

//    /** 是否删除 */
//    @ApiModelProperty("备注信息")
//    private String isDel;

    /** 状态 */
    @Excel(name = "状态")
    @ApiModelProperty("状态")
    private String status;

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
