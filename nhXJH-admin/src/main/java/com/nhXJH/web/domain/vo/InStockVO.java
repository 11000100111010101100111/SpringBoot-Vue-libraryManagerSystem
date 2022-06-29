package com.nhXJH.web.domain.vo;

import com.nhXJH.common.core.domain.entity.SysDept;
import com.nhXJH.system.domain.po.BaseBookClassCn;
import com.nhXJH.web.domain.BaseFile;
import com.nhXJH.web.domain.BasePurchaseRequisitionSub;
import com.nhXJH.web.domain.LibraryBook;
import com.nhXJH.web.domain.LibraryStock;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/3/9
 * Time: 23:13
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InStockVO {
    @ApiModelProperty("入库图书实体信息")
    private LibraryBook book;
    @ApiModelProperty("入库图书实体信息")
    private BaseBookClassCn claz;
    @ApiModelProperty("入库图书实体信息")
    private List<BaseFile> bookFile;
    @ApiModelProperty("申请入库部门信息")
    private SysDept approveDept;
    @ApiModelProperty("入库单信息")
    private BasePurchaseRequisitionSub subPurchase;
    @ApiModelProperty("库位具体实体信息")
    private List<InStockDetailVO> stockList;
    @ApiModelProperty("入库信息")
    private String msg;
    @ApiModelProperty("入库状态")
    private int status;
}
