package com.nhXJH.web.domain.param;

import com.nhXJH.web.domain.BasePurchaseRequisition;
import com.nhXJH.web.domain.BasePurchaseRequisitionSub;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/2/28
 * Time: 22:41
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseParam {
    @ApiModelProperty("申请")
    private BasePurchaseRequisition purchase;
    @ApiModelProperty("申请子项")
    private BasePurchaseRequisitionSub[] subPurchase;
    private String submitType;

    public List<BasePurchaseRequisitionSub> getSubPurchase(){
        if (null == subPurchase){
            return new ArrayList<>();
        }
        List<BasePurchaseRequisitionSub> arr = new ArrayList<>();
        for (int i=0;i<subPurchase.length;i++) {
            arr.add(subPurchase[i]);
        }
        return arr;
    }
}
