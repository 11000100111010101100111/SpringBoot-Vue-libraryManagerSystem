package com.nhXJH.web.domain.vo;

import com.nhXJH.web.domain.LibraryStock;
import com.nhXJH.web.domain.StockBookshelf;
import com.nhXJH.web.domain.StockStorey;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/3/9
 * Time: 23:16
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InStockDetailVO {
    @ApiModelProperty("库位信息")
    private LibraryStock stock;
    @ApiModelProperty("书架信息对象")
    private StockBookshelf shelf;
    @ApiModelProperty("书架层信息对象")
    private StockStorey storey;//
}
