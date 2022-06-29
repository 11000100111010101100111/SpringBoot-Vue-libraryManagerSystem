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
* 采购申请信息对象 base_purchase_requisition
*
* @author xjh
* @date 2022-01-25
*/
@TableName("base_purchase_requisition")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseRequisition extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 记录id */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty("${comment}")
    private Long id;

    @ApiModelProperty("采购分组ID")
    private Long groupId;

    /** 采购申请标题文字 */
    @Excel(name = "采购申请标题文字")
    @ApiModelProperty("采购申请标题文字")
    private String title;

    /** 采购物品名称 */
    @Excel(name = "采购物品名称")
    @ApiModelProperty("采购物品名称")
    private String entityName;

    /** 采购图书id */
    @Excel(name = "采购图书id")
    @ApiModelProperty("采购图书id")
    private Long entityId;

    /** 采购数量 */
    @Excel(name = "采购数量")
    @ApiModelProperty("采购数量")
    private Long number;

    /** 已处理数量 */
    @Excel(name = "已处理数量")
    @ApiModelProperty("已处理数量")
    private Long overNumber;

    /** 采购物品计量单位 */
    @Excel(name = "采购物品计量单位")
    @ApiModelProperty("采购物品计量单位")
    private Long entityUtil;

    /** 采购申请人id */
    @Excel(name = "采购申请人id")
    @ApiModelProperty("采购申请人id")
    private Long approvePersonal;

    /** 采购申请部门 */
    @Excel(name = "采购申请部门")
    @ApiModelProperty("采购申请部门")
    private Long approveDept;

    /** 采购流程模板id */
    @Excel(name = "采购流程模板id")
    @ApiModelProperty("采购流程模板id")
    private Long purchaseTemplate;

    /** 采购链状态，默认零（申请发起），处理中（一），处理完成（2），被取消（-1） */
    @Excel(name = "采购链状态", readConverterExp = "0=发起申请,1=处理中,2=处理完成,3=被取消")
    @ApiModelProperty("采购链状态，默认零")
    private String purchaseStatus;

    /** 采购说明 */
    @Excel(name = "采购说明")
    @ApiModelProperty("采购说明")
    private String mark;

    /** 记录状态（默认1表示已存在，被删除0） */
    @Excel(name = "状态",readConverterExp="0=失效,1=有效")
    @ApiModelProperty("记录状态")
    private Long status;

    /** 创建人ID */
    @Excel(name = "创建人ID")
    @ApiModelProperty("创建人ID")
    private Long createPersonal;

    /** 更新人id */
    @Excel(name = "更新人id")
    @ApiModelProperty("更新人id")
    private Long updatePersonal;



}
