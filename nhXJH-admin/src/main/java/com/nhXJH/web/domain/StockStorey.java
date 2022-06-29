package com.nhXJH.web.domain;

import com.nhXJH.common.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.nhXJH.common.annotation.Excels;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import io.swagger.annotations.ApiModelProperty;
import com.nhXJH.common.core.domain.BaseEntity;

/**
* 书架层信息对象 library_stock_storey
*
* @author xjh
* @date 2022-01-25
*/
@TableName("library_stock_storey")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class StockStorey extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 层ID */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty("id")
    private Long id;

    /** 层编码 */
    @Excel(name = "层编码")
    @ApiModelProperty("层编码")
    private String code;

    /** 层名称 */
    @Excel(name = "层名称")
    @ApiModelProperty("层名称")
    private String name;

    /** 层标题 */
    @Excel(name = "层标题")
    @ApiModelProperty("层标题")
    private String title;

    /** 存放图书类别 */
    @Excel(name = "存放图书类别")
    @ApiModelProperty("存放图书类别")
    private Long bookType;

    /** 备注 */
    @Excel(name = "备注")
    @ApiModelProperty("备注")
    private String mark;

    /** 状态，默认1,表示存在，失效使用0表示 */
    @Excel(name = "状态",readConverterExp="0=失效,1=有效")
    @ApiModelProperty("状态")
    private String status;

    /** 书架ID */
    @ApiModelProperty("书架ID")
    private Long bookshelf;


    @Excels({
            @Excel(name = "所在书架编码", targetAttr = "code", type = Excel.Type.EXPORT),
            @Excel(name = "所在书架名称", targetAttr = "name", type = Excel.Type.EXPORT)
    })
    private StockBookshelf shelf;

    /** 创建人ID */
    @Excel(name = "创建人ID")
    @ApiModelProperty("创建人ID")
    private Long createPersonal;

    /** 更新人ID */
    @Excel(name = "更新人ID")
    @ApiModelProperty("更新人ID")
    private Long updatePersonal;
}
