package com.nhXJH.web.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.nhXJH.common.annotation.Excels;
import com.nhXJH.common.core.domain.entity.SysDept;
import com.nhXJH.system.domain.po.BaseBookClassCn;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
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

import javax.validation.constraints.Pattern;

/**
* 图书实体信息对象 library_book
*
* @author xjh
* @date 2022-02-07
*/
@TableName("library_book")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LibraryBook extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty("id")
    private Long id;

    /** 图书名称 */
    @Excel(name = "图书名称")
    @ApiModelProperty("图书名称")
    private String name;

    /** 图书编码 */
    @Excel(name = "图书编码")
    @ApiModelProperty("图书编码")
    @Pattern(regexp = "/^[a-zA-Z0-9]{2,10}$/",message = "图书编码长度必须介于 2 和 10 之间的字母数字组成")
    private String code;

    /** 图书类型（资料、图书） */
    @Excel(name = "图书类别",readConverterExp = "0=图书,1=资料")
    @ApiModelProperty("图书类型")
    private String type;

    /** 保存、公开级别 */
    @Excel(name = "保存、公开级别",readConverterExp = "0=一级类别,1=二级类别")
    @ApiModelProperty("保存、公开级别")
    private String leavel;

    /** 图书类别 */
    @ApiModelProperty("图书类别")
    private Long claz;

    @Excels({
            @Excel(name = "图书类别编码",targetAttr = "code",type = Excel.Type.EXPORT),
            @Excel(name = "图书类别",targetAttr = "name",type = Excel.Type.EXPORT)
    })
    private BaseBookClassCn bookClassCn;

    /** 作者 */
    @ApiModelProperty("作者")
    private Long author;

    @Excels({
            @Excel(name = "作者",targetAttr = "name",type = Excel.Type.EXPORT)
    })
    private LibraryAuth libraryAuth;

    /** 出版社 */
    @Excel(name = "出版社")
    @ApiModelProperty("出版社")
    private Long mechanism;

    @Excels({
            @Excel(name = "所属图书管理机构",targetAttr = "deptName",type = Excel.Type.EXPORT),
            @Excel(name = "机构负责人",targetAttr = "leader",type = Excel.Type.EXPORT),
            @Excel(name = "机构联系方式",targetAttr = "phone",type = Excel.Type.EXPORT),
            @Excel(name = "机构邮箱",targetAttr = "email",type = Excel.Type.EXPORT)

    })
    private SysDept sysDept;
    @Excels({
            @Excel(name = "机构地址",targetAttr = "addr",type = Excel.Type.EXPORT)
    })
    private LibraryMechanism libraryMechanism;

    /** 出版日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出版日期", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty("出版日期")
    private Date publicationDate;

    /** 图书版本号 */
    @Excel(name = "图书版本号")
    @ApiModelProperty("图书版本号")
    private String version;

    /** 发行量（单位册） */
    @Excel(name = "发行量",suffix = "(册)")
    @ApiModelProperty("发行量")
    private String circulation;

    /** ISBN */
    @Excel(name = "ISBN")
    @ApiModelProperty("ISBN")
    private String isbn;

    /** 系列丛书名称 */
    @Excel(name = "系列丛书名称")
    @ApiModelProperty("系列丛书名称")
    private String series;

    /** 关键字（逗号分隔） */
    @Excel(name = "关键字")
    @ApiModelProperty("关键字")
    private String keyWords;

    /** 单价 */
    @Excel(name = "单价",suffix = "（￥）")
    @ApiModelProperty("单价")
    private String price;

    /** 说明 */
    @Excel(name = "说明")
    @ApiModelProperty("说明")
    private String mark;

    /** 其他内容 */
    @Excel(name = "其他内容")
    @ApiModelProperty("其他内容")
    private String other;

    /** 状态，1表示存在，0表示失效 */
    @Excel(name = "状态",readConverterExp="0=失效,1=有效")
    @ApiModelProperty("状态，1表示存在，0表示失效")
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
