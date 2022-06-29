package com.nhXJH.web.domain.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.nhXJH.common.annotation.Excel;
import com.nhXJH.common.core.domain.BaseEntity;
import com.nhXJH.web.domain.LibraryBook;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/2/8
 * Time: 20:03
 **/
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LibraryBookDto  extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** ID */
    @ApiModelProperty("ID")
    private Long id;

    /** 图书名称 */
    @ApiModelProperty("图书名称")
    private String name;

    /** 图书编码 */
    @ApiModelProperty("图书编码")
    private String code;

    /** 图书类型（资料、图书） */
    @ApiModelProperty("图书类型")
    private String type;
    @ApiModelProperty("图书类型code")
    private String typeCode;

    /** 保存、公开级别 */
    @ApiModelProperty("保存、公开级别")
    private String leavel;

    /** 图书类别 */
    @ApiModelProperty("图书类别")
    private String claz;
    @ApiModelProperty("图书类别ID")
    private Long clazId;

    /** 作者 */
    @Excel(name = "作者")
    @ApiModelProperty("作者")
    private String author;
    @ApiModelProperty("作者id")
    private Long authorId;

    /** 出版社 */
    @Excel(name = "出版社")
    @ApiModelProperty("出版社")
    private String mechanism;
    @ApiModelProperty("出版社id")
    private Long mechanismId;

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
    @Excel(name = "发行量", readConverterExp = "单=位册")
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
    @Excel(name = "关键字", readConverterExp = "逗=号分隔")
    @ApiModelProperty("关键字")
    private String keyWords;

    /** 单价 */
    @Excel(name = "单价")
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
    @ApiModelProperty("状态，1表示存在，0表示失效")
    private String status;
    @ApiModelProperty("状态码")
    private String statusName;

    /** 创建人 */
    @Excel(name = "创建人")
    @ApiModelProperty("创建人")
    private Long createPersonal;
    @ApiModelProperty("创建人id")
    private String createPersonalName;

    /** 更新人 */
    @Excel(name = "更新人")
    @ApiModelProperty("更新人")
    private Long updatePersonal;
    @ApiModelProperty("更新人id")
    private String updatePersonalName;

    public void setEntity(LibraryBook book){
        this.id=book.getId();
        this.name=book.getName();
        this.code=book.getCode();
        this.type=book.getType();
        this.leavel=book.getLeavel();

//        this.claz;
        this.clazId=book.getClaz();

//        this.author;
        this.authorId=book.getAuthor();

//        this.mechanism;
        this.mechanismId=book.getMechanism();

        this.publicationDate=book.getPublicationDate();

        this.version=book.getVersion();

        this.circulation=book.getCirculation();

        this.isbn=book.getIsbn();

        this.series=book.getSeries();

        this.keyWords=book.getKeyWords();

        this.price=book.getPrice();

        this.mark=book.getMark();

        this.other=book.getOther();

//        this.status;
        this.status=book.getStatus();

//        this.createPersonal;
        this.createPersonal=book.getCreatePersonal();

//        this.updatePersonal;

        this.updatePersonal=book.getUpdatePersonal();
    }
}
