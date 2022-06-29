package com.nhXJH.web.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.nhXJH.common.annotation.Excel;
import com.nhXJH.common.annotation.Excels;
import com.nhXJH.common.core.domain.entity.SysUser;
import com.nhXJH.framework.web.domain.server.Sys;
import com.nhXJH.web.domain.BasePurchaseNode;
import com.nhXJH.web.domain.BasePurchaseRequisition;
import com.nhXJH.web.domain.PurchaseLink;
import com.nhXJH.web.domain.param.purchase.PurchaseNode;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/3/6
 * Time: 13:02
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseLinkNodeVO extends PurchaseLink {
    BasePurchaseRequisition requisitionEntity;
    BasePurchaseNode purchaseNodeEntity;
    SysUser handelPersonalEntity;
    String handelResultName;
    SysUser createUser;
    SysUser updateUser;

    public void setVO(PurchaseLink link){
        this.setId( link.getId());
        this.setHandleResult( link.getHandleResult());
        this.setMark( link.getMark());
        this.setStatus( link.getStatus());
        this.setCreateTime(link.getCreateTime());
    }
}
