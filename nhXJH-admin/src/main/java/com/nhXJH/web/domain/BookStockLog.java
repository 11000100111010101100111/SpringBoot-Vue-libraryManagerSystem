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
* 图书库存出入信息对象 book_stock_log
*
* @author xjh
* @date 2022-01-25
*/
@TableName("book_stock_log")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BookStockLog extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty("id")
    private Long id;

    /** 图书库存ID */
    @Excel(name = "图书库存ID")
    @ApiModelProperty("图书库存ID")
    private Long stockId;

    /** 出或入库，出库-1，入库1 */
    @Excel(name = "出或入库",readConverterExp = "-1=出库,1=入库")
    @ApiModelProperty("出或入库，出库-1，入库1")
    private String isLoan;

    /** 操作方式（在线、线下、代办等） */
    @Excel(name = "操作方式", readConverterExp = "2=在线办理,1=线下办理,3=代办,0=其他方式")
    @ApiModelProperty("操作方式")
    private String operateType;

    /** 创建人 */
    @Excel(name = "创建人")
    @ApiModelProperty("创建人")
    private Long createPersonal;

    /** 代办人、线下工作人员、在线操作人等 */
    @Excel(name = "代办人、线下工作人员、在线操作人等")
    @ApiModelProperty("代办人、线下工作人员、在线操作人等")
    private Long otherPersonal;

    /** 更新人 */
    @Excel(name = "更新人")
    @ApiModelProperty("更新人")
    private Long updatePersonal;



}
