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
* 采购申请子对象 base_purchase_requisition_sub
*
* @author xjh
* @date 2022-02-28
*/
@TableName("base_purchase_requisition_sub")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BasePurchaseRequisitionSub extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty("id")
    private Long id;

    /** 采购申请组id */
    @Excel(name = "采购申请组id")
    @ApiModelProperty("采购申请组id")
    private Long groupId;

    /** 批次ID，每次子表记录修改形成新的批次 */
    @Excel(name = "批次ID")
    @ApiModelProperty("批次ID")
    private Long batchId;

    /** 采购图书id */
    @Excel(name = "采购图书id")
    @ApiModelProperty("采购图书id")
    private Long entityId;

    /** 采购图书名称 */
    @Excel(name = "采购图书名称")
    @ApiModelProperty("采购图书名称")
    private String entityName;

    /** 采购数 */
    @Excel(name = "采购数")
    @ApiModelProperty("采购数")
    private Long purchaseNumber;

    /** 处理数 */
    @Excel(name = "处理数")
    @ApiModelProperty("处理数")
    private Long purchaseOverNumber;

    /** 计量单位ID */
    @Excel(name = "计量单位ID")
    @ApiModelProperty("计量单位ID")
    private Long util;

    /** 计量单位名称 */
    @Excel(name = "计量单位名称")
    @ApiModelProperty("计量单位名称")
    private String utilName;

//    /** 是否删除 */
//    @Excel(name = "是否删除")
//    @ApiModelProperty("是否删除")
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
