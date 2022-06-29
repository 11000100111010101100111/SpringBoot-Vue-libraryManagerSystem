package com.nhXJH.web.domain;

import com.nhXJH.common.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.nhXJH.common.annotation.Excels;
import com.nhXJH.common.core.domain.entity.SysUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import io.swagger.annotations.ApiModelProperty;
import com.nhXJH.common.core.domain.BaseEntity;

/**
* 采购申请结点处理对象 base_purchase_link
*
* @author xjh
* @date 2022-01-25
*/
@TableName("base_purchase_link")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseLink extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 记录ID */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty("id")
    private Long id;

    /** 采购申请ID */
    @Excel(name = "采购申请ID")
    @ApiModelProperty("采购申请ID")
    private Long approveId;

    /** 采购申请ID */
    @Excel(name = "采购申请ID")
    @ApiModelProperty("采购申请ID")
    private Long purchaseNode;

    /** 结点处理人ID */
    @ApiModelProperty("结点处理人ID")
    private Long handlePersonal;

    @Excels({
            @Excel(name = "结点处理人", targetAttr = "nickName", type = Excel.Type.EXPORT),
            @Excel(name = "处理人联系方式", targetAttr = "phonenumber", type = Excel.Type.EXPORT)
    })
    private SysUser handelSysUser;

    /** 结点处理结果，默认0（待办，暂未处理），1（通过），（-1）拒绝 */
    @Excel(name = "结点处理结果", readConverterExp = "0=待办,1=通过,-1=拒绝")
    @ApiModelProperty("结点处理结果")
    private String handleResult;

    /** 备注 */
    @Excel(name = "备注")
    @ApiModelProperty("备注")
    private String mark;

    /** 记录状态，默认1（存在），0（删除） */
    @Excel(name = "状态",readConverterExp="0=失效,1=有效")
    @ApiModelProperty("记录状态，默认1")
    private String status;

    /** 创建人 */
    @Excel(name = "创建人")
    @ApiModelProperty("创建人")
    private Long createPersonal;

    /** 更新人 */
    @Excel(name = "更新人")
    @ApiModelProperty("更新人")
    private Long updatePersonal;

}
