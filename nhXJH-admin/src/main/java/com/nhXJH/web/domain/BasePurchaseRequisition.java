package com.nhXJH.web.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
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
* 采购申请信息对象 base_purchase_requisition
*
* @author xjh
* @date 2022-02-28
*/
@TableName("base_purchase_requisition")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BasePurchaseRequisition extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 记录id */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty("id")
    private Long id;

    /** 记录id */
    @ApiModelProperty("上一版本即修改前的id")
    private Long agoId;

    /** 采购申请标题文字 */
    @Excel(name = "采购申请标题文字")
    @ApiModelProperty("采购申请标题文字")
    private String title;

    /** 采购申请人id */
    @ApiModelProperty("采购申请标题文字")
    private Long approvePersonal;

    /** 采购申请部门 */
    @Excel(name = "采购申请部门")
    @ApiModelProperty("采购申请部门")
    private Long approveDept;

    /** 采购开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "采购开始时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("采购开始时间")
    private Date startTime;

    /** 采购结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "采购结束时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("采购结束时间")
    private Date endTime;

    /** 有效期至某年某月某日某时某分某秒结束 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "有效期至某年某月某日某时某分某秒结束", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("有效期至某年某月某日某时某分某秒结束")
    private Date termValidity;

    /** 采购流程模板id */
    @ApiModelProperty("有效期至某年某月某日某时某分某秒结束")
    private Long purchaseTemplate;

    /** 采购链状态，默认零（申请发起），处理中（一），处理完成（2），被取消（-1） */
    @Excel(name = "采购链状态，默认零", readConverterExp = "申=请发起")
    @ApiModelProperty("采购链状态，默认零")
    private String purchaseStatus;

    /** 采购说明 */
    @Excel(name = "采购说明")
    @ApiModelProperty("采购说明")
    private String mark;

//    /** 是否被删除 */
//    @ApiModelProperty("采购说明")
//    private String isDel;

    /** 记录状态（默认1表示已存在，被删除0） */
    @Excel(name = "记录状态", readConverterExp = "默=认1表示已存在，被删除0")
    @ApiModelProperty("记录状态")
    private String status;

//    /** 创建人ID */
//    @Excel(name = "创建人ID")
//    @ApiModelProperty("创建人ID")
//    private Long createPersonal;
//
//    /** 更新人id */
//    @Excel(name = "更新人id")
//    @ApiModelProperty("更新人id")
//    private Long updatePersonal;



}
