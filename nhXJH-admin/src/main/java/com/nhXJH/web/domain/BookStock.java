package com.nhXJH.web.domain;

import com.nhXJH.common.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.nhXJH.common.annotation.Excels;
import com.nhXJH.common.core.domain.entity.SysDept;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import io.swagger.annotations.ApiModelProperty;
import com.nhXJH.common.core.domain.BaseEntity;

/**
* 图书库存信息对象 book_stock
*
* @author xjh
* @date 2022-01-25
*/
@TableName("book_stock")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BookStock extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty("id")
    private Long id;

    /** 图书ID */
    @Excel(name = "图书ID")
    @ApiModelProperty("图书ID")
    private Long bookId;

    /** 图书管理机构ID */
    @ApiModelProperty("图书管理机构ID")
    private Long mechanismId;

    @Excels({
            @Excel(name = "图书管理机构",targetAttr = "deptName",type = Excel.Type.EXPORT)
    })
    private SysDept dept;


    /** 库存实体编码 */
    @Excel(name = "库存实体编码")
    @ApiModelProperty("库存实体编码")
    private String entityCode;

    /** 库位ID */
    @ApiModelProperty("库位ID")
    private Long stockLocaltion;

    @Excels({
            @Excel(name = "库位编码",targetAttr = "stockCode",type = Excel.Type.EXPORT),
            @Excel(name = "库位详细位置",targetAttr = "localtion",type = Excel.Type.EXPORT)
    })
    private LibraryStock libraryStock;


    /** 是否借出，默认未借出零表示，已出库-1表示 */
    @Excel(name = "是否借出",readConverterExp = "0=在库,-1=已出库")
    @ApiModelProperty("是否借出，默认未借出零表示，已出库-1表示")
    private String hasLoan;

    /** 状态，默认一存在，零表示失效 */
    @Excel(name = "状态",readConverterExp="0=失效,1=有效")
    @ApiModelProperty("状态，默认一存在，零表示失效")
    private String status;

    /** 创建人ID */
    @Excel(name = "创建人ID")
    @ApiModelProperty("创建人ID")
    private Long createPersonal;

    /** 更新人ID */
    @Excel(name = "更新人ID")
    @ApiModelProperty("更新人ID")
    private Long updatePersonal;



}
