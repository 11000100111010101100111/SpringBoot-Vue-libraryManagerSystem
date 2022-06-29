package com.nhXJH.web.domain;

import com.nhXJH.common.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.nhXJH.common.annotation.Excels;
import com.nhXJH.system.domain.po.BaseBookClassCn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import io.swagger.annotations.ApiModelProperty;
import com.nhXJH.common.core.domain.BaseEntity;

/**
* 书架信息对象 library_stock_bookshelf
*
* @author xjh
* @date 2022-01-25
*/
@TableName("library_stock_bookshelf")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class StockBookshelf extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 书架id */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty("ID")
    private Long id;

    /** 书架编码 */
    @Excel(name = "书架编码")
    @ApiModelProperty("书架编码")
    private String code;

    /** 书架名称 */
    @Excel(name = "书架名称")
    @ApiModelProperty("书架名称")
    private String name;

    /** 书架标题 */
    @Excel(name = "书架标题")
    @ApiModelProperty("书架标题")
    private String title;

    /** 书架存书类别ID */
    @Excel(name = "书架存书类别")
    @ApiModelProperty("书架存书类别ID")
    private Long bookType;

    @Excels({
            @Excel(name = "存书类别编码", targetAttr = "code", type = Excel.Type.EXPORT),
            @Excel(name = "存书类别", targetAttr = "name", type = Excel.Type.EXPORT)
    })
    private BaseBookClassCn classCn;

    /** 备注 */
    @Excel(name = "备注")
    @ApiModelProperty("备注")
    private String mark;

    /** 状态，默认1,表示存在，失效使用0表示 */
    @Excel(name = "状态",readConverterExp="0=失效,1=有效")
    @ApiModelProperty("状态，默认1,表示存在，失效使用0表示")
    private String status;

    /** 部门ID */
    @Excel(name = "部门")
    @ApiModelProperty("部门ID")
    private Long dept;

    /** 创建人ID */
    @Excel(name = "创建人")
    @ApiModelProperty("创建人ID")
    private Long createPersonal;

    /** 更新人ID */
    @Excel(name = "更新人")
    @ApiModelProperty("更新人ID")
    private Long updatePersonal;

//    public StockBookshelf getStockShelf(){
//        StockBookshelf bookshelf = new StockBookshelf();
//        bookshelf.setId (this.getId());// id;
//        bookshelf.setCode (this.getCode());// code;
//        bookshelf.setName (this.getName());// name;
//        bookshelf.setTitle (this.getTitle());// title;
//        bookshelf.setBookType (this.getBookType());// bookType;
//        bookshelf.setMark (this.getMark());// mark;
//        bookshelf.setStatus (this.getStatus());// status;
//        bookshelf.setDept (this.getDept());// dept;
//        bookshelf.setCreatePersonal (this.getCreatePersonal());// createPersonal;
//        bookshelf.setUpdatePersonal (this.getUpdatePersonal());// updatePersonal;
//        bookshelf.setCreateTime (this.getCreateTime());// createPersonal;
//        bookshelf.setUpdateTime (this.getUpdateTime());// updatePersonal;
//        return bookshelf;
//    }
}
