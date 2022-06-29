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
* 库位信息对象 library_stock
*
* @author xjh
* @date 2022-01-25
*/
@TableName("library_stock")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LibraryStock extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 库位id */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty("id")
    private Long id;

    /** 库位编码 */
    @Excel(name = "库位编码")
    @ApiModelProperty("库位编码")
    private String stockCode;

    /** 图书管理机构id */
    @Excel(name = "图书管理机构id")
    @ApiModelProperty("图书管理机构id")
    private Long mechanism;

    /** 库位所在书架层ID */
    @Excel(name = "库位所在书架层ID")
    @ApiModelProperty("库位所在书架层ID")
    private Long storeId;

    /** 库位具体位置，例如：xx楼xx库xx夹xx排... */
    @Excel(name = "库位具体位置，例如：xx楼xx库xx夹xx排...")
    @ApiModelProperty("库位具体位置，例如：xx楼xx库xx夹xx排...")
    private String localtion;

    /** 是否存放图书 */
    @Excel(name = "是否存放图书",readConverterExp = "0=正常未存放图书,1=已存放图书,-1=损坏未存放图书")
    @ApiModelProperty("是否存放图书,默认正常未存放图书，1表示已存放图书，-1表示损坏未存放图书")
    private String hasBook;

    /** 图书实体图片 */
    @Excel(name = "图书实体图片")
    @ApiModelProperty("图书实体图片")
    private String url;

    /** 备注 */
    @Excel(name = "备注")
    @ApiModelProperty("备注")
    private String mark;

    /** 库位状态*/
    @Excel(name = "状态",readConverterExp="0=失效,1=有效")
    @ApiModelProperty("库位状态")
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
