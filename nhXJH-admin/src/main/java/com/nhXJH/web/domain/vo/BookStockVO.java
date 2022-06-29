package com.nhXJH.web.domain.vo;

import com.nhXJH.common.core.domain.entity.SysDept;
import com.nhXJH.system.domain.po.BaseBookClassCn;
import com.nhXJH.web.domain.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/3/11
 * Time: 15:08
 * 图书的库存放回结果实体
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookStockVO {
    @ApiModelProperty("图书实体")
    private LibraryBook book;
    @ApiModelProperty("出版社信息")
    private LibraryPress press;
    @ApiModelProperty("作者信息")
    private LibraryAuth auth;
    @ApiModelProperty("图书类别信息")
    private BaseBookClassCn claz;
    @ApiModelProperty("图书的库位信息")
    private BookStock bookStock;
    @ApiModelProperty("库位信息")
    private LibraryStock stock;
    @ApiModelProperty("所在书架信息")
    private StockBookshelf shelf;
    @ApiModelProperty("所在书架层信息")
    private StockStorey storey;
    @ApiModelProperty("所在部门")
    private SysDept dept;
    @ApiModelProperty("全体父级部门")
    private List<SysDept> parentDept;
    @ApiModelProperty("图书附件")
    private BaseFileClassify file;
    @ApiModelProperty("图片地址")
    private List<String> imgSrc;
}
