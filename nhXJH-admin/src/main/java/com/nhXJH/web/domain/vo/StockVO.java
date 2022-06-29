package com.nhXJH.web.domain.vo;

import com.nhXJH.common.core.domain.entity.SysUser;
import com.nhXJH.system.domain.po.BaseBookClassCn;
import com.nhXJH.web.domain.*;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/2/24
 * Time: 21:38
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiOperation("库位存书信息")
public class StockVO {
    @ApiModelProperty("库位创建人")
    private SysUser createStockU;

    @ApiModelProperty("库位更新")
    private SysUser updateStockU;

    @ApiModelProperty("库位图书入库信息创建人")
    private SysUser createStockBookU;

    @ApiModelProperty("库位图书入库信息更新人")
    private SysUser updateStockBookU;

    @ApiModelProperty("库位图书当前借阅人")
    private SysUser useBookU;

    @ApiModelProperty("当前库位信息")
    private LibraryStock stock;

    @ApiModelProperty("当前库位存书信息")
    private BookStock bookStock;

    @ApiModelProperty("图书基本信息")
    private LibraryBook book;

    @ApiModelProperty("图书作者")
    private LibraryAuth auth;

    @ApiModelProperty("图书类别信息")
    private BaseBookClassCn bookType;

    @ApiModelProperty("出版社信息信息")
    private LibraryPress press;

    @ApiModelProperty("图书相关图片附件信息")
    private BaseFileClassify file;
}
